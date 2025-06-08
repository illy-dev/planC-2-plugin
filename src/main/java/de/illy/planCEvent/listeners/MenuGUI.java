package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.StatSystem.StatAPI;
import de.illy.planCEvent.util.GetHead;
import de.illy.planCEvent.util.RelicHelper;
import de.illy.planCEvent.util.RelicManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuGUI implements Listener {
    private Inventory mainMenu;
    private Inventory relicMenu;
    private Inventory statsMenu;
    private static final int[] RELIC_SLOTS = {20, 22, 24};
    RelicManager relicManager = PlanCEvent.getInstance().getRelicManager();

    private void openRelicMenu(Player player) {
        relicMenu = Bukkit.createInventory(null, 54, "Relics");

        List<ItemStack> relics = relicManager.loadRelics(player);

        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        relicMenu.setItem(48, backArrow);
        // relic slots

        for (int i = 0; i < RELIC_SLOTS.length; i++) {
            ItemStack relic = null;

            if (relics.size() > i) {
                relic = relics.get(i);
            }

            relicMenu.setItem(RELIC_SLOTS[i], relic != null ? addRemoveLore(relic) : createPlaceholderPane());
        }

        /*
        ItemStack relicslot = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta relicMeta = relicslot.getItemMeta();
        relicMeta.setDisplayName("§cRelic Slot");
        relicslot.setItemMeta(relicMeta);

        relicMenu.setItem(20, relicslot);
        relicMenu.setItem(22, relicslot);
        relicMenu.setItem(24, relicslot);*/

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        relicMenu.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < relicMenu.getSize(); i++) {
            if (relicMenu.getItem(i) == null) {
                relicMenu.setItem(i, filler);
            }
        }
        player.openInventory(relicMenu);
    }

    private boolean isRelicSlot(int slot) {
        for (int i : RELIC_SLOTS) {
            if (i == slot) return true;
        }
        return false;
    }

    private void openStatsMenu(Player player) {
        statsMenu = Bukkit.createInventory(null, 54, "Your Equipment and Stats");

        // main hand
        if (player.getInventory().getItemInMainHand().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Hand Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(2, emptyPiece);
        } else {
            statsMenu.setItem(2, player.getInventory().getItemInMainHand());
        }

        // off hand
        if (player.getInventory().getItemInOffHand().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Offhand Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(47, emptyPiece);
        } else {
            statsMenu.setItem(47, player.getInventory().getItemInOffHand());
        }

        // helmet
        if (player.getInventory().getHelmet().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Helmet Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(11, emptyPiece);
        } else {
            statsMenu.setItem(11, player.getInventory().getHelmet());
        }

        // chestplate
        if (player.getInventory().getChestplate().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Chestplate Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(20, emptyPiece);
        } else {
            statsMenu.setItem(20, player.getInventory().getChestplate());
        }

        // leggings
        if (player.getInventory().getLeggings().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Leggings Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(29, emptyPiece);
        } else {
            statsMenu.setItem(29, player.getInventory().getLeggings());
        }

        // boots
        if (player.getInventory().getBoots().equals(new ItemStack(Material.AIR))) {
            ItemStack emptyPiece = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta emptyPieceMeta = emptyPiece.getItemMeta();
            emptyPieceMeta.setDisplayName("§7Empty Boots Slot");
            emptyPiece.setItemMeta(emptyPieceMeta);

            statsMenu.setItem(38, emptyPiece);
        } else {
            statsMenu.setItem(38, player.getInventory().getBoots());
        }

        // combat stats
        List<String> combatstatsLore = new ArrayList<>();
        ItemStack combatstats = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta combatstatsMeta = combatstats.getItemMeta();
        combatstatsMeta.setDisplayName("§cCombat Stats");
        combatstatsLore.add(" ");
        combatstatsLore.add("§c❤ Health §f" + StatAPI.getTotalStat(player, "stat_health"));
        combatstatsLore.add("§a❈ Defense §f" + StatAPI.getTotalStat(player, "stat_defense"));
        combatstatsLore.add("§c❁ Strength §f" + StatAPI.getTotalStat(player, "stat_strength"));
        combatstatsLore.add("§b✎ Intelligence §f" + StatAPI.getTotalStat(player, "stat_intelligence"));
        combatstatsLore.add("§9☣ Crit Chance §f" + StatAPI.getTotalStat(player, "stat_crit_chance") + "%");
        combatstatsLore.add("§9☠ Crit Damage §f" + StatAPI.getTotalStat(player, "stat_crit_damage") + "%");
        combatstatsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        combatstatsMeta.setLore(combatstatsLore);
        combatstats.setItemMeta(combatstatsMeta);
        statsMenu.setItem(15, combatstats);

        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        statsMenu.setItem(48, backArrow);


        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        statsMenu.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < statsMenu.getSize(); i++) {
            if (statsMenu.getItem(i) == null) {
                statsMenu.setItem(i, filler);
            }
        }
        player.openInventory(statsMenu);
    }

    public void openMainMenu(Player player) {
        mainMenu = Bukkit.createInventory(null, 54, "Menu");

        // player stats button
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setDisplayName("§r" + player.getPlayerListName() + "§7's stats");
        skullMeta.setOwner(player.getName());
        playerHead.setItemMeta(skullMeta);
        mainMenu.setItem(13, playerHead);

        // skills button
        List<String> loreskills = new ArrayList<>();
        ItemStack skills = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta skillsMeta = skills.getItemMeta();
        skillsMeta.setDisplayName("§aYour Skills");
        loreskills.add("§7View your Skill progression and");
        loreskills.add("§7rewards");
        loreskills.add("");
        loreskills.add("§60.0§7 Skill Avg.");
        loreskills.add("");
        loreskills.add("§eClick to view!");
        skillsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillsMeta.setLore(loreskills);
        skills.setItemMeta(skillsMeta);
        mainMenu.setItem(21, skills);

        // level button
        String levelHeadBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkODg1YjMyYjBkZDJkNmI3ZjFiNTgyYTM0MTg2ZjhhNTM3M2M0NjU4OWEyNzM0MjMxMzJiNDQ4YjgwMzQ2MiJ9fX0=";
        List<String> lorelevel = new ArrayList<>();
        ItemStack level = GetHead.getCustomHead(levelHeadBase64, "§aLeveling");
        ItemMeta levelMeta = level.getItemMeta();
        lorelevel.add("§7Your Level: [§b214§7]");
        lorelevel.add("");
        lorelevel.add("§7Determine how far you've");
        lorelevel.add("§7progressed and earn rewards");
        lorelevel.add("§7from completing unique tasks.");
        lorelevel.add("");
        lorelevel.add("§7Progress to Level 215:");
        lorelevel.add("§3────────────§f────  §b55§3/§b100 XP");
        lorelevel.add("");
        lorelevel.add("§eClick to view!");
        levelMeta.setLore(lorelevel);
        level.setItemMeta(levelMeta);
        mainMenu.setItem(22, level);

        // recipes button
        List<String> lorerecipes = new ArrayList<>();
        ItemStack recipes = new ItemStack(Material.BOOK);
        ItemMeta recipesMeta = recipes.getItemMeta();
        recipesMeta.setDisplayName("§aRecipe Book");
        lorerecipes.add("§7Trough your adventure, you will");
        lorerecipes.add("§7unlock recipes for all kinds of");
        lorerecipes.add("§7special items! You can view how to");
        lorerecipes.add("§7craft these items here.");
        lorerecipes.add("");
        lorerecipes.add("§7Recipe Book Unlocked: §e100.0§6%");
        lorerecipes.add("§2────────§f────────  §e23§6/§e23");
        lorerecipes.add("");
        lorerecipes.add("§eClick to view!");
        recipesMeta.setLore(lorerecipes);
        recipes.setItemMeta(recipesMeta);
        mainMenu.setItem(23, recipes);

        // enchantingtable button
        List<String> loreenchanting = new ArrayList<>();
        ItemStack enchanting = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta enchantingMeta = enchanting.getItemMeta();
        enchantingMeta.setDisplayName("§aEnchanting Table");
        loreenchanting.add("§7Opens the enchanting menu.");
        loreenchanting.add("");
        loreenchanting.add("§eClick to open!");
        enchantingMeta.setLore(loreenchanting);
        enchanting.setItemMeta(enchantingMeta);
        mainMenu.setItem(30, enchanting);

        // craftingtable button
        List<String> lorecrafting = new ArrayList<>();
        ItemStack crafting = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingMeta = crafting.getItemMeta();
        craftingMeta.setDisplayName("§aCrafting Table");
        lorecrafting.add("§7Opens the crafting grid.");
        lorecrafting.add("");
        lorecrafting.add("§eClick to open!");
        craftingMeta.setLore(lorecrafting);
        crafting.setItemMeta(craftingMeta);
        mainMenu.setItem(31, crafting);

        // relicmenu button
        String relicHeadBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTlmM2M4ZjQ4MzIxZTY2OGNjYWM1MzAwZDdkYzliOTJhM2ZiZTNkZWNhMmEzOGRkODE5MWM2OGQyNTFhNDAxNCJ9fX0=";
        List<String> lorerelic = new ArrayList<>();
        ItemStack relic = GetHead.getCustomHead(relicHeadBase64, "§aRelics");
        ItemMeta relicMeta = relic.getItemMeta();
        lorerelic.add("§7View and manage all your Relics.");
        lorerelic.add("");
        lorerelic.add("§7Activate your Relics!");
        lorerelic.add("");
        lorerelic.add("§eClick to view!");
        relicMeta.setLore(lorerelic);
        relic.setItemMeta(relicMeta);
        mainMenu.setItem(32, relic);

        // quicktravel button
        String travelHeadBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzljODg4MWU0MjkxNWE5ZDI5YmI2MWExNmZiMjZkMDU5OTEzMjA0ZDI2NWRmNWI0MzliM2Q3OTJhY2Q1NiJ9fX0=";
        List<String> loretravel = new ArrayList<>();
        ItemStack travel = GetHead.getCustomHead(travelHeadBase64, "§bFast Travel");
        ItemMeta travelMeta = travel.getItemMeta();
        loretravel.add("§7Teleport to locations you've already");
        loretravel.add("§7visited.");
        loretravel.add("");
        loretravel.add("§eClick to pick location!");
        travelMeta.setLore(loretravel);
        travel.setItemMeta(travelMeta);
        mainMenu.setItem(48, travel);

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        mainMenu.setItem(49, close);

        // settings button
        List<String> lore_setting = new ArrayList<>();
        ItemStack settings = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta settingsMeta = settings.getItemMeta();
        settingsMeta.setDisplayName("§aSettings");
        lore_setting.add("§7View and edit your settings.");
        lore_setting.add("");
        lore_setting.add("§eClick to view!");
        settingsMeta.setLore(lore_setting);
        settings.setItemMeta(settingsMeta);
        mainMenu.setItem(50, settings);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < mainMenu.getSize(); i++) {
            if (mainMenu.getItem(i) == null) {
                mainMenu.setItem(i, filler);
            }
        }

        player.openInventory(mainMenu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        Player player = (Player) e.getWhoClicked();


        if (e.getView().getTitle().equals("Menu")) {
            e.setCancelled(true);
            int slot = e.getSlot();

            switch (slot) {
                case 13:
                    openStatsMenu(player);
                    break;
                case 32:
                    openRelicMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }
        } else if (e.getView().getTitle().equals("Relics")) {
            e.setCancelled(true);
            int slot = e.getSlot();

            switch (slot) {
                case 48:
                    openMainMenu(player);
                    return;
                case 49:
                    player.closeInventory();
                    return;
                default:
                    break;
            }


            Inventory clickedInventory = e.getClickedInventory();
            ItemStack clicked = e.getCurrentItem();
            ItemStack cursor = e.getCursor();

            if (clickedInventory != null && clickedInventory.equals(relicMenu)) {
                if (isRelicSlot(slot)) {
                    if (clicked == null || clicked.getType() == Material.AIR) {
                        if (RelicHelper.isRelicItem(cursor)) {
                            ItemStack relicToPlace = cursor.clone();
                            relicToPlace = addRemoveLore(relicToPlace);
                            relicToPlace.setAmount(1);

                            relicMenu.setItem(slot, relicToPlace);

                            int cursorAmount = cursor.getAmount();
                            if (cursorAmount > 1) {
                                cursor.setAmount(cursorAmount - 1);
                                e.setCursor(cursor);
                            } else {
                                e.setCursor(null);
                            }

                            RelicHelper.applyRelicEffect(player, relicToPlace);
                        }
                    } else {
                        if (RelicHelper.isRelicItem(clicked)) {
                            RelicHelper.removeRelicEffect(player, clicked);

                            ItemStack relicToReturn = removeRemoveLore(clicked);
                            player.getInventory().addItem(relicToReturn);
                            relicMenu.setItem(slot, createPlaceholderPane());

                            player.updateInventory();
                        }
                    }
                    return;
                }
            }

            else if (clickedInventory != null && clickedInventory.equals(player.getInventory())) {
                if ((cursor == null || cursor.getType() == Material.AIR) && RelicHelper.isRelicItem(clicked)) {
                    for (int relicSlot : RELIC_SLOTS) {
                        ItemStack itemInSlot = relicMenu.getItem(relicSlot);
                        if (itemInSlot == null || itemInSlot.getType() == Material.RED_STAINED_GLASS_PANE) {
                            int amount = clicked.getAmount();
                            if (amount > 1) {
                                clicked.setAmount(amount - 1);
                                clickedInventory.setItem(e.getSlot(), clicked);
                            } else {
                                clickedInventory.setItem(e.getSlot(), null);
                            }

                            ItemStack relicToPlace = clicked.clone();
                            relicToPlace.setAmount(1);
                            relicToPlace = addRemoveLore(relicToPlace);

                            relicMenu.setItem(relicSlot, relicToPlace);
                            RelicHelper.applyRelicEffect(player, relicToPlace);

                            e.setCancelled(true);
                            return;
                        }
                    }
                    player.sendMessage("§cYou have no empty relic slots!");
                    e.setCancelled(true);
                    return;
                }
            }

            if (clicked != null && RelicHelper.isRelicItem(clicked)) {
                if (hasRemoveLore(clicked)) {
                    ItemStack returnedRelic = removeRemoveLore(clicked);
                    player.getInventory().addItem(returnedRelic);
                    e.getInventory().setItem(e.getSlot(), createPlaceholderPane());
                    player.updateInventory();
                    RelicHelper.removeRelicEffect(player, clicked);
                    e.setCancelled(true);
                }
            }

        } else if (e.getView().getTitle().equals("Your Equipment and Stats")) {
            e.setCancelled(true);
            int slot = e.getSlot();

            switch (slot) {
                case 48:
                    openMainMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Relics")) return;

        Player player = (Player) event.getPlayer();
        Inventory inv = event.getInventory();

        List<ItemStack> relics = new ArrayList<>();

        for (int slot : RELIC_SLOTS) {
            ItemStack item = inv.getItem(slot);
            if (RelicHelper.isRelicItem(item)) {
                relics.add(removeRemoveLore(item.clone()));
            } else {
                relics.add(null);
            }
        }

        relicManager.saveRelics(player, relics);
    }


    private boolean isPlaceholderItem(ItemStack item) {
        if (item == null || item.getType() != Material.RED_STAINED_GLASS_PANE) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Relic Slot");
    }

    private ItemStack createPlaceholderPane() {
        ItemStack pane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Relic Slot");
        pane.setItemMeta(meta);
        return pane;
    }

    private boolean hasRemoveLore(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;
        return meta.getLore().stream().anyMatch(line -> ChatColor.stripColor(line).equalsIgnoreCase("Click to remove!"));
    }

    private ItemStack addRemoveLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Click to remove!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack removeRemoveLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().stream()
                .filter(line -> !ChatColor.stripColor(line).equalsIgnoreCase("Click to remove!"))
                .collect(Collectors.toList());
        meta.setLore(lore.isEmpty() ? null : lore);
        item.setItemMeta(meta);
        return item;
    }

}
