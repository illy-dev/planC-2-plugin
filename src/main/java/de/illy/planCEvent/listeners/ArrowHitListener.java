package de.illy.planCEvent.listeners;

import de.illy.planCEvent.StatSystem.CustomDamageHelper;
import de.illy.planCEvent.commands.ToggleDamage;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowHitListener implements Listener {

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player shooter)) return;
        if (!(event.getHitEntity() instanceof LivingEntity target)) return;
        if (!ToggleDamage.isEnabled()) return;

        if (arrow.hasMetadata("term_arrow")) {
            CustomDamageHelper.dealDamage(shooter.getPlayer(), target, 210);
        }

        // Cleanup arrow
        arrow.remove();
    }
}
