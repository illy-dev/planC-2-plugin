package de.illy.planCEvent.items.RelicContainer;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.items.ItemAbility;
import de.illy.planCEvent.listeners.CaseGUI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class RelicContainerAbility implements ItemAbility {
    @Override
    public void execute(Player player) {
        CaseGUI.openCase(player);

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (IsRelicContainer(itemInHand)) {
            int amount = itemInHand.getAmount();
            if (amount == 1) {
                player.getInventory().setItemInMainHand(null);
            } else {
                itemInHand.setAmount(amount - 1);
            }

        }

    }

    @Override
    public void remove(Player player) {

    }

    private static boolean IsRelicContainer(ItemStack main) {
        if (main == null || !main.hasItemMeta()) return false;

        var meta = main.getItemMeta();
        var container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_relic_container");

        return container.has(key, PersistentDataType.BYTE);
    }
}
