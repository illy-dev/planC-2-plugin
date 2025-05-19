package de.illy.planCEvent.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CustomItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private static JavaPlugin plugin;

    private final List<String> enchantmentLore = new ArrayList<>();
    private String abilityName = null;
    private final List<String> abilityDescription = new ArrayList<>();
    private String abilityUsage = null;
    private String rarity = null;

    private de.illy.planCEvent.items.AbilityTrigger trigger = null;
    private Class<? extends de.illy.planCEvent.items.ItemAbility> abilityClass = null;

    public CustomItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public static void setPlugin(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
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

    public CustomItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public CustomItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
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

        if (plugin != null && abilityClass != null && trigger != null) {
            NamespacedKey key = new NamespacedKey(plugin, "item_ability_class");
            NamespacedKey triggerKey = new NamespacedKey(plugin, "item_ability_trigger");

            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, abilityClass.getName());
            meta.getPersistentDataContainer().set(triggerKey, PersistentDataType.STRING, trigger.name());
        }

        item.setItemMeta(meta);
        return item;
    }
}
