package de.illy.planCEvent.commands;

import de.illy.planCEvent.items.RelicContainer.RelicContainer;
import de.illy.planCEvent.items.XpBottles.ColossalXpBottle;
import de.illy.planCEvent.items.XpBottles.GrandXpBottle;
import de.illy.planCEvent.items.XpBottles.TitanicXpBottle;
import de.illy.planCEvent.items.weapons.hyperion.Hyperion;
import de.illy.planCEvent.items.weapons.terminator.Terminator;
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
            player.getInventory().addItem(Terminator.create());
            player.getInventory().addItem(Hyperion.create());
            player.getInventory().addItem(GrandXpBottle.create());
            player.getInventory().addItem(TitanicXpBottle.create());
            player.getInventory().addItem(ColossalXpBottle.create());
            player.getInventory().addItem(RelicContainer.create());
            return true;
        }

        return true;
    }
}
