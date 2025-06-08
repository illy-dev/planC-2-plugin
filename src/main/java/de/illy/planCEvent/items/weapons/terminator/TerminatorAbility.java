package de.illy.planCEvent.items.weapons.terminator;


import de.illy.planCEvent.items.ItemAbility;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class TerminatorAbility implements ItemAbility {

    private static final long COOLDOWN_MS = 300; // 0.3 seconds
    private static final Map<Player, Long> cooldowns = new HashMap<>();

    @Override
    public void execute(Player player) {
        long now = System.currentTimeMillis();
        if (cooldowns.containsKey(player) && now - cooldowns.get(player) < COOLDOWN_MS) {
            return;
        }

        cooldowns.put(player, now);

        World world = player.getWorld();
        Location eyeLocation = player.getEyeLocation();
        Vector baseDirection = eyeLocation.getDirection().normalize();

        double spreadAngle = 5.0;

        for (int i = 0; i < 3; i++) {
            int finalI = i;

            new BukkitRunnable() {
                @Override
                public void run() {
                    Vector direction = baseDirection.clone();

                    // Left, center, right arrows
                    if (finalI == 0) {
                        direction = rotateVector(direction, -spreadAngle);
                    } else if (finalI == 2) {
                        direction = rotateVector(direction, spreadAngle);
                    }

                    Arrow arrow = world.spawnArrow(
                            eyeLocation.clone().add(direction.clone().multiply(1)),
                            direction,
                            3.0f, // speed
                            0f    // spread
                    );

                    arrow.setShooter(player);
                    arrow.setCritical(true);
                    arrow.setDamage(0.0);
                    arrow.setPickupStatus(Arrow.PickupStatus.CREATIVE_ONLY);

                    arrow.setMetadata("term_arrow", new FixedMetadataValue(
                            Bukkit.getPluginManager().getPlugin("PlanCEvent"), true
                    ));

                    world.spawnParticle(Particle.CRIT, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
                    //player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
                }
            }.runTaskLater(Bukkit.getPluginManager().getPlugin("PlanCEvent"), i * 1L);
        }
    }

    @Override
    public void remove(Player player) {}

    // 2D horizontal rotation
    private Vector rotateVector(Vector vector, double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double x = vector.getX();
        double z = vector.getZ();

        double newX = x * cos - z * sin;
        double newZ = x * sin + z * cos;

        return new Vector(newX, vector.getY(), newZ).normalize();
    }
}

