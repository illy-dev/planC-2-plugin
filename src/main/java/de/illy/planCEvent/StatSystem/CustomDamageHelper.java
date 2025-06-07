package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.StatSystem.StatAPI;
import de.illy.planCEvent.StatSystem.CustomHealthManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static de.illy.planCEvent.StatSystem.DamageDisplayUtil.showDamage;

public class CustomDamageHelper {
    public static void dealDamage(Player caster, LivingEntity target, double weaponBaseDamage) {
        DamageCalculator.DamageResult result = DamageCalculator.calculateWeaponDamage(caster, target, weaponBaseDamage);

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
