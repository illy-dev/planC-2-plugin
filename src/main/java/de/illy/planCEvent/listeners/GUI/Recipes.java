package de.illy.planCEvent.listeners.GUI;

import de.illy.planCEvent.items.craftingItems.MiscItems;
import de.illy.planCEvent.items.craftingItems.Platine;
import de.illy.planCEvent.util.GetHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Recipes {
    public static List<ItemStack> ingredientsEmptyPlatine = Arrays.asList(
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB),
            new ItemStack(Material.DARK_PRISMARINE_SLAB)
    );

    public static List<ItemStack> ingredientsPlatine = Arrays.asList(
            new ItemStack(Material.IRON_INGOT),
            new ItemStack(Material.REDSTONE_TORCH),
            new ItemStack(Material.IRON_INGOT),
            new ItemStack(Material.COMPARATOR),
            new ItemStack(Material.REDSTONE),
            new ItemStack(Material.REPEATER),
            new ItemStack(Material.RESIN_CLUMP),
            Platine.emptyPlatine,
            new ItemStack(Material.RESIN_CLUMP)
    );

    public static List<ItemStack> ingredientsGameboy = Arrays.asList(
            new ItemStack(Material.WHITE_TERRACOTTA),
            new ItemStack(Material.NETHER_STAR),
            new ItemStack(Material.WHITE_TERRACOTTA),
            new ItemStack(Material.STONE_BUTTON),
            Platine.platine,
            new ItemStack(Material.STONE_BUTTON),
            new ItemStack(Material.WHITE_TERRACOTTA),
            new ItemStack(Material.REDSTONE_BLOCK),
            new ItemStack(Material.WHITE_TERRACOTTA)
    );

    public static List<ItemStack> ingredientsVerStrings = Arrays.asList(
            new ItemStack(Material.STRING),
            new ItemStack(Material.DIAMOND),
            new ItemStack(Material.RESIN_BRICK),
            new ItemStack(Material.DIAMOND),
            new ItemStack(Material.RESIN_BRICK),
            new ItemStack(Material.AIR),
            new ItemStack(Material.RESIN_BRICK),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR)
    );

    public static List<ItemStack> ingredientsTerminator = Arrays.asList(
            new ItemStack(Material.AIR),
            new ItemStack(Material.RESIN_CLUMP),
            new ItemStack(Material.OBSIDIAN),
            new ItemStack(Material.RESIN_CLUMP),
            new ItemStack(Material.BOW),
            MiscItems.verstaerkterString,
            new ItemStack(Material.OBSIDIAN),
            MiscItems.verstaerkterString,
            new ItemStack(Material.NETHER_STAR)
    );

    public static List<ItemStack> ingredientsHyperionHandle = Arrays.asList(
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.NETHER_STAR),
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.NETHER_STAR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.AIR)
    );

    public static List<ItemStack> ingredientsUnchHyperion = Arrays.asList(
            new ItemStack(Material.BLAZE_POWDER),
            new ItemStack(Material.NETHERITE_SWORD),
            new ItemStack(Material.GLOWSTONE_DUST),
            new ItemStack(Material.RESIN_BRICK),
            new ItemStack(Material.HEAVY_CORE),
            new ItemStack(Material.RESIN_BRICK),
            new ItemStack(Material.COPPER_INGOT),
            MiscItems.hyperionHandle,
            new ItemStack(Material.COPPER_INGOT)
    );

    public static List<ItemStack> ingredientsHyperion = Arrays.asList(
            new ItemStack(Material.DIAMOND_BLOCK),
            new ItemStack(Material.SCULK_CATALYST),
            new ItemStack(Material.NETHER_STAR),
            new ItemStack(Material.ECHO_SHARD),
            new ItemStack(Material.NETHER_STAR),
            new ItemStack(Material.SCULK_CATALYST),
            MiscItems.unChargedHype,
            new ItemStack(Material.ECHO_SHARD),
            new ItemStack(Material.DIAMOND_BLOCK)
    );

    public static List<ItemStack> ingredientsEnchFeather = Arrays.asList(
            new ItemStack(Material.FEATHER),
            new ItemStack(Material.FEATHER),
            new ItemStack(Material.AIR),
            new ItemStack(Material.FEATHER),
            new ItemStack(Material.LAPIS_LAZULI),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.CRYING_OBSIDIAN)
            );

    public static List<ItemStack> ingredientsSlimeBoots = Arrays.asList(
            new ItemStack(Material.DIAMOND),
            new ItemStack(Material.ECHO_SHARD),
            new ItemStack(Material.DIAMOND),
            MiscItems.enchFeather,
            new ItemStack(Material.NETHERITE_BOOTS),
            MiscItems.enchFeather,
            new ItemStack(Material.GOLD_INGOT),
            new ItemStack(Material.CHISELED_RESIN_BRICKS),
            new ItemStack(Material.GOLD_INGOT)
    );


    public static List<ItemStack> ingredientsDruidArmor = Arrays.asList(
            new ItemStack(Material.GHAST_TEAR),
            new ItemStack(Material.GLOWSTONE_DUST),
            new ItemStack(Material.GHAST_TEAR),
            new ItemStack(Material.GLOWSTONE_DUST),
            GetHead.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I1ZmFmNGNkODcxMzhjODcxY2M2YTg2NzU4MTdhODk5ODVhM2NiODk3MjFhNGM3NjJmZTY2NmZmNjE4MWMyNCJ9fX0=", "§lDiamond Armor Piece"),
            new ItemStack(Material.GLOWSTONE_DUST),
            new ItemStack(Material.RESIN_CLUMP),
            new ItemStack(Material.MOSS_BLOCK),
            new ItemStack(Material.RESIN_CLUMP)
    );

    public static List<ItemStack> ingredientsIronRod = Arrays.asList(
            new ItemStack(Material.IRON_INGOT),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.STICK),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.IRON_INGOT),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR)
    );

    public static List<ItemStack> ingredientsEnderwand = Arrays.asList(
            new ItemStack(Material.AIR),
            new ItemStack(Material.AMETHYST_SHARD),
            new ItemStack(Material.ENDER_EYE),
            new ItemStack(Material.DIAMOND),
            MiscItems.ironRod,
            new ItemStack(Material.AMETHYST_SHARD),
            MiscItems.ironRod,
            new ItemStack(Material.DIAMOND),
            new ItemStack(Material.AIR)
    );
}
