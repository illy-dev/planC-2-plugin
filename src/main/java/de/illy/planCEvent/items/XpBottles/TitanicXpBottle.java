package de.illy.planCEvent.items.XpBottles;

import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TitanicXpBottle {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName("§9Titanic Experience Bottle")
                .addCustomEnchantmentLore("§7Smash it open to receive experience!")
                .setRarity("§9§lRARE")
                .setUnbreakable(true)
                .addTag("is_titanic")
                .build();
    }
}
