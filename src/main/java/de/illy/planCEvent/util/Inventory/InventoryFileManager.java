package de.illy.planCEvent.util.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class InventoryFileManager {

    private static File file;
    private static FileConfiguration inventoryConfig;

    public static void setup(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "inventories.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not create inventories.yml");
                e.printStackTrace();
            }
        }

        inventoryConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return inventoryConfig;
    }

    public static void save() {
        try {
            inventoryConfig.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save inventories.yml");
            e.printStackTrace();
        }
    }

    public static void reload() {
        inventoryConfig = YamlConfiguration.loadConfiguration(file);
    }
}
