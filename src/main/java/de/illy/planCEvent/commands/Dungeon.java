package de.illy.planCEvent.commands;

import de.illy.planCEvent.dungeons.generator.DungeonGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Dungeon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        if (!sender.hasPermission("planc.dev")) return false;

        World world = Bukkit.getWorld("dungeon");

        try {
            long seed = args.length > 0 ? Long.parseLong(args[0]) : System.currentTimeMillis();
            DungeonGenerator generator = new DungeonGenerator(
                    world, 64
            );
            generator.generate(seed);
            player.sendMessage("§aDungeon generated successfully!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid seed. Please use a number.");
        } catch (Exception e) {
            player.sendMessage("§cFailed to generate dungeon: " + e.getMessage());
        }

        return true;
    }
}