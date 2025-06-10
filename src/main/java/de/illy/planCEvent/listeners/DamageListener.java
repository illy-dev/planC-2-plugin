package de.illy.planCEvent.listeners;

import de.illy.planCEvent.StatSystem.HealthManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        double damage = event.getDamage();
        HealthManager.calculateDamagePlayer(player, damage);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
        event.setCancelled(true);
    }
}
