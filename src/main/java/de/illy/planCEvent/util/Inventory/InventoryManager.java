package de.illy.planCEvent.util.Inventory;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.UUID;

public class InventoryManager {

    private static FileConfiguration config = InventoryFileManager.get();

    public static void saveInventory(Player player) {
        UUID uuid = player.getUniqueId();

        // Save the inventory, including the main inventory and armor
        config.set("inventories." + uuid + ".contents", player.getInventory().getContents());
        config.set("inventories." + uuid + ".armor", player.getInventory().getArmorContents());

        // Save the offhand separately
        config.set("inventories." + uuid + ".offhand", player.getInventory().getItemInOffHand());

        // Save experience level and potion effects
        config.set("inventories." + uuid + ".experience", player.getTotalExperience());
        config.set("inventories." + uuid + ".potionEffects", player.getActivePotionEffects());

        InventoryFileManager.save();
    }

    public static void restoreInventory(Player player) {
        UUID uuid = player.getUniqueId();

        // Restore the main inventory and armor
        ItemStack[] contents = ((ItemStack[]) config.get("inventories." + uuid + ".contents"));
        ItemStack[] armor = ((ItemStack[]) config.get("inventories." + uuid + ".armor"));

        if (contents != null) {
            player.getInventory().setContents(contents);
        }
        if (armor != null) {
            player.getInventory().setArmorContents(armor);
        }

        // Restore the offhand item
        ItemStack offhand = ((ItemStack) config.get("inventories." + uuid + ".offhand"));
        if (offhand != null) {
            player.getInventory().setItemInOffHand(offhand);
        }

        // Restore the experience level
        if (config.contains("inventories." + uuid + ".experience")) {
            player.setTotalExperience(config.getInt("inventories." + uuid + ".experience"));
        }

        // Restore potion effects
        if (config.contains("inventories." + uuid + ".potionEffects")) {
            Object potionEffectsObj = config.get("inventories." + uuid + ".potionEffects");
            if (potionEffectsObj instanceof List) {
                List<?> potionEffectsList = (List<?>) potionEffectsObj;
                for (Object obj : potionEffectsList) {
                    if (obj instanceof PotionEffect) {
                        player.addPotionEffect((PotionEffect) obj);
                    }
                }
            }
        }

        InventoryFileManager.save();
    }


    public static void clearSavedInventory(UUID uuid) {
        config.set("inventories." + uuid, null);
        InventoryFileManager.save();
    }
}
