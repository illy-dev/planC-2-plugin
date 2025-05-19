package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class MobDeathListener implements Listener {

    private final TowerOfDungeonEvent towerOfDungeonEvent;
    private final JavaPlugin plugin;

    public MobDeathListener(JavaPlugin plugin, TowerOfDungeonEvent towerOfDungeonEvent) {
        this.plugin = plugin;
        this.towerOfDungeonEvent = towerOfDungeonEvent;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        UUID owner = null;
            for (Map.Entry<UUID, List<LivingEntity>> entry : towerOfDungeonEvent.getPlayerMobs().entrySet()) {
                if (entry.getValue().remove(entity)) {
                    owner = entry.getKey();
                    break;
                }
            }

        if (owner != null && towerOfDungeonEvent.getPlayerMobs().get(owner).isEmpty()) {
            Player player = Bukkit.getPlayer(owner);
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }, 1L);
                towerOfDungeonEvent.spawnNextWaveForPlayer(player);
                towerOfDungeonEvent.spawnLootBox(player);
            }
        }
    }
}
