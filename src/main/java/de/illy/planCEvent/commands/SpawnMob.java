package de.illy.planCEvent.commands;

import de.illy.planCEvent.StatSystem.CustomHealthManager;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SpawnMob implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length < 3) {
            player.sendMessage("§cUsage: /spawnmob <EntityType> <Name> <MaxHP>");
            return true;
        }

        try {
            EntityType type = EntityType.valueOf(args[0].toUpperCase());
            String name = args[1];
            double maxHp = Double.parseDouble(args[2]);

            Location loc = player.getLocation();
            LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(loc, type);
            entity.setInvulnerable(true);
            entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1000);
            entity.setHealth(1000);
            CustomHealthManager.createCustomMob(entity, name, maxHp);
            CustomHealthManager.resetHealth(entity);
            player.sendMessage("§aSpawned custom mob: " + name + " with " + maxHp + " HP.");

        } catch (IllegalArgumentException e) {
            player.sendMessage("§cInvalid entity type or number.");
        }

        return true;
    }
}
