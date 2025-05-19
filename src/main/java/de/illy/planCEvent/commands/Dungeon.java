package de.illy.planCEvent.commands;

import de.illy.planCEvent.dungeons.generator.DungeonGenerator;
import de.illy.planCEvent.util.Wave.WaveManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Dungeon implements CommandExecutor {

    private final WaveManager waveManager;
    private final DungeonGenerator dungeonGenerator;

    public Dungeon(WaveManager waveManager, DungeonGenerator dungeonGenerator) {
        this.waveManager = waveManager;
        this.dungeonGenerator = dungeonGenerator;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Usage: /dungeon <normal|tower>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "normal" -> {
                player.sendMessage("Generating dungeon...");
                DungeonGenerator generator = new DungeonGenerator();
                generator.generateDungeon(true);

                return true;
            }
            case "clean" -> {
                return true;
            }

            case "tower" -> {
                // TODO: Implement tower generation
                player.sendMessage("Tower dungeon coming soon.");
                return true;
            }

            default -> {
                player.sendMessage("Unknown dungeon type. Use: normal or tower.");
                return true;
            }
        }
    }
}
