package de.illy.planCEvent.StatSystem;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManaManager {
    @Getter
    private static final Map<UUID, Double> manaMap = new HashMap<>();


    public static double getMaxMana(Player player) {
        return 100 + StatAPI.getTotalStat(player, "stat_intelligence");
    }


    public static double getMana(Player player) {
        return manaMap.getOrDefault(player.getUniqueId(), getMaxMana(player));
    }

    public static void setMana(Player player, double value) {
        UUID id = player.getUniqueId();
        double max = getMaxMana(player);
        manaMap.put(id, Math.min(Math.max(0, value), max));
    }

    public static void addMana(Player player, double amount) {
        setMana(player, getMana(player) + amount);
    }

    public static boolean consumeMana(Player player, double amount) {
        double current = getMana(player);
        if (current >= amount) {
            setMana(player, current - amount);
            return true;
        }
        player.sendMessage("§cYou don't have enough Mana to do that!");
        return false;
    }

    public static void resetMana(Player player) {
        setMana(player, getMaxMana(player));
    }

    public static void remove(Player player) {
        manaMap.remove(player.getUniqueId());
    }
}
