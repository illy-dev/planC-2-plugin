package de.illy.planCEvent.commands;

import de.illy.planCEvent.items.weapons.hyperion.Hyperion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveItems implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.getInventory().addItem(Hyperion.create());
            return true;
        }

        return true;
    }
}
