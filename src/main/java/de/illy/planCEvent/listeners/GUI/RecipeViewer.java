package de.illy.planCEvent.listeners.GUI;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeViewer {
    public void openRecipeViewer(Player player, ItemStack result, List<ItemStack> ingredients) {
        Inventory gui = Bukkit.createInventory(null, 54, "Crafting Recipe");

        // Show ingredients (up to 9) in a 3x3 grid layout
        int[] ingredientSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
        for (int i = 0; i < Math.min(ingredients.size(), ingredientSlots.length); i++) {
            ItemStack ingredient = ingredients.get(i);
            if (ingredient != null) {
                ItemStack display = ingredient.clone();
                ItemMeta meta = display.getItemMeta();
                if (meta != null && (!meta.hasDisplayName() || meta.getDisplayName().isEmpty())) {
                    meta.setDisplayName("§f" + WordUtils.capitalizeFully(ingredient.getType().toString().replace("_", " ")));
                    display.setItemMeta(meta);
                }
                gui.setItem(ingredientSlots[i], display);
            }
        }

        gui.setItem(25, result.clone());

        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        gui.setItem(48, backArrow);

        // crafting table
        ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingTableMeta = craftingTable.getItemMeta();
        craftingTableMeta.setDisplayName("§aCrafting Table");
        craftingTable.setItemMeta(craftingTableMeta);

        gui.setItem(23, craftingTable);

        // Close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        gui.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        Set<Integer> reserved = new HashSet<>();
        reserved.addAll(Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30));

        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null && !reserved.contains(i) ) {
                gui.setItem(i, filler);
            }
        }

        player.openInventory(gui);
    }

}
