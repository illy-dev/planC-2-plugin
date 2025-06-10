package de.illy.planCEvent.StatSystem;

import org.bukkit.GameMode;
import org.bukkit.entity.*;

import static de.illy.planCEvent.StatSystem.DamageDisplayUtil.showDamage;

public class AbilityDamageHelper {
    public static void dealAbilityDamage(Player caster, LivingEntity target, double baseAbilityDamage, double scaling) {
        if (target instanceof ArmorStand) return;
        if (target instanceof ItemFrame) return;

        DamageCalculator.DamageResult result = DamageCalculator.calculateAbilityDamage(caster, target, baseAbilityDamage, scaling);

        if (target instanceof Player) {
            if (((Player) target).getGameMode() != GameMode.SURVIVAL) return;
            showDamage(target, (int) HealthManager.calculateDamagePlayer((Player) target, result.damage), result.isCrit);
        } else {
            double currentHp = CustomHealthManager.getHealth(target);
            double newHp = Math.max(0, currentHp - result.damage);

            CustomHealthManager.setHealth(target, newHp);

            if (CustomHealthManager.isCustomMob(target)) {
                CustomHealthManager.updateNameTag(target);
            }

            showDamage(target, (int) result.damage, result.isCrit);

            if (newHp <= 0) {
                double bukkitKillDamage = target.getHealth();
                target.damage(bukkitKillDamage, caster);
                CustomHealthManager.remove(target);
            }
        }


    }

}
