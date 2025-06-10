package de.illy.planCEvent.util;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MagnetRelicTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!RelicHelper.playerHasRelic(player, "magnet")) continue;

            player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5).stream()
                    .filter(entity -> entity instanceof org.bukkit.entity.Item)
                    .forEach(entity -> {
                        org.bukkit.entity.Item item = (org.bukkit.entity.Item) entity;
                        item.setPickupDelay(0);
                        item.setVelocity(player.getLocation().toVector().subtract(item.getLocation().toVector()).normalize().multiply(0.4));
                    });
        }
    }

    public static void start() {
        new MagnetRelicTask().runTaskTimer(PlanCEvent.getInstance(), 0L, 10L); // every 0.5s
    }
}

