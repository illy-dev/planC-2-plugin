package de.illy.planCEvent.listeners;

import de.illy.planCEvent.StatSystem.CustomHealthManager;
import de.illy.planCEvent.commands.ToggleDamage;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerEventListener implements Listener {

    private final TowerOfDungeonEvent dungeonEvent;

    public PlayerEventListener(TowerOfDungeonEvent event) {
        this.dungeonEvent = event;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (PlayerManager.isPlayerInEvent(player)) {
            dungeonEvent.markPlayerAsEliminated(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (PlayerManager.getEventStatus() && PlayerManager.isPlayerInEvent(player)) {
            PlayerManager.disqualifyPlayer(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (ToggleDamage.isEnabled()) {
            CustomHealthManager.getMaxHealth(player);
        }

        if (PlayerManager.isDisqualified(uuid)) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("§cYou have been disqualified for disconnecting during the event.");
            Bukkit.broadcastMessage("§c" + player.getName() + " has been disqualified for disconnecting during the event.");
        }
    }

}
