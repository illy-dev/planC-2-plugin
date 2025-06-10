package de.illy.planCEvent.listeners;

import de.illy.planCEvent.util.AfkManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerActivityListener implements Listener {
    private final AfkManager afkManager;

    public PlayerActivityListener(AfkManager afkManager) {
        this.afkManager = afkManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().distanceSquared(e.getTo()) > 0) {
            afkManager.updateActivity(e.getPlayer());
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        afkManager.updateActivity(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        afkManager.updateActivity(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        afkManager.updateActivity(e.getPlayer());
    }
}
