package de.illy.planCEvent.items.craftingItems;

import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiscItems {
    public static final ItemStack verstaerkterString;
    public static final ItemStack hyperionHandle;
    public static final ItemStack unChargedHype;
    public static final ItemStack enchFeather;
    public static final ItemStack ironRod;

    static {
        verstaerkterString = new CustomItemBuilder(Material.STRING)
                .setDisplayName("§9Verstärkter String")
                .setRarity("§9§lRARE")
                .addCheckEnchantment()
                .build();
        hyperionHandle = new CustomItemBuilder(Material.BREEZE_ROD)
                .setDisplayName("§5Hyperion Handle")
                .setRarity("§5§lEPIC")
                .addCheckEnchantment2()
                .build();
        unChargedHype = new CustomItemBuilder(Material.NETHERITE_SWORD)
                .setDisplayName("§6Uncharged Hyperion")
                .setRarity("§6§lLEGENDARY SWORD")
                .addCheckEnchantment()
                .build();
        enchFeather = new CustomItemBuilder(Material.FEATHER)
                .setDisplayName("§9Enchanted Feather")
                .setRarity("§9§lRARE")
                .addCheckEnchantment()
                .build();
        ironRod = new CustomItemBuilder(Material.TURTLE_SCUTE)
                .setDisplayName("§9Iron Rod")
                .setRarity("§9§lRARE")
                .addCheckEnchantment3()
                .build();
    }
}
