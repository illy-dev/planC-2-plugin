package de.illy.planCEvent.items.weapons.hyperion;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Hyperion {

    public static ItemStack create() {
        return new CustomItemBuilder(Material.IRON_SWORD)
                .addStat("base_damage", 250)
                .addStat("strength", 150)
                .addStat("intelligence", 350)
                .setDisplayName("§6Hyperion")
                .addCustomEnchantmentLore("§7Damage: §c250")
                .addCustomEnchantmentLore("§7Strength: §c150")
                .addCustomEnchantmentLore("§7Intelligence: §a350")
                .setAbilityName("§6Ability: Wither Impact §e§lRIGHT CLICK")
                .addAbilityDescriptionLine("§7Teleport §a10 blocks§7 ahead of you.")
                .addAbilityDescriptionLine("§7then implode dealing §cmassive§7 damage to nearby enemies.")
                .addAbilityDescriptionLine("§7Also applied the wither shield scroll ability")
                .addAbilityDescriptionLine("§7reducing damage taken and granting absorption shield")
                .addAbilityDescriptionLine("§7for §e5 §7seconds.")
                .setAbilityUsage("§8Mana Cost: §3250")
                .setRarity("§6§lLEGENDARY DUNGEON SWORD")
                .setAbility(AbilityTrigger.RIGHT_CLICK)
                .setAbilityClass(HyperionAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }

}
