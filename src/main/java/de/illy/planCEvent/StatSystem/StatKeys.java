package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.NamespacedKey;

public class StatKeys {
    private static final PlanCEvent plugin = PlanCEvent.getInstance();

    public static final NamespacedKey HEALTH = new NamespacedKey(plugin, "stat_health");
    public static final NamespacedKey STRENGTH = new NamespacedKey(plugin, "stat_strength");
    public static final NamespacedKey CRIT_CHANCE = new NamespacedKey(plugin, "stat_crit_chance");
    public static final NamespacedKey CRIT_DAMAGE = new NamespacedKey(plugin, "stat_crit_damage");

    public static final NamespacedKey MAGIC_DAMAGE = new NamespacedKey(plugin, "stat_magic_damage");
    public static final NamespacedKey INTELLIGENCE = new NamespacedKey(plugin, "stat_intelligence");
    public static final NamespacedKey REGEN_AMOUNT_MANA = new NamespacedKey(plugin, "stat_regen_amount_mana");
    public static final NamespacedKey REGEN_INTERVAL_MANA = new NamespacedKey(plugin, "stat_regen_interval_mana");

    public static final NamespacedKey BASE_DMG = new NamespacedKey(plugin, "stat_base_damage");
    public static final NamespacedKey DEFENSE = new NamespacedKey(plugin, "stat_defense");
    public static final NamespacedKey ADDITIVE_DAMAGE_MULTIPLIER = new NamespacedKey(plugin, "stat_additive_damage_multiplier");
    public static final NamespacedKey MULTIPLICATIVE_DAMAGE_MULTIPLIER = new NamespacedKey(plugin, "stat_multiplicative_damage_multiplier");
    public static final NamespacedKey BONUS_DAMAGE = new NamespacedKey(plugin, "stat_bonus_damage");
}
