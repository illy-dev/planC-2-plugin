package de.illy.planCEvent.util;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Effect;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class RelicHelper {


    public static boolean isRelicItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_relic");
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static void applyRelicEffect(Player player, ItemStack relic) {
        String relicId = getRelicId(relic);
        switch (relicId) {
            case "range_relic":
                player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(
                        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).getBaseValue() - 5
                );
                break;
            case "speed_relic":
                player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).setBaseValue(
                        player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).getBaseValue() - 0.05
                );
                break;
            case "fire_relic":
                player.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 1), true);
            // Add more relics here
        }
    }

    public static void removeRelicEffect(Player player, ItemStack relic) {
        String relicId = getRelicId(relic);
        switch (relicId) {
            case "range_relic":
                player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(
                        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).getBaseValue() - 5
                );
                break;
            case "speed_relic":
                player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).setBaseValue(
                        player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).getBaseValue() - 0.05
                );
                break;
            case "fire_relic":
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
    }

    public static String getRelicId(ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "relic_id");
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "");
    }

    public static ItemStack createRelicItem(JavaPlugin plugin, ItemStack baseItem, String relicId, String displayName) {
        if (!baseItem.hasItemMeta()) return baseItem;
        ItemStack relic = baseItem.clone();
        ItemMeta meta = relic.getItemMeta();

        NamespacedKey keyIsRelic = new NamespacedKey(plugin, "is_relic");
        NamespacedKey keyRelicId = new NamespacedKey(plugin, "relic_id");

        meta.getPersistentDataContainer().set(keyIsRelic, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(keyRelicId, PersistentDataType.STRING, relicId);
        meta.setDisplayName(displayName);

        relic.setItemMeta(meta);
        return relic;
    }
}
