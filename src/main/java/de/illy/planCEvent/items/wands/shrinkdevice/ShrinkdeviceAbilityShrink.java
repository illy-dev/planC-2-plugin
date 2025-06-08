package de.illy.planCEvent.items.wands.shrinkdevice;

import de.illy.planCEvent.StatSystem.ManaManager;
import de.illy.planCEvent.items.ItemAbility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;


public class ShrinkdeviceAbilityShrink implements ItemAbility {

    @Override
    public void execute(Player player) {
        int sizeLevel = Shrinkdevice.getSizeLevelMap().getOrDefault(player.getUniqueId(), 0);
        if (sizeLevel > -4) {
            sizeLevel--;
            Shrinkdevice.getSizeLevelMap().put(player.getUniqueId(), sizeLevel);
        }
        if (ManaManager.consumeMana(player, 120)) {
            Shrinkdevice.applySize(player, sizeLevel);
            String actionBarText = "§b-120 Mana (§6Shrink§b)";
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText));
        }
    }

    @Override
    public void remove(Player player) {

    }

}
