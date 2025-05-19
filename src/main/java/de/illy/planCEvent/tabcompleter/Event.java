package de.illy.planCEvent.tabcompleter;

import de.illy.planCEvent.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Event implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender.hasPermission("event")) {
            if (args.length == 1) {
                ArrayList<String> availableArgs = new ArrayList<>();
                availableArgs.add("start");
                availableArgs.add("stop");
                availableArgs.add("nextwave");
                availableArgs.add("info");
                availableArgs.add("addPlayer");
                availableArgs.add("removePlayer");
                return availableArgs;
            } else if (args.length == 2) {
                ArrayList<String> addPlayers = new ArrayList<>();
                if (args[0].equals("addPlayer")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!PlayerManager.isPlayerInEvent(p))
                            addPlayers.add(p.getName());
                    }
                    return addPlayers;
                }

                ArrayList<String> removePlayers = new ArrayList<>();
                if (args[0].equals("removePlayer")) {
                    for (Player p: PlayerManager.getPlayers()) {
                        removePlayers.add(p.getName());
                    }
                    return removePlayers;
                }

                ArrayList<String> worlds = new ArrayList<>();
                if (args[0].equals("start")) {
                    for (World w: Bukkit.getWorlds()) {
                        worlds.add(w.getName());
                    }
                    return worlds;
                }

            } else if (args.length == 3) {
                ArrayList<String> GameModes = new ArrayList<>();
                GameModes.add("HideAndSeek");
                GameModes.add("TowerOfDungeon");
                if (args[0].equals("start")) {
                    return GameModes;
                }
            }
        }
        return List.of();
    }
}
