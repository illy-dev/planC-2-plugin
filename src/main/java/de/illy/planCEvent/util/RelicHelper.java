package de.illy.planCEvent.util;

import de.illy.planCEvent.PlanCEvent;
import lombok.Getter;
import org.bukkit.Effect;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.*;


public class RelicHelper {
    private static final Map<UUID, Integer> playersWithCoinRelic = new HashMap<>();
    private static final Map<String, Set<UUID>> relicPlayerMap = new HashMap<>();

    static {
        relicPlayerMap.put("void", new HashSet<>());
        relicPlayerMap.put("phantom", new HashSet<>());
        relicPlayerMap.put("rage", new HashSet<>());
        relicPlayerMap.put("magnet", new HashSet<>());
    }



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
            case "fire_relic":
                player.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 1), true);
                break;
            case "void_relic":
                addRelic(player, "void");
                break;
            case "phantom_relic":
                addRelic(player, "phantom");
                break;
            case "coin_relic":
                addCoinRelic(player);
                break;
            case "rage_relic":
                addRelic(player, "rage");
                break;
            case "magnet_relic":
                addRelic(player, "magnet");
                break;
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
            case "fire_relic":
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                break;
            case "void_relic":
                removeRelic(player, "void");
                break;
            case "phantom_relic":
                removeRelic(player, "phantom");
                break;
            case "coin_relic":
                removeCoinRelic(player);
                break;
            case "rage_relic":
                removeRelic(player, "rage");
                break;
            case "magnet_relic":
                removeRelic(player, "magnet");
                break;
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

    public static int getCoinRelicCount(Player player) {
        return playersWithCoinRelic.getOrDefault(player.getUniqueId(), 0);
    }

    public static void addCoinRelic(Player player) {
        UUID uuid = player.getUniqueId();
        playersWithCoinRelic.put(uuid, playersWithCoinRelic.getOrDefault(uuid, 0) + 1);
    }

    public static void removeCoinRelic(Player player) {
        UUID uuid = player.getUniqueId();
        int current = playersWithCoinRelic.getOrDefault(uuid, 0);
        if (current <= 1) {
            playersWithCoinRelic.remove(uuid);
        } else {
            playersWithCoinRelic.put(uuid, current - 1);
        }
    }

    public static boolean playerHasRelic(Player player, String relicType) {
        Set<UUID> set = relicPlayerMap.get(relicType);
        return set != null && set.contains(player.getUniqueId());
    }

    public static void addRelic(Player player, String relicType) {
        relicPlayerMap.computeIfAbsent(relicType, k -> new HashSet<>()).add(player.getUniqueId());
    }

    public static void removeRelic(Player player, String relicType) {
        Set<UUID> set = relicPlayerMap.get(relicType);
        if (set != null) {
            set.remove(player.getUniqueId());
        }
    }

}
