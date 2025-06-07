package de.illy.planCEvent.listeners;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.ItemAbility;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemAbilityListener implements Listener {

    private final JavaPlugin plugin;

    public ItemAbilityListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        var meta = item.getItemMeta();
        var container = meta.getPersistentDataContainer();

        NamespacedKey classKey = new NamespacedKey(plugin, "item_ability_class");
        NamespacedKey triggerKey = new NamespacedKey(plugin, "item_ability_trigger");

        if (!container.has(classKey, PersistentDataType.STRING)
                || !container.has(triggerKey, PersistentDataType.STRING)) return;

        AbilityTrigger configuredTrigger = AbilityTrigger.valueOf(
                container.get(triggerKey, PersistentDataType.STRING)
        );

        boolean matches = switch (configuredTrigger) {
            case RIGHT_CLICK -> event.getAction().toString().contains("RIGHT");
            case LEFT_CLICK -> event.getAction().toString().contains("LEFT");
            case SHIFT_RIGHT_CLICK -> event.getAction().toString().contains("RIGHT") && event.getPlayer().isSneaking();
        };

        if (!matches) return;

        try {
            String className = container.get(classKey, PersistentDataType.STRING);
            Class<?> clazz = Class.forName(className);
            if (!ItemAbility.class.isAssignableFrom(clazz)) return;

            ItemAbility ability = (ItemAbility) clazz.getDeclaredConstructor().newInstance();
            ability.execute(event.getPlayer());

            event.setCancelled(true); // Prevent default action (e.g., attacking)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
