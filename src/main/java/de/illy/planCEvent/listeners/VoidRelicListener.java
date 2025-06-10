package de.illy.planCEvent.listeners;

import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class VoidRelicListener implements Listener {
    private final Plugin plugin;

    public VoidRelicListener(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        if (RelicHelper.playerHasRelic(killer, "void")) {
            spawnVoidBlackHole(event.getEntity().getLocation(), killer);
        }
    }

    private void spawnVoidBlackHole(Location center, Player killer) {
        int durationTicks = 60;
        double radius = 4;

        new BukkitRunnable() {
            int ticksLived = 0;

            @Override
            public void run() {
                if (ticksLived > durationTicks) {
                    cancel();
                    return;
                }

                for (int i = 0; i < 5; i++) {
                    double angle = Math.toRadians(ticksLived * 15 + i * 72);
                    double x = Math.cos(angle) * 0.5;
                    double z = Math.sin(angle) * 0.5;
                    Location particleLoc = center.clone().add(x, 0.5 + 0.02 * ticksLived, z);
                    Particle.DustOptions blackDust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1f);
                    center.getWorld().spawnParticle(Particle.DUST, particleLoc, 0, 0, 0, 0, blackDust);
                }

                for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                    if (entity instanceof LivingEntity && entity != killer) {
                        Vector direction = center.toVector().subtract(entity.getLocation().toVector());

                        if (direction.lengthSquared() > 0) {
                            Vector pullVector = direction.normalize().multiply(0.2);
                            entity.setVelocity(entity.getVelocity().add(pullVector));
                        }
                    }
                }

                ticksLived++;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}
