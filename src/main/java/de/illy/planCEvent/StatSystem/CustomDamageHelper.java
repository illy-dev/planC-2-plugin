package de.illy.planCEvent.StatSystem;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static de.illy.planCEvent.StatSystem.DamageDisplayUtil.showDamage;

public class CustomDamageHelper {
    public static void dealDamage(Player caster, LivingEntity target, double weaponBaseDamage) {
        if (target instanceof ArmorStand) return;
        if (target instanceof ItemFrame) return;

        DamageCalculator.DamageResult result = DamageCalculator.calculateWeaponDamage(caster, target, weaponBaseDamage);

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
