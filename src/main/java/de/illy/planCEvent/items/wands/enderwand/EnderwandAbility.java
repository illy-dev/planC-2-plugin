package de.illy.planCEvent.items.wands.enderwand;

import de.illy.planCEvent.StatSystem.ManaManager;
import de.illy.planCEvent.items.ItemAbility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EnderwandAbility implements ItemAbility {
    @Override
    public void execute(Player player) {
        // +50 speed hinzufügen

        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        double maxDistance = 15.0;
        double stepSize = 0.5;
        Location targetLocation = player.getLocation(); // Fallback

        for (double i = 0; i <= maxDistance; i += stepSize) {
            Location current = eyeLocation.clone().add(direction.clone().multiply(i));
            Block block = current.getBlock();
            Block above = current.clone().add(0, 1, 0).getBlock();

            if (block.isPassable() && above.isPassable()) {
                targetLocation = current;
            } else {
                break;
            }
        }

        if (ManaManager.consumeMana(player, 50)) {
            player.teleport(targetLocation);
            String actionBarText = "§b-50 Mana (§6Instant Transmission§b)";
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText));
        }
    }

    @Override
    public void remove(Player player) {

    }
}
