package de.illy.planCEvent.util;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HelixEffect {

    private final Plugin plugin;
    private final Player player;
    private int taskID = -1;

    // Configurable parameters
    private final double radius = 1.0;
    private final double height = 2.0;
    private final int durationTicks = 10 * 20; // 10 seconds
    private final int points = 50;
    private final double angleSpeed = Math.PI / 10;
    Particle.DustOptions goldDust = new Particle.DustOptions(Color.fromRGB(255, 215, 0), 0.4f);
    private final Particle particle = Particle.DUST;

    public HelixEffect(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void start() {
        taskID = new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= durationTicks || !player.isOnline()) {
                    this.cancel();
                    return;
                }

                Location baseLocation = player.getLocation().clone();
                double angle = ticks * angleSpeed;

                for (double y = 0; y <= height; y += height / points) {
                    double currentAngle = angle + y * 4; // spiral effect
                    double x = radius * Math.cos(currentAngle);
                    double z = radius * Math.sin(currentAngle);

                    baseLocation.add(x, y, z);
                    player.getWorld().spawnParticle(particle, baseLocation, 0, 0, 0, 0, 0, goldDust);
                    baseLocation.subtract(x, y, z); // reset for next loop
                }

                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 1L).getTaskId();
    }

    public void stop() {
        if (taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
