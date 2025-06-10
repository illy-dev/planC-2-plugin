package de.illy.planCEvent.listeners;

import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class PhantomRelicListener implements Listener {
    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) return;

        Player target = (Player) event.getTarget();
        if (!RelicHelper.playerHasRelic(target, "phantom")) return;

        World world = target.getWorld();
        long time = world.getTime();
        boolean isNight = (time > 13000 && time < 23000);

        if (isNight) {
            event.setCancelled(true);
        }
    }
}
