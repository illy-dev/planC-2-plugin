package de.illy.planCEvent.dungeons;

import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.util.Wave.WaveManager;
import org.bukkit.entity.Player;

import java.util.*;

public class DungeonManager {
    private static final Map<UUID, TowerOfDungeonEvent> activeDungeons = new HashMap<>();

    public static void startDungeonForPlayer(Player player, WaveManager waveManager) {
        UUID uuid = player.getUniqueId();
        if (activeDungeons.containsKey(uuid)) {
            player.sendMessage("§cYou are already in a dungeon!");
            return;
        }

        //TowerOfDungeonEvent dungeonEvent = new TowerOfDungeonEvent(waveManager, player, activeDungeons.size());
        TowerOfDungeonEvent dungeonEvent = new TowerOfDungeonEvent(waveManager);
        activeDungeons.put(uuid, dungeonEvent);

        dungeonEvent.startEvent(player.getWorld());
    }

    public static TowerOfDungeonEvent getDungeon(Player player) {
        return activeDungeons.get(player.getUniqueId());
    }

    public static void endDungeon(Player player) {
        activeDungeons.remove(player.getUniqueId());
    }

    public static Collection<TowerOfDungeonEvent> getAllDungeons() {
        return activeDungeons.values();
    }
}
