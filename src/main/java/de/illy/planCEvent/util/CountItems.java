package de.illy.planCEvent.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CountItems {
    public static int countItemsInInventory(Player player, ItemStack targetItem) {
        int count = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.isSimilar(targetItem)) {
                count += item.getAmount();
            }
        }

        return count;
    }

    public static boolean removeCustomItem(Player player, ItemStack targetItem, int amountToRemove) {
        ItemStack[] contents = player.getInventory().getContents();
        int remaining = amountToRemove;

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.isSimilar(targetItem)) {
                int stackAmount = item.getAmount();

                if (stackAmount <= remaining) {
                    // Remove entire stack
                    player.getInventory().setItem(i, null);
                    remaining -= stackAmount;
                } else {
                    // Remove partial amount
                    item.setAmount(stackAmount - remaining);
                    player.getInventory().setItem(i, item);
                    return true; // All items removed
                }

                if (remaining <= 0) {
                    return true; // Done removing
                }
            }
        }

        return false; // Not enough matching items to remove
    }
}
