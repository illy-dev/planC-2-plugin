package de.illy.planCEvent.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelicManager {
    private final JavaPlugin plugin;

    public RelicManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private File getPlayerFile(UUID uuid) {
        return new File(plugin.getDataFolder(), "data/" + uuid + ".yml");
    }

    public void saveRelics(Player player, List<ItemStack> relics) {
        File file = getPlayerFile(player.getUniqueId());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (int i = 0; i < relics.size(); i++) {
            config.set("relics." + i, relics.get(i));
        }

        try {
            file.getParentFile().mkdirs();
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ItemStack> loadRelics(Player player) {
        File file = getPlayerFile(player.getUniqueId());
        if (!file.exists()) return new ArrayList<>();

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<ItemStack> relics = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ItemStack item = config.getItemStack("relics." + i);
            relics.add(item);
        }

        return relics;
    }
}

