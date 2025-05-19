package de.illy.planCEvent.items.weapons.hyperion;

import de.illy.planCEvent.items.ItemAbility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class HyperionAbility implements ItemAbility {

    @Override
    public void execute(Player player) {

        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        double maxDistance = 10.0;
        double stepSize = 0.5;
        Location targetLocation = player.getLocation(); // Fallback

        for (double i = 0; i <= maxDistance; i += stepSize) {
            Location current = eyeLocation.clone().add(direction.clone().multiply(i));
            Block block = current.getBlock();
            Block above = current.clone().add(0, 1, 0).getBlock();

            if (block.isPassable() && above.isPassable()) {
                targetLocation = current;
            } else {
                break; // Stop at first obstruction
            }
        }

        player.teleport(targetLocation);

        double radius = 5.0;
        double damage = 30.0;
        int hitCount = 0;

        List<Entity> nearby = player.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearby) {
            if (entity instanceof LivingEntity && entity != player) {
                ((LivingEntity) entity).damage(damage, player);
                hitCount++;
            }
        }

        World world = player.getWorld();
        player.getWorld().spawnParticle(
                Particle.EXPLOSION,
                player.getLocation(),
                10, 2, 2, 2, 0.1 // count, offsetX, offsetY, offsetZ, speed
        );

        player.sendMessage("§7Your Implosion hit §c" + hitCount + " §7enemies for §c" + (int) damage + " §7damage!");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
    }
}
