package de.illy.planCEvent.items.XpBottles;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrandXpBottle {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName("§aGrand Experience Bottle")
                .addCustomEnchantmentLore("§7Smash it open to receive experience!")
                .setRarity("§a§lUNCOMMON")
                .setUnbreakable(true)
                .addTag("is_grand")
                .build();
    }
}
