package de.illy.planCEvent.util;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerManager {
    public enum EventType {
        NONE, // No event is running
        HIDE_AND_SEEK,
        TOWER_OF_DUNGEON
    }


    private static boolean isRunning = false;
    private static EventType currentEvent = EventType.NONE;
    private static final Set<UUID> playerUUIDs = new HashSet<>();
    private static final Set<UUID> disqualifiedPlayers = new HashSet<>();

    public static void disqualifyPlayer(UUID uuid) {
        disqualifiedPlayers.add(uuid);
    }

    public static boolean isDisqualified(UUID uuid) {
        return disqualifiedPlayers.contains(uuid);
    }

    public static void clearDisqualifiedPlayers() {
        disqualifiedPlayers.clear();
    }
    public static void startEvent() throws SQLException {
        isRunning = true;
        DbManager.setEventRunning(1);
    }

    public static void stopEvent() throws SQLException {
        isRunning = false;
        DbManager.setEventRunning(0);
    }

    public static boolean getEventStatus() {
        return isRunning;
    }

    public static EventType getCurrentEvent() {
        return currentEvent;
    }

    public static void setCurrentEvent(EventType event) {
        currentEvent = event;
    }

    public static void addPlayer(Player player) {
        playerUUIDs.add(player.getUniqueId());
    }

    public static void removePlayer(Player player) {
        playerUUIDs.remove(player.getUniqueId());
    }

    public static Set<Player> getPlayers() {
        return playerUUIDs.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static int getPlayerCount() {
        return (int) playerUUIDs.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .count();
    }

    public static boolean isPlayerInEvent(Player player) {
        return playerUUIDs.contains(player.getUniqueId());
    }

    public static void clearPlayers() {
        playerUUIDs.clear();
    }

    public static Set<UUID> getAllEventPlayerUUIDs() {
        return Collections.unmodifiableSet(playerUUIDs);
    }
}
