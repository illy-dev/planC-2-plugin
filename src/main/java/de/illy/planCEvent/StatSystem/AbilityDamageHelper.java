package de.illy.planCEvent.StatSystem;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static de.illy.planCEvent.StatSystem.DamageDisplayUtil.showDamage;

public class AbilityDamageHelper {
    public static void dealAbilityDamage(Player caster, LivingEntity target, double baseAbilityDamage, double scaling) {
        DamageCalculator.DamageResult result = DamageCalculator.calculateAbilityDamage(caster, target, baseAbilityDamage, scaling);

        double currentHp = CustomHealthManager.getHealth(target);
        double newHp = Math.max(0, currentHp - result.damage);

        CustomHealthManager.setHealth(target, newHp);

        if (CustomHealthManager.isCustomMob(target)) {
            CustomHealthManager.updateNameTag(target);
        }

        showDamage(target, (int) result.damage, result.isCrit);

        if (newHp <= 0) {
            target.setHealth(0);
            CustomHealthManager.remove(target);
        }
    }

}
