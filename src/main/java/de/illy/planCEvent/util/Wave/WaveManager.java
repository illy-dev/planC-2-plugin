package de.illy.planCEvent.util.Wave;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class WaveManager {
    private final JavaPlugin plugin;
    private final List<Wave> waves = new ArrayList<>();

    public WaveManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadWaves();
    }

    public void loadWaves() {
        waves.clear();
        File file = new File(plugin.getDataFolder(), "waves.yml");
        if (!file.exists()) {
            plugin.saveResource("waves.yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection waveSection = config.getConfigurationSection("waves");
        if (waveSection == null) return;

        for (String waveKey : waveSection.getKeys(false)) {
            ConfigurationSection mobSection = waveSection.getConfigurationSection(waveKey);
            if (mobSection == null) continue;

            Map<EntityType, Integer> mobMap = new HashMap<>();
            for (String mobName : mobSection.getKeys(false)) {
                try {
                    EntityType type = EntityType.valueOf(mobName.toUpperCase());
                    int count = mobSection.getInt(mobName);
                    mobMap.put(type, count);
                } catch (IllegalArgumentException e) {
                    Bukkit.getLogger().warning("Invalid mob type in waves.yml: " + mobName);
                }
            }
            waves.add(new Wave(mobMap));
        }
    }


    public Wave getWave(int index) {
        if (index < 0 || index >= waves.size()) return null;
        return waves.get(index);
    }

    public int getWaveCount() {
        return waves.size();
    }
}
