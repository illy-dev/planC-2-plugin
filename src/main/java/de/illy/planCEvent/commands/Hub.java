package de.illy.planCEvent.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Hub implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player) {
            Location hub = new Location(Bukkit.getWorld("hub"), 0, 68, 8);
            ((Player) sender).teleport(hub);
            return true;
        }
        return false;
    }
}
