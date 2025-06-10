package de.illy.planCEvent.commands;

// start stop info players nextwave

// start:
// in welt tpen
// kein cleanstone u. kein black stained glass abbauen
// 18x18 ding bauen
// jede person in sein ding tpen



import de.illy.planCEvent.events.HideAndSeekEvent;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static de.illy.planCEvent.util.ChatUtil.Praefix;

public class Event implements CommandExecutor {

    private final TowerOfDungeonEvent towerOfDungeonEvent;
    private final HideAndSeekEvent hideAndSeekEvent;

    public Event(TowerOfDungeonEvent towerOfDungeonEvent, HideAndSeekEvent hideAndSeekEvent) {
        this.towerOfDungeonEvent = towerOfDungeonEvent;
        this.hideAndSeekEvent = hideAndSeekEvent;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player) {

            if (!sender.hasPermission("planc.dev")) return false;
            if (sender.hasPermission("event")) {
                if (args.length > 0) {
                    switch (args[0]) {
                        case "start":
                            if (PlayerManager.getEventStatus()) {
                                sender.sendMessage(Praefix + ChatColor.RED + "Event is already running.");
                                break;
                            }
                            if (PlayerManager.getPlayerCount() == 0) {
                                sender.sendMessage(Praefix + ChatColor.RED + "There is no player in the event.");
                                break;
                            }
                            if (args.length < 3) {
                                sender.sendMessage(Praefix + ChatColor.RED + "Usage: /event start <world> <gamemode>");
                                break;
                            }
                            World eventWorld = Bukkit.getWorld(args[1]);

                            if (args[2].equals("TowerOfDungeon")) {
                                if (eventWorld != null) {
                                    try {
                                        PlayerManager.startEvent();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    PlayerManager.setCurrentEvent(PlayerManager.EventType.TOWER_OF_DUNGEON);
                                    towerOfDungeonEvent.startEvent(eventWorld);
                                }
                            }
                            if (args[2].equals("HideAndSeek")) {
                                if (eventWorld != null) {
                                    try {
                                        PlayerManager.startEvent();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    PlayerManager.setCurrentEvent(PlayerManager.EventType.HIDE_AND_SEEK);
                                }
                            }
                            break;

                        case "stop":
                            if (!PlayerManager.getEventStatus()) {
                                sender.sendMessage(Praefix + ChatColor.RED + "No event is running.");
                                break;
                            }
                            PlayerManager.EventType currentEvent = PlayerManager.getCurrentEvent();
                            if ( currentEvent == PlayerManager.EventType.HIDE_AND_SEEK) {
                                hideAndSeekEvent.stopEvent();
                                PlayerManager.setCurrentEvent(PlayerManager.EventType.NONE);
                            } else if ( currentEvent == PlayerManager.EventType.TOWER_OF_DUNGEON) {
                                towerOfDungeonEvent.stopEvent();
                                PlayerManager.setCurrentEvent(PlayerManager.EventType.NONE);
                            }

                            try {
                                PlayerManager.stopEvent();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            sender.sendMessage(Praefix + ChatColor.GREEN + "Event stopped and " + PlayerManager.getPlayerCount() + " players got teleported back");
                            PlayerManager.clearPlayers();
                            break;

                        case "info":
                            sender.sendMessage(ChatColor.BOLD + "EVENT STATUS:");
                            sender.sendMessage("---------------------------");
                            sender.sendMessage("Event running: "  + PlayerManager.getEventStatus());
                            sender.sendMessage("Players in the event: " + PlayerManager.getPlayerCount());
                            sender.sendMessage("---------------------------");
                            break;

                        case "nextwave":
                            if (!PlayerManager.getEventStatus()) {
                                sender.sendMessage(Praefix + ChatColor.RED + "No event is running.");
                                break;
                            }
                            if (towerOfDungeonEvent.allWavesCompleted()) {
                                sender.sendMessage(Praefix + ChatColor.YELLOW + "All waves have been completed.");
                                break;
                            }

                            for (Player p: PlayerManager.getPlayers()) {
                                towerOfDungeonEvent.spawnNextWaveForPlayer(p);
                            }

                            break;
                        case "addPlayer":
                            if (PlayerManager.getEventStatus()) {
                                sender.sendMessage(Praefix + ChatColor.RED + "The Event is already running");
                                break;
                            }
                            if (args.length < 2) {
                                sender.sendMessage(Praefix + ChatColor.RED + "Usage: /event addPlayer <player>");
                                break;
                            }
                            Player targetAdd = Bukkit.getPlayerExact(args[1]);
                            if (targetAdd != null) {
                                if (!PlayerManager.isPlayerInEvent(targetAdd)) {
                                    PlayerManager.addPlayer(targetAdd);
                                    sender.sendMessage(Praefix + ChatColor.GREEN + targetAdd.getName() + " has been added to the event.");
                                    targetAdd.sendMessage(Praefix + ChatColor.GREEN + " You have been added to the event.");
                                } else {
                                    sender.sendMessage(Praefix + ChatColor.YELLOW + targetAdd.getName() + " is already in the event.");
                                }
                            } else {
                                sender.sendMessage(Praefix + ChatColor.RED + "Player not found or not online.");
                            }
                            break;
                        case "removePlayer":
                            if (PlayerManager.getEventStatus()) {
                                sender.sendMessage(Praefix + ChatColor.RED + "The Event is already running");
                                break;
                            }
                            if (args.length < 2) {
                                sender.sendMessage(Praefix + ChatColor.RED + "Usage: /event removePlayer <player>");
                                break;
                            }
                            Player targetRemove = Bukkit.getPlayerExact(args[1]);
                            if (targetRemove != null) {
                                if (PlayerManager.isPlayerInEvent(targetRemove)) {
                                    PlayerManager.removePlayer(targetRemove);
                                    sender.sendMessage(Praefix + ChatColor.RED + targetRemove.getName() + " has been removed from the event.");
                                    targetRemove.sendMessage(Praefix + ChatColor.RED + " You have been removed from the event.");
                                } else {
                                    sender.sendMessage(Praefix + ChatColor.YELLOW + targetRemove.getName() + " is not in the event.");
                                }
                            } else {
                                sender.sendMessage(Praefix + ChatColor.RED + "Player not found or not online.");
                            }
                            break;

                    }
                }

                return true;
            }

            sender.sendMessage(Praefix + ChatColor.RED + "You have no permissions to do that.");
            return true;
        }

        sender.sendMessage("console not allowed");
        return false;
    }
}
