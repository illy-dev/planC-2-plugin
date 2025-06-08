package de.illy.planCEvent.items.weapons.hyperion;

import de.illy.planCEvent.StatSystem.AbilityDamageHelper;
import de.illy.planCEvent.StatSystem.ManaManager;
import de.illy.planCEvent.commands.ToggleDamage;
import de.illy.planCEvent.items.ItemAbility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
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
        if (ManaManager.consumeMana(player, 250)) {
            player.teleport(targetLocation);

            double radius = 5.0;
            double damage = 30.0;
            int hitCount = 0;

            List<Entity> nearby = player.getNearbyEntities(radius, radius, radius);
            for (Entity entity : nearby) {
                if (entity instanceof LivingEntity && entity != player && !(entity instanceof ArmorStand)) {
                    if (!ToggleDamage.isEnabled()) {((LivingEntity) entity).damage(damage, player);} else {
                        AbilityDamageHelper.dealAbilityDamage(player, (LivingEntity) entity, 250, 1);
                    }
                    hitCount++;
                }
            }

            World world = player.getWorld();
            player.getWorld().spawnParticle(
                    Particle.EXPLOSION,
                    player.getLocation(),
                    10, 2, 2, 2, 0.1 // count, offsetX, offsetY, offsetZ, speed
            );
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

            String actionBarText = "§b-250 Mana (§6Wither Impact§b)";
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText));
        }
    }

    @Override
    public void remove(Player player) {}
}
