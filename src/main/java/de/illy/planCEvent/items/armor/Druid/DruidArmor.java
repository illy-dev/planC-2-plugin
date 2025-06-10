package de.illy.planCEvent.items.armor.Druid;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DruidArmor {
    public static final ItemStack DRUID_HELMET;
    public static final ItemStack DRUID_CHESTPLATE;
    public static final ItemStack DRUID_LEGGINS;
    public static final ItemStack DRUID_BOOTS;

    static {
        DRUID_HELMET = new CustomItemBuilder(Material.DIAMOND_HELMET)
                .addStat("health", 20)
                .addStat("strength", 5)
                .addStat("intelligence", 10)
                .setDisplayName("§9Druid Helmet")
                .addCustomEnchantmentLore("§7Health: §a+20")
                .addCustomEnchantmentLore("§7Strength: §c+5")
                .addCustomEnchantmentLore("§7Intelligence: §a+10")
                .setAbilityName("§r§6Full Set Bonus: Forest Blessing")
                .addAbilityDescriptionLine("§r§7Regenerate faster in §aforest biomes§7.")
                .setRarity("§9§lRARE HELMET")
                .setBonusTrigger(AbilityTrigger.FULL_SET)
                .setBonusClass(DruidArmorAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
        DRUID_CHESTPLATE = new CustomItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addStat("health", 20)
                .addStat("strength", 5)
                .addStat("intelligence", 10)
                .setDisplayName("§9Druid Chestplate")
                .addCustomEnchantmentLore("§7Health: §a+20")
                .addCustomEnchantmentLore("§7Strength: §c+5")
                .addCustomEnchantmentLore("§7Intelligence: §a+10")
                .setAbilityName("§r§6Full Set Bonus: Forest Blessing")
                .addAbilityDescriptionLine("§r§7Regenerate faster in §aforest biomes§7.")
                .setRarity("§9§lRARE CHESTPLATE")
                .setBonusTrigger(AbilityTrigger.FULL_SET)
                .setBonusClass(DruidArmorAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
        DRUID_LEGGINS = new CustomItemBuilder(Material.DIAMOND_LEGGINGS)
                .addStat("health", 20)
                .addStat("strength", 5)
                .addStat("intelligence", 10)
                .setDisplayName("§9Druid Leggings")
                .addCustomEnchantmentLore("§7Health: §a+20")
                .addCustomEnchantmentLore("§7Strength: §c+5")
                .addCustomEnchantmentLore("§7Intelligence: §a+10")
                .setAbilityName("§r§6Full Set Bonus: Forest Blessing")
                .addAbilityDescriptionLine("§r§7Regenerate faster in §aforest biomes§7.")
                .setRarity("§9§lRARE LEGGINGS")
                .setBonusTrigger(AbilityTrigger.FULL_SET)
                .setBonusClass(DruidArmorAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
        DRUID_BOOTS = new CustomItemBuilder(Material.DIAMOND_BOOTS)
                .addStat("health", 20)
                .addStat("strength", 5)
                .addStat("intelligence", 10)
                .setDisplayName("§9Druid Boots")
                .addCustomEnchantmentLore("§7Health: §a+20")
                .addCustomEnchantmentLore("§7Strength: §c+5")
                .addCustomEnchantmentLore("§7Intelligence: §a+10")
                .setAbilityName("§r§6Full Set Bonus: Forest Blessing")
                .addAbilityDescriptionLine("§r§7Regenerate faster in §aforest biomes§7.")
                .setRarity("§9§lRARE BOOTS")
                .setBonusTrigger(AbilityTrigger.FULL_SET)
                .setBonusClass(DruidArmorAbility.class)
                .addCheckEnchantment()
                .setUnbreakable(true)
                .build();
    }
}
