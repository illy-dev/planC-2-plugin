package de.illy.planCEvent.listeners;

import de.illy.planCEvent.util.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static de.illy.planCEvent.util.ChatUtil.Praefix;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!PlayerManager.isPlayerInEvent(player)) return;

        Material type = event.getBlock().getType();

        if (type == Material.STONE || type == Material.BLACK_STAINED_GLASS) {
            event.setCancelled(true);
            player.sendMessage(Praefix + ChatColor.RED + "You can't break that block during the event!");
        }
    }
}
