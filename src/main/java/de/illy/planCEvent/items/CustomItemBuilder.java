package de.illy.planCEvent.items;

import de.illy.planCEvent.PlanCEvent;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemBuilder {

    private ItemStack item;
    private ItemMeta meta;
    @Setter
    private static JavaPlugin plugin;

    private final List<String> enchantmentLore = new ArrayList<>();
    private String abilityName = null;
    private final List<String> abilityDescription = new ArrayList<>();
    private String abilityUsage = null;
    private String rarity = null;
    private final Map<String, Double> stats = new HashMap<>();

    private de.illy.planCEvent.items.AbilityTrigger trigger = null;
    private Class<? extends de.illy.planCEvent.items.ItemAbility> abilityClass = null;

    private Class<? extends de.illy.planCEvent.items.ItemAbility> bonusClass = null;
    private de.illy.planCEvent.items.AbilityTrigger bonusTrigger = null;


    public CustomItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public CustomItemBuilder(ItemStack existingItem) {
        this.item = existingItem.clone();
        this.meta = this.item.getItemMeta();
    }

    public CustomItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public CustomItemBuilder addCustomEnchantmentLore(String line) {
        enchantmentLore.add(line);
        return this;
    }

    public CustomItemBuilder setAbilityName(String name) {
        this.abilityName = name;
        return this;
    }

    public CustomItemBuilder addAbilityDescriptionLine(String line) {
        this.abilityDescription.add(line);
        return this;
    }

    public CustomItemBuilder setAbilityUsage(String usage) {
        this.abilityUsage = usage;
        return this;
    }

    public CustomItemBuilder setRarity(String rarity) {
        this.rarity = rarity;
        return this;
    }

    public CustomItemBuilder addCheckEnchantment() {
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public CustomItemBuilder setRelicId(String relicId) {
        NamespacedKey relicKey = new NamespacedKey(PlanCEvent.getInstance(), "relic_id");
        meta.getPersistentDataContainer().set(relicKey, PersistentDataType.STRING, relicId);
        return this;
    }

    public CustomItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public CustomItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public CustomItemBuilder setAbility(de.illy.planCEvent.items.AbilityTrigger trigger) {
        this.trigger = trigger;
        return this;
    }

    public CustomItemBuilder setAbilityClass(Class<? extends de.illy.planCEvent.items.ItemAbility> abilityClass) {
        this.abilityClass = abilityClass;
        return this;
    }

    public CustomItemBuilder setBonusClass(Class<? extends de.illy.planCEvent.items.ItemAbility> setBonusClass) {
        this.bonusClass = setBonusClass;
        return this;
    }

    public CustomItemBuilder setBonusTrigger(de.illy.planCEvent.items.AbilityTrigger trigger) {
        this.bonusTrigger = trigger;
        return this;
    }


    public CustomItemBuilder addStat(String statName, double value) {
        stats.put(statName.toLowerCase(), value);
        return this;
    }

    public CustomItemBuilder addTag(String key) {
        if (plugin != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BYTE, (byte) 1);
        }
        return this;
    }

    public ItemStack build() {
        List<String> lore = new ArrayList<>(enchantmentLore);

        if (abilityName != null || !abilityDescription.isEmpty()) {
            lore.add("");
        }

        if (abilityName != null) {
            lore.add(abilityName);
        }

        lore.addAll(abilityDescription);

        if (abilityUsage != null) {
            lore.add(abilityUsage);
        }

        if (rarity != null) {
            lore.add("");
            lore.add(rarity);
        }

        meta.setLore(lore);

        if (plugin != null) {
            stats.forEach((key, value) -> {
                NamespacedKey statKey = new NamespacedKey(plugin, "stat_" + key);
                meta.getPersistentDataContainer().set(statKey, PersistentDataType.DOUBLE, value);
            });
        }

        if (plugin != null && abilityClass != null && trigger != null) {
            NamespacedKey key = new NamespacedKey(plugin, "item_ability_class");
            NamespacedKey triggerKey = new NamespacedKey(plugin, "item_ability_trigger");

            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, abilityClass.getName());
            meta.getPersistentDataContainer().set(triggerKey, PersistentDataType.STRING, trigger.name());
        }

        if (plugin != null && bonusClass != null && bonusTrigger != null) {
            NamespacedKey setBonusKey = new NamespacedKey(plugin, "item_bonus_ability_class");
            NamespacedKey setBonusTriggerKey = new NamespacedKey(plugin, "item_bonus_ability_trigger");

            meta.getPersistentDataContainer().set(setBonusKey, PersistentDataType.STRING, bonusClass.getName());
            meta.getPersistentDataContainer().set(setBonusTriggerKey, PersistentDataType.STRING, bonusTrigger.name());
        }


        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
