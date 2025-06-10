package de.illy.planCEvent.items.CoinSystem;

import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Coin {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.FIREWORK_STAR)
                .setDisplayName("§eCoin")
                .setRarity("§f§lCOMMON")
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }
}
