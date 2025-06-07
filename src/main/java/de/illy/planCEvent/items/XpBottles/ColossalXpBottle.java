package de.illy.planCEvent.items.XpBottles;

import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ColossalXpBottle {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName("§5Colossal Experience Bottle")
                .addCustomEnchantmentLore("§7Smash it open to receive experience!")
                .setRarity("§5§lEPIC")
                .setUnbreakable(true)
                .addTag("is_colossal")
                .build();
    }
}
