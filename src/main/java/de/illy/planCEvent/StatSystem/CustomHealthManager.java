package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.StatSystem.StatAPI;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomHealthManager {

    private static final Map<UUID, Double> healthMap = new HashMap<>();
    private static final Map<UUID, Double> maxHealthMap = new HashMap<>();
    private static final Map<UUID, String> customNameMap = new HashMap<>();

    public static boolean isCustomMob(LivingEntity entity) {
        return healthMap.containsKey(entity.getUniqueId());
    }

    public static void createCustomMob(LivingEntity entity, String name, double maxHp) {
        UUID id = entity.getUniqueId();
        healthMap.put(id, maxHp);
        maxHealthMap.put(id, maxHp);
        customNameMap.put(id, name);
        updateNameTag(entity);
    }

    public static void damage(LivingEntity entity, double amount) {
        UUID id = entity.getUniqueId();
        double current = healthMap.getOrDefault(id, 0.0);
        double newHealth = Math.max(0, current - amount);
        healthMap.put(id, newHealth);
        updateNameTag(entity);
    }

    public static boolean isDead(LivingEntity entity) {
        return healthMap.getOrDefault(entity.getUniqueId(), 0.0) <= 0;
    }

    public static void updateNameTag(LivingEntity entity) {
        UUID id = entity.getUniqueId();
        String name = customNameMap.getOrDefault(id, "Mob");
        double current = healthMap.getOrDefault(id, 0.0);
        double max = maxHealthMap.getOrDefault(id, 0.0);

        String tag = String.format("§c%s §7[§a%.0f§7/§c%.0f§7]", name, current, max);
        entity.setCustomName(tag);
        entity.setCustomNameVisible(true);
    }

    public static void remove(LivingEntity entity) {
        UUID id = entity.getUniqueId();
        healthMap.remove(id);
        maxHealthMap.remove(id);
        customNameMap.remove(id);
        entity.setCustomName(null);
        entity.setCustomNameVisible(false);
    }

    public static double getMaxHealth(LivingEntity entity) {
        if (isCustomMob(entity)) {
            return maxHealthMap.getOrDefault(entity.getUniqueId(), 0.0);
        }

        if (entity instanceof Player player) {
            return 1000 + StatAPI.getTotalStat(player, "stat_health");
        } else {
            return entity.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 2;
        }
    }

    public static double getHealth(LivingEntity entity) {
        if (isCustomMob(entity)) {
            return healthMap.getOrDefault(entity.getUniqueId(), 0.0);
        }
        return getMaxHealth(entity);
    }

    public static void setHealth(LivingEntity entity, double value) {
        if (isCustomMob(entity)) {
            healthMap.put(entity.getUniqueId(), Math.max(0, value));
            updateNameTag(entity);
        }
    }

    public static void resetHealth(LivingEntity entity) {
        if (isCustomMob(entity)) {
            setHealth(entity, getMaxHealth(entity));
        }
    }
}
