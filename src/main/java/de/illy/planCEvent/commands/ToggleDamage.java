package de.illy.planCEvent.commands;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

public class ToggleDamage implements CommandExecutor {
    @Getter
    private static boolean enabled = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        enabled = !enabled;
        sender.sendMessage(ChatColor.GREEN + "Custom Damage is now " + (enabled ? "ENABLED" : "DISABLED"));
        return true;
    }
}
