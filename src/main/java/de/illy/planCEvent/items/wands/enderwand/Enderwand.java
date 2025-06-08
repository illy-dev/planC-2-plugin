package de.illy.planCEvent.items.wands.enderwand;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import de.illy.planCEvent.items.weapons.hyperion.HyperionAbility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Enderwand {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.WOODEN_SWORD)
                .addStat("base_damage", 100)
                .addStat("strength", 100)
                .setDisplayName("§5Ender Wand")
                .addCustomEnchantmentLore("§7Damage: §c100")
                .addCustomEnchantmentLore("§7Strength: §c100")
                .setAbilityName("§6Ability: Instant Transmission  §e§lRIGHT CLICK")
                .addAbilityDescriptionLine("§7Teleport §a15 blocks§7 ahead of you")
                .addAbilityDescriptionLine("§7and gain §a+50 §f✦Speed for §a3 seconds§7.")
                .setAbilityUsage("§8Mana Cost: §350")
                .setRarity("§5§lEPIC WAND")
                .setAbility(AbilityTrigger.RIGHT_CLICK)
                .setAbilityClass(EnderwandAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }
}
