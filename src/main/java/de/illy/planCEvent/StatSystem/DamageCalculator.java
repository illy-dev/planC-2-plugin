package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class DamageCalculator {

    private static final Random random = new Random();

    public static DamageResult calculateWeaponDamage(Player player, LivingEntity target, double weaponBaseDamage) {
        // Stats
        double strength = StatAPI.getTotalStat(player, "stat_strength");
        double critChance = StatAPI.getTotalStat(player, "stat_crit_chance");
        double critDamage = StatAPI.getTotalStat(player, "stat_crit_damage");

        if (isUsingTerminator(player)) {
            critChance /= 4.0;
        }

        player.sendMessage("§l----------------- MOB DMG DEBUG -----------------");
        player.sendMessage("PLAYER > §2" + player.getName());
        player.sendMessage("TARGET > §4" + target.getName());
        player.sendMessage("basedamage > " + weaponBaseDamage);
        player.sendMessage("critchance > " + critChance);
        player.sendMessage("critdamage > " + critDamage);
        player.sendMessage("Strength > " + strength);

        double additive = StatAPI.getTotalStat(player, "stat_additive_damage_multiplier"); // ex: +0.2 for +20%
        double multiplicative = StatAPI.getTotalStat(player, "stat_multiplicative_damage_multiplier"); // ex: 1.5 for 1.5x
        double bonus = StatAPI.getTotalStat(player, "stat_bonus_damage");

        // Crit logic
        boolean isCrit = random.nextDouble() < critChance / 100.0;
        double critMultiplier = 1 + (isCrit ? critDamage : 0) / 100.0;

        // Base formula
        double rawDamage = ((5 + weaponBaseDamage) * (1 + strength / 100.0) * (1 + additive) * (multiplicative + 1) + bonus) * critMultiplier;
        player.sendMessage("raw > " + String.valueOf(rawDamage));
        // Defense reduction
        double defense = getEntityDefense(target);
        double finalDamage = applyDefenseReduction(rawDamage, defense);

        player.sendMessage("final >> " + String.valueOf(finalDamage));
        return new DamageResult(finalDamage, isCrit);
    }

    public static DamageResult calculateAbilityDamage(Player player, LivingEntity target, double baseAbilityDamage, double scaling) {
        double intelligence = StatAPI.getTotalStat(player, "stat_intelligence");
        double additive = StatAPI.getTotalStat(player, "stat_additive_damage_multiplier");
        double multiplicative = StatAPI.getTotalStat(player, "stat_multiplicative_damage_multiplier");
        double bonus = StatAPI.getTotalStat(player, "stat_bonus_damage");

        // Base formula (no crits for abilities by default)
        double rawDamage = baseAbilityDamage * (1 + (intelligence / 100.0) * scaling) * (1 + additive) * (multiplicative + 1) + bonus;

        double defense = getEntityDefense(target);
        double finalDamage = applyDefenseReduction(rawDamage, defense);

        return new DamageResult(finalDamage, false);
    }

    private static boolean isUsingTerminator(Player player) {
        ItemStack main = player.getInventory().getItemInMainHand();
        if (main == null || !main.hasItemMeta()) return false;

        var meta = main.getItemMeta();
        var container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_terminator");

        return container.has(key, PersistentDataType.BYTE);
    }


    private static double applyDefenseReduction(double damage, double defense) {
        if (defense <= 0) return damage;
        return damage * (1 - (defense / (defense + 100.0)));
    }

    private static double getEntityDefense(LivingEntity entity) {
        if (entity instanceof Player player) {
            return StatAPI.getTotalStat(player, "stat_defense");
        }

        // TODO: Add mob-specific defense logic if needed
        return 0.0;
    }

    public static class DamageResult {
        public final double damage;
        public final boolean isCrit;

        public DamageResult(double damage, boolean isCrit) {
            this.damage = damage;
            this.isCrit = isCrit;
        }
    }
}
