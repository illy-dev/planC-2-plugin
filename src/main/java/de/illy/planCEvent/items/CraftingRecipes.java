package de.illy.planCEvent.items;

import de.illy.planCEvent.items.armor.Druid.DruidArmor;
import de.illy.planCEvent.items.armor.Slimeboots.Slimeboots;
import de.illy.planCEvent.items.craftingItems.MiscItems;
import de.illy.planCEvent.items.craftingItems.Platine;
import de.illy.planCEvent.items.wands.enderwand.Enderwand;
import de.illy.planCEvent.items.weapons.hyperion.Hyperion;
import de.illy.planCEvent.items.weapons.terminator.Terminator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingRecipes {

    public static void register(JavaPlugin plugin) {
        // empty platine
        NamespacedKey key1 = new NamespacedKey(plugin, "empty_platine");
        ItemStack emptyPlatine = Platine.emptyPlatine;

        ShapedRecipe emptyPlatineRecipe = new ShapedRecipe(key1, emptyPlatine);
        emptyPlatineRecipe.shape("AAA", "AAA", "AAA");
        emptyPlatineRecipe.setIngredient('A', Material.DARK_PRISMARINE_SLAB);
        Bukkit.addRecipe(emptyPlatineRecipe);

        // platine
        NamespacedKey key2 = new NamespacedKey(plugin, "platine");
        ItemStack platine = Platine.platine;

        ShapedRecipe platineRecipe = new ShapedRecipe(key2, platine);
        platineRecipe.shape("ABA", "CDG", "EFE");
        platineRecipe.setIngredient('A', Material.IRON_INGOT);
        platineRecipe.setIngredient('B', Material.REDSTONE_TORCH);
        platineRecipe.setIngredient('C', Material.COMPARATOR);
        platineRecipe.setIngredient('G', Material.REPEATER);
        platineRecipe.setIngredient('D', Material.REDSTONE);
        platineRecipe.setIngredient('E', Material.RESIN_CLUMP);
        platineRecipe.setIngredient('F', emptyPlatine);
        Bukkit.addRecipe(platineRecipe);

        // verstärkte strings
        NamespacedKey key3 = new NamespacedKey(plugin, "ver_strings");
        ItemStack verStrings = MiscItems.verstaerkterString;

        ShapedRecipe verStringsRecipe = new ShapedRecipe(key3, verStrings);
        verStringsRecipe.shape("ABC", "BC*", "C**");
        verStringsRecipe.setIngredient('A', Material.STRING);
        verStringsRecipe.setIngredient('B', Material.DIAMOND);
        verStringsRecipe.setIngredient('C', Material.RESIN_BRICK);
        Bukkit.addRecipe(verStringsRecipe);

        // terminator
        NamespacedKey key4 = new NamespacedKey(plugin, "terminator");
        ItemStack terminator = Terminator.create();

        ShapedRecipe terminatorRecipe = new ShapedRecipe(key4, terminator);
        terminatorRecipe.shape("*AB", "ADC", "BCE");
        terminatorRecipe.setIngredient('A', Material.RESIN_CLUMP);
        terminatorRecipe.setIngredient('B', Material.OBSIDIAN);
        terminatorRecipe.setIngredient('C', verStrings);
        terminatorRecipe.setIngredient('D', Material.BOW);
        terminatorRecipe.setIngredient('E', Material.NETHER_STAR);
        Bukkit.addRecipe(terminatorRecipe);

        // hyperion handle
        NamespacedKey key5 = new NamespacedKey(plugin, "hyperion_handle");
        ItemStack hyperion_handle = MiscItems.hyperionHandle;

        ShapedRecipe handleRecipe = new ShapedRecipe(key5, hyperion_handle);
        handleRecipe.shape("AAA", "BAB", "*A*");
        handleRecipe.setIngredient('A', Material.GOLD_INGOT);
        handleRecipe.setIngredient('B', Material.NETHER_STAR);
        Bukkit.addRecipe(handleRecipe);

        // uncharged hype
        NamespacedKey key6 = new NamespacedKey(plugin, "uncharged_hype");
        ItemStack uncharged_hype = MiscItems.unChargedHype;

        ShapedRecipe unchHypeRecipe = new ShapedRecipe(key6, uncharged_hype);
        unchHypeRecipe.shape("ABC", "DED", "FGF");
        unchHypeRecipe.setIngredient('A', Material.BLAZE_POWDER);
        unchHypeRecipe.setIngredient('B', hyperion_handle);
        unchHypeRecipe.setIngredient('C', Material.GLOWSTONE_DUST);
        unchHypeRecipe.setIngredient('D', Material.RESIN_BRICK);
        unchHypeRecipe.setIngredient('E', Material.HEAVY_CORE);
        unchHypeRecipe.setIngredient('F', Material.COPPER_INGOT);
        unchHypeRecipe.setIngredient('G', Material.GOLD_INGOT);
        Bukkit.addRecipe(unchHypeRecipe);

        // hype
        NamespacedKey key7 = new NamespacedKey(plugin, "hyperion");
        ItemStack hype = Hyperion.create();

        ShapedRecipe hypeRecipe = new ShapedRecipe(key7, hype);
        hypeRecipe.shape("ABC", "DCB", "EDA");
        hypeRecipe.setIngredient('A', Material.DIAMOND_BLOCK);
        hypeRecipe.setIngredient('B', Material.SCULK_CATALYST);
        hypeRecipe.setIngredient('C', Material.NETHER_STAR);
        hypeRecipe.setIngredient('D', Material.ECHO_SHARD);
        hypeRecipe.setIngredient('E', uncharged_hype);
        Bukkit.addRecipe(hypeRecipe);

        // ench feathers
        NamespacedKey key8 = new NamespacedKey(plugin, "ench_feather");
        ItemStack ench_feather = MiscItems.enchFeather;

        ShapedRecipe ench_featherRecipe = new ShapedRecipe(key8, ench_feather);
        ench_featherRecipe.shape("AA*", "AB*", "**C");
        ench_featherRecipe.setIngredient('A', Material.FEATHER);
        ench_featherRecipe.setIngredient('B', Material.LAPIS_LAZULI);
        ench_featherRecipe.setIngredient('C', Material.CRYING_OBSIDIAN);
        Bukkit.addRecipe(ench_featherRecipe);

        // slime boots
        NamespacedKey key9 = new NamespacedKey(plugin, "slime_boots");
        ItemStack slime_boots = Slimeboots.create();

        ShapedRecipe slime_bootsRecipe = new ShapedRecipe(key9, slime_boots);
        slime_bootsRecipe.shape("ABA", "CDC", "EFE");
        slime_bootsRecipe.setIngredient('A', Material.DIAMOND);
        slime_bootsRecipe.setIngredient('B', Material.ECHO_SHARD);
        slime_bootsRecipe.setIngredient('C', ench_feather);
        slime_bootsRecipe.setIngredient('D', Material.NETHERITE_BOOTS);
        slime_bootsRecipe.setIngredient('E', Material.GOLD_INGOT);
        slime_bootsRecipe.setIngredient('F', Material.CHISELED_RESIN_BRICKS);
        Bukkit.addRecipe(slime_bootsRecipe);

        // druid helmet
        NamespacedKey key10 = new NamespacedKey(plugin, "druid_helmet");
        ItemStack druid_helmet = DruidArmor.DRUID_HELMET;

        ShapedRecipe druid_helmetRecipe = new ShapedRecipe(key10, druid_helmet);
        druid_helmetRecipe.shape("ABA", "BCB", "DED");
        druid_helmetRecipe.setIngredient('A', Material.GHAST_TEAR);
        druid_helmetRecipe.setIngredient('B', Material.GLOWSTONE_DUST);
        druid_helmetRecipe.setIngredient('C', Material.DIAMOND_HELMET);
        druid_helmetRecipe.setIngredient('D', Material.RESIN_CLUMP);
        druid_helmetRecipe.setIngredient('E', Material.MOSS_BLOCK);
        Bukkit.addRecipe(druid_helmetRecipe);

        // druid chestplate
        NamespacedKey key11 = new NamespacedKey(plugin, "druid_chestplate");
        ItemStack druid_chestplate = DruidArmor.DRUID_CHESTPLATE;

        ShapedRecipe druid_chestplateRecipe = new ShapedRecipe(key11, druid_chestplate);
        druid_chestplateRecipe.shape("ABA", "BCB", "DED");
        druid_chestplateRecipe.setIngredient('A', Material.GHAST_TEAR);
        druid_chestplateRecipe.setIngredient('B', Material.GLOWSTONE_DUST);
        druid_chestplateRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        druid_chestplateRecipe.setIngredient('D', Material.RESIN_CLUMP);
        druid_chestplateRecipe.setIngredient('E', Material.MOSS_BLOCK);
        Bukkit.addRecipe(druid_chestplateRecipe);

        // druid leggings
        NamespacedKey key12 = new NamespacedKey(plugin, "druid_leggings");
        ItemStack druid_leggings = DruidArmor.DRUID_LEGGINS;

        ShapedRecipe druid_leggingsRecipe = new ShapedRecipe(key12, druid_leggings);
        druid_leggingsRecipe.shape("ABA", "BCB", "DED");
        druid_leggingsRecipe.setIngredient('A', Material.GHAST_TEAR);
        druid_leggingsRecipe.setIngredient('B', Material.GLOWSTONE_DUST);
        druid_leggingsRecipe.setIngredient('C', Material.DIAMOND_LEGGINGS);
        druid_leggingsRecipe.setIngredient('D', Material.RESIN_CLUMP);
        druid_leggingsRecipe.setIngredient('E', Material.MOSS_BLOCK);
        Bukkit.addRecipe(druid_leggingsRecipe);

        // druid boots
        NamespacedKey key13 = new NamespacedKey(plugin, "druid_boots");
        ItemStack druid_boots = DruidArmor.DRUID_BOOTS;

        ShapedRecipe druid_bootsRecipe = new ShapedRecipe(key13, druid_boots);
        druid_bootsRecipe.shape("ABA", "BCB", "DED");
        druid_bootsRecipe.setIngredient('A', Material.GHAST_TEAR);
        druid_bootsRecipe.setIngredient('B', Material.GLOWSTONE_DUST);
        druid_bootsRecipe.setIngredient('C', Material.DIAMOND_BOOTS);
        druid_bootsRecipe.setIngredient('E', Material.RESIN_CLUMP);
        Bukkit.addRecipe(druid_bootsRecipe);

        // iron rod
        NamespacedKey key14 = new NamespacedKey(plugin, "iron_rod");
        ItemStack iron_rod = MiscItems.ironRod;

        ShapedRecipe iron_rodRecipe = new ShapedRecipe(key14, iron_rod);
        iron_rodRecipe.shape("B**", "A**", "B**");
        iron_rodRecipe.setIngredient('A', Material.STICK);
        iron_rodRecipe.setIngredient('B', Material.IRON_INGOT);
        Bukkit.addRecipe(iron_rodRecipe);

        // enderwand
        NamespacedKey key15 = new NamespacedKey(plugin, "enderwand");
        ItemStack enderwand = Enderwand.create();

        ShapedRecipe enderwandRecipe = new ShapedRecipe(key15, enderwand);
        enderwandRecipe.shape("*AB", "CDA", "DC*");
        enderwandRecipe.setIngredient('A', Material.AMETHYST_SHARD);
        enderwandRecipe.setIngredient('B', Material.ENDER_EYE);
        enderwandRecipe.setIngredient('C', Material.DIAMOND);
        enderwandRecipe.setIngredient('D', iron_rod);
        Bukkit.addRecipe(enderwandRecipe);

    }
}
