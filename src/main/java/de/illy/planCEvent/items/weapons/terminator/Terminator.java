package de.illy.planCEvent.items.weapons.terminator;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Terminator {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.BOW)
                .setDisplayName("§6Terminator")
                .addStat("strength", 50)
                .addStat("crit_damage", 250)
                .addStat("base_damage", 210)
                .addStat("crit_chance", 100)
                .addCustomEnchantmentLore("§7Damage: §c+210")
                .addCustomEnchantmentLore("§7Strength: §c+50")
                .addCustomEnchantmentLore("§7Crit Damage: §c+250%")
                .addCustomEnchantmentLore("§7Bonus Attack Speed: §c+40%")
                .addCustomEnchantmentLore("§7Shot Cooldown: §a0.3s")
                .addCustomEnchantmentLore("")
                .addCustomEnchantmentLore("§7Shoots §b3§7 arrows at once.")
                .addCustomEnchantmentLore("§7Can damage endermen.")
                .addCustomEnchantmentLore("")
                .addCustomEnchantmentLore("§cDivides your §9☣Crit Chance §cby 4!")
                .setAbilityName("§6Shortbow: Instantly shoots!")
                .setRarity("§6§lLEGENDARY BOW")
                .setAbility(AbilityTrigger.LEFT_CLICK)
                .setAbilityClass(TerminatorAbility.class)
                .setUnbreakable(true)
                .addCheckEnchantment()
                .addTag("is_terminator")
                .build();
    }
}
