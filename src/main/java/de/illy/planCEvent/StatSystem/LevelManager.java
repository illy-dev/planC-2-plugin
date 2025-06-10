package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.StatSystem.StatPoints.StatDefinitions;
import de.illy.planCEvent.StatSystem.StatPoints.StatMeta;
import de.illy.planCEvent.util.LevelColor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelManager {
    @Getter
    private static final Map<UUID, Double> levelMap = new HashMap<>();

    @Getter
    private static final Map<UUID, Double> xpMap = new HashMap<>();

    @Getter
    private static final Map<UUID, Integer> pointsLeftMap = new HashMap<>();

    @Getter
    private static final Map<UUID, Map<String, Integer>> assignedPointsMap = new HashMap<>();

    public static double getLevel(Player player) {
        return StatAPI.getTotalStat(player, "stat_level");
    }

    public static double getStoredLevel(Player player) {
        return levelMap.getOrDefault(player.getUniqueId(), 1.0);
    }

    public static double getStoredXp(Player player) {
        return xpMap.getOrDefault(player.getUniqueId(), 0.0);
    }

    public static void setLevel(Player player, double value) {
        UUID id = player.getUniqueId();
        levelMap.put(id, value);
        StatAPI.setExtraStat(id, "stat_level", value);
        updatePointsBasedOnLevel(player);
    }

    public static void addLevel(Player player, double amount) {
        double oldLevel = getStoredLevel(player);
        setLevel(player, oldLevel + amount);
        player.sendMessage("§3§l            LEVEL UP");
        player.sendMessage(LevelColor.getColor((int) getStoredLevel(player))+ "          Level §8" + (int) oldLevel + " → " + "[" + LevelColor.getColor((int)getStoredLevel(player)) + (int) getStoredLevel(player) + "§8]");
        player.sendMessage("");
        player.sendMessage("§6§l            REWARDS");
        player.sendMessage("             §8+§a1 §7Skill Point");
    }


    public static void resetLevel(Player player) {
        setLevel(player, 1);
        xpMap.put(player.getUniqueId(), 0.0);
        StatAPI.setExtraStat(player.getUniqueId(), "stat_xp", 0.0);
    }


    public static double getXp(Player player) {
        return StatAPI.getTotalStat(player, "stat_xp");
    }

    public static void setXp(Player player, double value) {
        UUID id = player.getUniqueId();
        if (value >= 100) {
            int levelsToAdd = (int) (value / 100);
            addLevel(player, levelsToAdd);
        }
        double remainderXp = value % 100;
        xpMap.put(id, remainderXp);
        StatAPI.setExtraStat(id, "stat_xp", remainderXp);
    }



    public static void addXp(Player player, double amount) {
        double currentXp = getStoredXp(player);
        double newTotalXp = currentXp + amount;

        setXp(player, newTotalXp);
    }

    // speichern
    public static void savePlayerData(Player player) {
        UUID id = player.getUniqueId();
        File file = new File(PlanCEvent.getInstance().getDataFolder(), "players/" + id.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("skillPoints.pointsLeft", pointsLeftMap.getOrDefault(id, 0));

        // Save assigned points per stat
        Map<String, Integer> assignedPoints = assignedPointsMap.getOrDefault(id, new HashMap<>());
        for (Map.Entry<String, Integer> entry : assignedPoints.entrySet()) {
            config.set("skillPoints.assigned." + entry.getKey(), entry.getValue());
        }

        config.set("level", levelMap.getOrDefault(id, 1.0));
        config.set("xp", xpMap.getOrDefault(id, 0.0));

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updatePointsBasedOnLevel(Player player) {
        UUID id = player.getUniqueId();
        int level = (int) getStoredLevel(player);
        int totalPoints = level; // 1 point per level

        Map<String, Integer> assigned = assignedPointsMap.getOrDefault(id, new HashMap<>());
        int spentPoints = assigned.values().stream().mapToInt(Integer::intValue).sum();

        int pointsLeft = totalPoints - spentPoints;
        if (pointsLeft < 0) pointsLeft = 0;

        pointsLeftMap.put(id, pointsLeft);
    }

    public static int getPointsLeft(Player player) {
        return (int) LevelManager.getStoredLevel(player) - getTotalAssignedPoints(player);
    }

    public static int getTotalAssignedPoints(Player player) {
        return assignedPointsMap.getOrDefault(player.getUniqueId(), new HashMap<>())
                .values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int getAssignedPoints(Player player, String stat) {
        return assignedPointsMap.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(stat, 0);
    }

    public static double getAssignedPointsValues(Player player, String stat) {
        Map<String, Integer> stats = assignedPointsMap.get(player.getUniqueId());
        StatMeta meta = StatDefinitions.STAT_INFO.get(stat);

        if (stats == null || meta == null) {
            return 0;
        }

        return stats.getOrDefault(stat, 0) * meta.perPoint;
    }


    public static boolean assignPoint(Player player, String stat) {
        int pointsLeft = getPointsLeft(player);
        if (pointsLeft <= 0) return false;

        UUID id = player.getUniqueId();
        assignedPointsMap.putIfAbsent(id, new HashMap<>());
        Map<String, Integer> stats = assignedPointsMap.get(id);

        stats.put(stat, stats.getOrDefault(stat, 0) + 1);

        // Recalculate total and set
        /*
        StatMeta meta = StatDefinitions.STAT_INFO.get(stat);
        if (meta != null) {
            double total = stats.get(stat) * meta.perPoint;
            StatAPI.setExtraStat(id, stat, total);
        }*/

        return true;
    }


    public static boolean removePoint(Player player, String stat) {
        UUID id = player.getUniqueId();
        Map<String, Integer> stats = assignedPointsMap.getOrDefault(id, new HashMap<>());
        int current = stats.getOrDefault(stat, 0);
        if (current <= 0) return false;

        stats.put(stat, current - 1);

        // Recalculate and set
        /*
        StatMeta meta = StatDefinitions.STAT_INFO.get(stat);
        if (meta != null) {
            double total = stats.get(stat) * meta.perPoint;
            StatAPI.setExtraStat(id, stat, total);
        }*/

        return true;
    }



    public static void loadPlayerData(Player player) {
        UUID id = player.getUniqueId();
        File file = new File(PlanCEvent.getInstance().getDataFolder(), "players/" + id.toString() + ".yml");
        if (!file.exists()) return;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        double level = config.getDouble("level", 1.0);
        double xp = config.getDouble("xp", 0.0);

        levelMap.put(id, level);
        xpMap.put(id, xp);

        int pointsLeft = config.getInt("skillPoints.pointsLeft", 0);
        pointsLeftMap.put(id, pointsLeft);

        Map<String, Integer> assignedPoints = new HashMap<>();
        if (config.isConfigurationSection("skillPoints.assigned")) {
            for (String key : config.getConfigurationSection("skillPoints.assigned").getKeys(false)) {
                assignedPoints.put(key, config.getInt("skillPoints.assigned." + key, 0));
            }
        }
        assignedPointsMap.put(id, assignedPoints);
    }

}
