package de.illy.planCEvent.events;

import de.illy.planCEvent.util.Inventory.InventoryManager;
import de.illy.planCEvent.util.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

import static de.illy.planCEvent.util.ChatUtil.Praefix;

public class HideAndSeekEvent {
    private final Map<UUID, Location> initialPlayerLocs = new HashMap<>();

    public void startEvent(World eventWorld) {
        int currPlayer = 0;
        initialPlayerLocs.clear();

        List<Player> players = new ArrayList<>(PlayerManager.getPlayers());
        int totalPlayers = players.size();
        Location center = new Location(eventWorld, 400, 44, 1733);


        for (Player p : players) {
            initialPlayerLocs.put(p.getUniqueId(), p.getLocation());

            double angle = Math.toRadians((360.0 / totalPlayers) * currPlayer);
            double dx = Math.sin(angle) * 4;
            double dz = Math.cos(angle) * 4;
            Location eventPlayerLoc = center.clone().add(dx, 0, dz);

            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(eventPlayerLoc);

            currPlayer++;
        }
    }

    public void stopEvent() {

        for (Player p : PlayerManager.getPlayers()) {
            Location returnLoc = initialPlayerLocs.get(p.getUniqueId());

            if (returnLoc != null) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Praefix + ChatColor.BLUE + "Teleporting back...");
                p.teleport(returnLoc);
                InventoryManager.restoreInventory(p);
                InventoryManager.clearSavedInventory(p.getUniqueId());

            }


        }
        initialPlayerLocs.clear();
    }
}
