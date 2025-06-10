package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatAPI {

    private static final Map<UUID, Map<String, Double>> extraStats = new HashMap<>();

    private static final Map<String, Double> defaultStats = Map.ofEntries(
            Map.entry("stat_strength", 0.0),
            Map.entry("stat_health", 100.0),
            Map.entry("stat_crit_chance", 30.0),
            Map.entry("stat_crit_damage", 50.0),
            Map.entry("stat_magic_damage", 0.0),
            Map.entry("stat_defense", 0.0),
            Map.entry("stat_additive_damage_multiplier", 0.0),
            Map.entry("stat_multiplicative_damage_multiplier", 1.0),
            Map.entry("stat_bonus_damage", 0.0),
            Map.entry("stat_level", 1.0),
            Map.entry("stat_xp", 0.0),
            Map.entry("stat_speed", 1.0)
    );


    public static void setExtraStat(UUID uuid, String stat, double value) {
        extraStats.computeIfAbsent(uuid, k -> new HashMap<>()).put(stat, value);
    }

    public static double getExtraStat(UUID uuid, String stat) {
        return extraStats.getOrDefault(uuid, Map.of()).getOrDefault(stat, 0.0);
    }

    public static double getTotalStat(Player player, String stat) {
        JavaPlugin plugin = PlanCEvent.getInstance();
        NamespacedKey key = new NamespacedKey(plugin, stat);
        double base = StatUtils.getPlayerItemStat(player, key);
        double extra = getExtraStat(player.getUniqueId(), stat);
        double defaultValue = defaultStats.getOrDefault(stat, 0.0);
        double points = LevelManager.getAssignedPointsValues(player, stat);

        PlayerStatCalculationEvent event = new PlayerStatCalculationEvent(player, stat, defaultValue + base + extra + points);
        Bukkit.getPluginManager().callEvent(event);
        return event.getModifiedValue();
    }
}
