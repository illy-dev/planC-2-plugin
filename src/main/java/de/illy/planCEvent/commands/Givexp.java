package de.illy.planCEvent.commands;

import de.illy.planCEvent.StatSystem.LevelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Givexp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!commandSender.hasPermission("planc.dev")) return false;
        if (commandSender instanceof Player player) {
            if (args[0].equals("add")) {
                LevelManager.addXp(player, Double.parseDouble(args[1]));
            } else if (args[0].equals("reset")) {
                LevelManager.resetLevel(player);
                player.sendMessage("reset to " + LevelManager.getStoredLevel(player) + " and XP: " + LevelManager.getStoredXp(player));
            }

        }
        return false;
    }
}
