package de.illy.planCEvent.items.armor.Slimeboots;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Slimeboots {
    public static ItemStack create() {
        return new CustomItemBuilder(Material.NETHERITE_BOOTS)
                .addStat("strength", 50)
                .addStat("intelligence", 100000)
                .setDisplayName("§5Slime Boots")
                .addCustomEnchantmentLore("§7Strength: §c50")
                .addCustomEnchantmentLore("§7Intelligence: §a5")
                .setAbilityName("§r§7Jump higher and receive no fall damage")
                .addAbilityDescriptionLine("§r§7from heights until §b30 blocks§7.")
                .setRarity("§5§lEPIC BOOTS")
                .setAbility(AbilityTrigger.EQUIP)
                .setAbilityClass(SlimebootsAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }
}
