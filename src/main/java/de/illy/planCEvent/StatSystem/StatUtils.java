package de.illy.planCEvent.StatSystem;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class StatUtils {
    public static double getStat(ItemStack item, NamespacedKey key) {
        if (item == null || !item.hasItemMeta()) return 0;
        var meta = item.getItemMeta();
        var container = meta.getPersistentDataContainer();
        return container.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
    }

    public static double getPlayerItemStat(Player player, NamespacedKey key) {
        double total = 0;

        total += getStat(player.getInventory().getItemInMainHand(), key);
        total += getStat(player.getInventory().getItemInOffHand(), key);

        for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
            total += getStat(armorPiece, key);
        }

        return total;
    }

}
