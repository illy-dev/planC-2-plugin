package de.illy.planCEvent.StatSystem;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HealthManager {
    @Getter
    private static final Map<UUID, Double> healthMap = new HashMap<>();

    public static double getMaxHealth(Player player) {
        return StatAPI.getTotalStat(player, "stat_health");
    }

    public static double getHealth(Player player) {
        return healthMap.getOrDefault(player.getUniqueId(), getMaxHealth(player));
    }

    public static void setHealth(Player player, double value) {
        UUID id = player.getUniqueId();
        double max = getMaxHealth(player);
        double clampedValue = Math.min(Math.max(0, value), max);
        healthMap.put(id, clampedValue);

        if (clampedValue <= 0) {
            player.setHealth(0.0);
            resetHealth(player);
        }
    }

    public static void addHealth(Player player, double amount) {
        setHealth(player, getHealth(player) + amount);
    }

    public static boolean consumeHealth(Player player, double amount) {
        double current = getHealth(player);
        if (current >= amount) {
            setHealth(player, current - amount);
            return true;
        }
        player.sendMessage("§cYou don't have enough Health to do that!");
        return false;
    }

    public static void resetHealth(Player player) {
        setHealth(player, getMaxHealth(player));
    }

    public static void remove(Player player) {
        healthMap.remove(player.getUniqueId());
    }

    public static double calculateDamagePlayer(Player player, double damage) {
        double defense = StatAPI.getTotalStat(player, "stat_defense");
        double hp = getHealth(player);

        // default armor
        if (Objects.equals(player.getInventory().getHelmet(), new ItemStack(Material.DIAMOND_HELMET))) {defense += 1;}
        if (Objects.equals(player.getInventory().getChestplate(), new ItemStack(Material.DIAMOND_CHESTPLATE))) {defense += 1;}
        if (Objects.equals(player.getInventory().getLeggings(), new ItemStack(Material.DIAMOND_LEGGINGS))) {defense += 1;}
        if (Objects.equals(player.getInventory().getBoots(), new ItemStack(Material.DIAMOND_BOOTS))) {defense += 1;}
        if (Objects.equals(player.getInventory().getHelmet(), new ItemStack(Material.NETHERITE_HELMET))) {defense += 2;}
        if (Objects.equals(player.getInventory().getChestplate(), new ItemStack(Material.NETHERITE_CHESTPLATE))) {defense += 2;}
        if (Objects.equals(player.getInventory().getLeggings(), new ItemStack(Material.NETHERITE_LEGGINGS))) {defense += 2;}
        if (Objects.equals(player.getInventory().getBoots(), new ItemStack(Material.NETHERITE_BOOTS))) {defense += 2;}

        double damageReduc = defense / (defense + 100);
        double newDamage = damage - (damage * damageReduc);

        setHealth(player, hp - newDamage);
        return newDamage;
    }
}
