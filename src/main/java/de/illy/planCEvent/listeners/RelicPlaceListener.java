package de.illy.planCEvent.listeners;

import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class RelicPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (RelicHelper.isRelicItem(event.getItemInHand())) {
            event.setCancelled(true);
        }
    }
}
