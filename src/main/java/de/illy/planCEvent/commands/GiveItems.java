package de.illy.planCEvent.commands;

import de.illy.planCEvent.items.CoinSystem.Coin;
import de.illy.planCEvent.items.RelicContainer.RelicContainer;
import de.illy.planCEvent.items.XpBottles.ColossalXpBottle;
import de.illy.planCEvent.items.XpBottles.GrandXpBottle;
import de.illy.planCEvent.items.XpBottles.TitanicXpBottle;
import de.illy.planCEvent.items.armor.Druid.DruidArmor;
import de.illy.planCEvent.items.armor.Slimeboots.Slimeboots;
import de.illy.planCEvent.items.armor.Slimeboots.SlimebootsAbility;
import de.illy.planCEvent.items.wands.enderwand.Enderwand;
import de.illy.planCEvent.items.wands.shrinkdevice.Shrinkdevice;
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
            player.getInventory().addItem(Slimeboots.create());
            player.getInventory().addItem(Enderwand.create());
            player.getInventory().addItem(Shrinkdevice.create());

            player.getInventory().addItem(DruidArmor.DRUID_HELMET);
            player.getInventory().addItem(DruidArmor.DRUID_CHESTPLATE);
            player.getInventory().addItem(DruidArmor.DRUID_LEGGINS);
            player.getInventory().addItem(DruidArmor.DRUID_BOOTS);
            player.getInventory().addItem(Coin.create());
            return true;
        }

        return true;
    }
}
