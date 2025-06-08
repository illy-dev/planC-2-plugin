package de.illy.planCEvent.listeners;

import de.illy.planCEvent.items.armor.Slimeboots.SlimebootsAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamageListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!SlimebootsAbility.hasFallDamageImmunity(player)) return;

        if (player.getFallDistance() <= 30.0) {
            e.setCancelled(true);
        }
    }
}
