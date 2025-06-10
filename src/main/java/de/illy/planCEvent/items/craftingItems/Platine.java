package de.illy.planCEvent.items.craftingItems;

import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Platine {
    public static final ItemStack emptyPlatine;
    public static final ItemStack platine;

    static {
        emptyPlatine = new CustomItemBuilder(Material.GLISTERING_MELON_SLICE)
                .setDisplayName("§aEmpty Platine")
                .setRarity("§a§lUNCOMMON")
                .addCheckEnchantment()
                .build();
        platine = new CustomItemBuilder(Material.GLISTERING_MELON_SLICE)
                .setDisplayName("§9Platine")
                .setRarity("§9§lRARE")
                .addCheckEnchantment2()
                .build();
    }
}
