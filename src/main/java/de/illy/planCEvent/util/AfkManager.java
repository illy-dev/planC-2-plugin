package de.illy.planCEvent.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AfkManager {
    private final HashMap<UUID, Long> lastActivity = new HashMap<>();

    public void updateActivity(Player player) {
        lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public boolean isAfk(Player player) {
        long last = lastActivity.getOrDefault(player.getUniqueId(), 0L);
        return System.currentTimeMillis() - last > 5 * 60 * 1000;
    }

}
