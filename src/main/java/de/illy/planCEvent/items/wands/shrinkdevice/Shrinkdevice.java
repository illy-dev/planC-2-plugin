package de.illy.planCEvent.items.wands.shrinkdevice;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Shrinkdevice {
    @Getter
    private static Map<UUID, Integer> sizeLevelMap = new HashMap<>();

    public static ItemStack create() {
        return new CustomItemBuilder(Material.STICK)
                .setDisplayName("§cShrink Device")
                .setAbilityName("§6Ability: Grow  §e§lRIGHT CLICK")
                .addAbilityDescriptionLine("§7Grow your own player size.")
                .addAbilityDescriptionLine("§8Mana Cost: §3120")
                .addAbilityDescriptionLine("")
                .addAbilityDescriptionLine("§6Ability: Shrink  §e§lLEFT CLICK")
                .addAbilityDescriptionLine("§7Shrink your own player size.")
                .addAbilityDescriptionLine("§8Mana Cost: §3120")
                .setRarity("§c§lSPECIAL ITEM")
                .setAbility(AbilityTrigger.RIGHT_CLICK)
                .setAbilityClass(ShrinkdeviceAbilityGrow.class)
                .setBonusTrigger(AbilityTrigger.LEFT_CLICK)
                .setBonusClass(ShrinkdeviceAbilityShrink.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }

    public static String generateShrinkBar(int level) {
        int total = 9;
        int mid = 4; // Mitte des Balkens

        StringBuilder bar = new StringBuilder("§eSize: §f[");

        for (int i = 0; i < total; i++) {
            if (i == mid) {
                bar.append("§f|"); // Mitte immer weißja ab
            } else if (level < 0 && i >= mid + level && i < mid) {
                bar.append("§c|"); // kleiner (rot)
            } else if (level > 0 && i <= mid + level && i > mid) {
                bar.append("§a|"); // größer (grün)
            } else {
                bar.append("§7|"); // leer (grau)
            }
        }

        bar.append("§f]");
        return bar.toString();
    }

    public static void applySize(Player player, int level) {
        AttributeInstance scaleAttr = player.getAttribute(Attribute.SCALE);
        if (scaleAttr != null) {
            scaleAttr.setBaseValue(1.0 + (level * 0.25));
        }
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        String bar = generateShrinkBar(level);
        player.sendActionBar(bar);
    }
}
