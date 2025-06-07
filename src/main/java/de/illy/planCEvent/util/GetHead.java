package de.illy.planCEvent.util;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.UUID;

public class GetHead {

    public static ItemStack getCustomHead(String base64, String displayName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta == null) return head;

        try {
            // Create GameProfile with dummy name
            GameProfile profile = new GameProfile(UUID.randomUUID(), "rune_" + UUID.randomUUID().toString().substring(0, 8));
            profile.getProperties().put("textures", new Property("textures", base64));

            // Get the CraftMetaSkull class (implementation of SkullMeta)
            Class<?> craftMetaSkullClass = meta.getClass();

            // Get the profile field
            Field profileField = craftMetaSkullClass.getDeclaredField("profile");
            profileField.setAccessible(true);

            // Get Minecraft server's ResolvableProfile class
            Class<?> resolvableProfileClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");

            // Find constructor of ResolvableProfile that takes a GameProfile
            Constructor<?> resolvableProfileConstructor = resolvableProfileClass.getDeclaredConstructor(GameProfile.class);
            resolvableProfileConstructor.setAccessible(true);

            // Create an instance of ResolvableProfile wrapping our GameProfile
            Object resolvableProfileInstance = resolvableProfileConstructor.newInstance(profile);

            // Set the field with the ResolvableProfile instance
            profileField.set(meta, resolvableProfileInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        meta.setDisplayName(displayName);
        head.setItemMeta(meta);

        return head;
    }
}

