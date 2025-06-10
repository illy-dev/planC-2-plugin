package de.illy.planCEvent.commands;

import de.illy.planCEvent.util.HelixEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class spawnParticles implements CommandExecutor {
    private final Plugin plugin;

    public spawnParticles(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("planc.dev")) return false;
        if (sender instanceof Player player) {
            new HelixEffect(plugin, player).start();
        }
        return false;
    }
}
