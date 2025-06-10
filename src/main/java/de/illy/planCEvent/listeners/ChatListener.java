package de.illy.planCEvent.listeners;

import de.illy.planCEvent.StatSystem.LevelManager;
import de.illy.planCEvent.util.LevelColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        int playerLevel = (int) LevelManager.getStoredLevel(player);
        String prefix = "§8[" + LevelColor.getColor(playerLevel) + playerLevel + "§8]§7 ";
        String playerName = event.getPlayer().getDisplayName();
        event.setFormat(prefix + playerName + ": " + event.getMessage());
    }
}
