package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.StatSystem.LevelManager;
import de.illy.planCEvent.StatSystem.StatAPI;
import de.illy.planCEvent.items.CoinSystem.Coin;
import de.illy.planCEvent.items.RelicContainer.RelicContainer;
import de.illy.planCEvent.items.armor.Druid.DruidArmor;
import de.illy.planCEvent.items.armor.Slimeboots.Slimeboots;
import de.illy.planCEvent.items.craftingItems.MiscItems;
import de.illy.planCEvent.items.craftingItems.Platine;
import de.illy.planCEvent.items.wands.enderwand.Enderwand;
import de.illy.planCEvent.items.wands.shrinkdevice.Shrinkdevice;
import de.illy.planCEvent.items.weapons.hyperion.Hyperion;
import de.illy.planCEvent.items.weapons.terminator.Terminator;
import de.illy.planCEvent.listeners.GUI.RecipeViewer;
import de.illy.planCEvent.listeners.GUI.Recipes;
import de.illy.planCEvent.util.*;
import de.illy.planCEvent.util.Quests.Quest;
import org.bukkit.*;
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
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuGUI implements Listener {
    private Inventory mainMenu;
    private Inventory relicMenu;
    private Inventory statsMenu;
    private Inventory skillsMenu;
    private Inventory travelMenu;
    private Inventory recipeMenu;
    private Inventory shop;
    private static final int[] RELIC_SLOTS = {20, 22, 24};
    RelicManager relicManager = PlanCEvent.getInstance().getRelicManager();

    private void openSkillsMenu(Player player) {
        skillsMenu = Bukkit.createInventory(null, 54, "Skills");

        LevelManager.updatePointsBasedOnLevel(player);



        // strength
        int defaultStrength = (int) ((int) StatAPI.getTotalStat(player, "stat_strength") - LevelManager.getAssignedPointsValues(player, "stat_strength"));
        List<String> strengthLore = new ArrayList<>();
        ItemStack strengthIcon = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta strengthIconMeta = strengthIcon.getItemMeta();
        strengthIconMeta.setDisplayName("§c❁ Strength");
        strengthLore.add("§r§7Strength increases the damage you");
        strengthLore.add("§7deal.");
        strengthLore.add("");
        strengthLore.add("§7You have §c" + defaultStrength + " §8+ §c" + (int) LevelManager.getAssignedPointsValues(player, "stat_strength"));
        strengthLore.add("§n§l§m§8---------------------");
        strengthLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_strength") + " points");
        strengthLore.add("§7Per point: §c+1❁");
        strengthLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        strengthIconMeta.setLore(strengthLore);
        strengthIcon.setItemMeta(strengthIconMeta);

        skillsMenu.setItem(23, strengthIcon);

        // intelligence
        int defaultIntelligence = (int) ((int) StatAPI.getTotalStat(player, "stat_intelligence") - LevelManager.getAssignedPointsValues(player, "stat_intelligence"));
        List<String> intelligenceLore = new ArrayList<>();
        ItemStack intelligenceIcon = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta intelligenceIconMeta = intelligenceIcon.getItemMeta();
        intelligenceIconMeta.setDisplayName("§b✎ Intelligence");
        intelligenceLore.add("§r§7Intelligence increases the damage of");
        intelligenceLore.add("§7your magical items and your mana");
        intelligenceLore.add("§7pool.");
        intelligenceLore.add("");
        intelligenceLore.add("§7You have §b" + defaultIntelligence + " §8+ §b" + (int) LevelManager.getAssignedPointsValues(player, "stat_intelligence"));
        intelligenceLore.add("§n§l§m§8---------------------");
        intelligenceLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_intelligence") + " points");
        intelligenceLore.add("§7Per point: §b+2✎");
        intelligenceLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        intelligenceIconMeta.setLore(intelligenceLore);
        intelligenceIcon.setItemMeta(intelligenceIconMeta);

        skillsMenu.setItem(22, intelligenceIcon);

        // crit chance
        double totalStat = StatAPI.getTotalStat(player, "stat_crit_chance");
        int critPoints = LevelManager.getAssignedPoints(player, "stat_crit_chance");

        double defaultCritChance = totalStat - (critPoints * 0.2);
        double critBonus = critPoints * 0.2;

        String formattedDefault = String.format(Locale.US, "%.1f", defaultCritChance);
        String formattedBonus = String.format(Locale.US, "%.1f", critBonus);
        String critChanceBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U0ZjQ5NTM1YTI3NmFhY2M0ZGM4NDEzM2JmZTgxYmU1ZjJhNDc5OWE0YzA0ZDlhNGRkYjcyZDgxOWVjMmIyYiJ9fX0=";
        List<String> critchanceLore = new ArrayList<>();
        ItemStack critchanceIcon = GetHead.getCustomHead(critChanceBase64, "§9☣ Crit Chance");
        ItemMeta critchanceIconMeta = critchanceIcon.getItemMeta();
        critchanceLore.add("§r§7Critical Chance is the percent");
        critchanceLore.add("§7chance that you land a Critical Hit");
        critchanceLore.add("§7when damaging an enemy");
        critchanceLore.add("");
        critchanceLore.add("§7You have §9" + formattedDefault + "% §8+ §9" + formattedBonus + "%");
        critchanceLore.add("§n§l§m§8---------------------");
        critchanceLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_crit_chance") + " points");
        critchanceLore.add("§7Per point: §9+0.2☣");
        critchanceLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        critchanceIconMeta.setLore(critchanceLore);
        critchanceIcon.setItemMeta(critchanceIconMeta);

        skillsMenu.setItem(21, critchanceIcon);

        // crit damage
        double defaultCritDamage = ( StatAPI.getTotalStat(player, "stat_crit_damage") - LevelManager.getAssignedPointsValues(player, "stat_crit_damage"));
        String critDamageBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRhZmIyM2VmYzU3ZjI1MTg3OGU1MzI4ZDExY2IwZWVmODdiNzljODdiMjU0YTdlYzcyMjk2ZjkzNjNlZjdjIn19fQ==";
        List<String> critdamageLore = new ArrayList<>();
        ItemStack critdamageIcon = GetHead.getCustomHead(critDamageBase64, "§9☠ Crit Damage");
        ItemMeta critdamageIconMeta = critdamageIcon.getItemMeta();
        critdamageLore.add("§r§7Critical Damage multiplies the damage");
        critdamageLore.add("§7that you deal when you land a Critical Hit.");
        critdamageLore.add("");
        critdamageLore.add("§7You have §9" + (int) defaultCritDamage + "% §8+ §9" + (int) LevelManager.getAssignedPointsValues(player, "stat_crit_damage"));
        critdamageLore.add("§n§l§m§8---------------------");
        critdamageLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_crit_damage") + " points");
        critdamageLore.add("§7Per point: §9+1☠");
        critdamageLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        critdamageIconMeta.setLore(critdamageLore);
        critdamageIcon.setItemMeta(critdamageIconMeta);

        skillsMenu.setItem(30, critdamageIcon);

        // defense
        double defaultDefense = ( StatAPI.getTotalStat(player, "stat_defense") - LevelManager.getAssignedPointsValues(player, "stat_defense"));
        List<String> defenseLore = new ArrayList<>();
        ItemStack defenseIcon = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta defenseIconMeta = defenseIcon.getItemMeta();
        defenseIconMeta.setDisplayName("§a❈ Defense");
        defenseLore.add("§r§7Your defense stat reduces the");
        defenseLore.add("§7damage you take from enemies.");
        defenseLore.add("");
        defenseLore.add("§7You have §a" + (int) defaultDefense + " §8+ §a" + (int) LevelManager.getAssignedPointsValues(player, "stat_defense"));
        defenseLore.add("§n§l§m§8---------------------");
        defenseLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_defense") + " points");
        defenseLore.add("§7Per point: §a+1❈");
        defenseLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        defenseIconMeta.setLore(defenseLore);
        defenseIcon.setItemMeta(defenseIconMeta);

        skillsMenu.setItem(31, defenseIcon);

        // health
        double defaultHealth = ( StatAPI.getTotalStat(player, "stat_health") - LevelManager.getAssignedPointsValues(player, "stat_health"));
        List<String> healthLore = new ArrayList<>();
        ItemStack healthIcon = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta healthIconMeta = healthIcon.getItemMeta();
        healthIconMeta.setDisplayName("§c❤ Health");
        healthLore.add("§r§7Your Health stat increases your");
        healthLore.add("§7maximum health.");
        healthLore.add("");
        healthLore.add("§7You have §c" + (int) defaultHealth + " §8+ §c" + (int) LevelManager.getAssignedPointsValues(player, "stat_health"));
        healthLore.add("§n§l§m§8---------------------");
        healthLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_health") + " points");
        healthLore.add("§7Per point: §c+5❤");
        healthLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        healthIconMeta.setLore(healthLore);
        healthIcon.setItemMeta(healthIconMeta);

        skillsMenu.setItem(32, healthIcon);

        // speed
        double defaultSpeed = (Math.round((StatAPI.getTotalStat(player, "stat_speed"))*1000) / 10.0) - (Math.round((LevelManager.getAssignedPointsValues(player, "stat_speed"))*1000) / 10.0);
        List<String> speedLore = new ArrayList<>();
        ItemStack speedIcon = new ItemStack(Material.SUGAR);
        ItemMeta speedIconMeta = speedIcon.getItemMeta();
        speedIconMeta.setDisplayName("§f✦ Speed");
        speedLore.add("§r§7Your Speed stat increases how fast");
        speedLore.add("§7you can walk.");
        speedLore.add("");
        speedLore.add("§7You have §f" + defaultSpeed + " §8+ §f" + (Math.round((LevelManager.getAssignedPointsValues(player, "stat_speed"))*1000) / 10.0));
        speedLore.add("§n§l§m§8---------------------");
        speedLore.add("§7Stat has: §c" + LevelManager.getAssignedPoints(player, "stat_speed") + " points");
        speedLore.add("§7Per point: §f+1.5✦");
        speedLore.add("§7Points left: §c" + LevelManager.getPointsLeft(player));
        speedIconMeta.setLore(speedLore);
        speedIcon.setItemMeta(speedIconMeta);

        skillsMenu.setItem(20, speedIcon);

        // ? info
        String infoBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5OWIwNWI5YTFkYjRkMjliNWU2NzNkNzdhZTU0YTc3ZWFiNjY4MTg1ODYwMzVjOGEyMDA1YWViODEwNjAyYSJ9fX0=";
        List<String> infoLore = new ArrayList<>();
        ItemStack infoIcon = GetHead.getCustomHead(infoBase64, "§fHow this works:");
        ItemMeta infoIconMeta = infoIcon.getItemMeta();
        infoLore.add("§71. Skill Points are based on your Level.");
        infoLore.add("§72. Left click to add a Point.");
        infoLore.add("§73. Right click to remove a Point.");
        infoLore.add("§73. Shift click to add/remove 10 Points.");
        infoIconMeta.setLore(infoLore);
        infoIcon.setItemMeta(infoIconMeta);

        skillsMenu.setItem(24, infoIcon);

        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        skillsMenu.setItem(48, backArrow);

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        skillsMenu.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < skillsMenu.getSize(); i++) {
            if (skillsMenu.getItem(i) == null) {
                skillsMenu.setItem(i, filler);
            }
        }

        player.openInventory(skillsMenu);
    }

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
        combatstatsLore.add("§f✦ Speed " + (Math.round((StatAPI.getTotalStat(player, "stat_speed"))*1000) / 10.0) + "%");
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

    public void openFastTravelMenu(Player player) {
        travelMenu = Bukkit.createInventory(null, 54, "Fast Travel");

        // hub item
        String hubItemBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzljODg4MWU0MjkxNWE5ZDI5YmI2MWExNmZiMjZkMDU5OTEzMjA0ZDI2NWRmNWI0MzliM2Q3OTJhY2Q1NiJ9fX0=";
        ItemStack hubIcon = GetHead.getCustomHead(hubItemBase64, "§bHub");
        ItemMeta hubMeta = hubIcon.getItemMeta();
        List<String> hubLore = new ArrayList<>();
        hubLore.add("");
        hubLore.add("§eClick to Teleport!");
        hubMeta.setLore(hubLore);
        hubIcon.setItemMeta(hubMeta);

        travelMenu.setItem(10, hubIcon);

        // survival world
        String survivalItemBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUyY2M0MjAxNWU2Njc4ZjhmZDQ5Y2NjMDFmYmY3ODdmMWJhMmMzMmJjZjU1OWEwMTUzMzJmYzVkYjUwIn19fQ==";
        ItemStack survIcon = GetHead.getCustomHead(survivalItemBase64, "§bSurvival World");
        ItemMeta survMeta = survIcon.getItemMeta();
        List<String> survLore = new ArrayList<>();
        survLore.add("");
        survLore.add("§eClick to Teleport!");
        survMeta.setLore(survLore);
        survIcon.setItemMeta(survMeta);

        travelMenu.setItem(11, survIcon);

        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        travelMenu.setItem(48, backArrow);

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        travelMenu.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < travelMenu.getSize(); i++) {
            boolean isTopRow = i >= 0 && i <= 8;
            boolean isBottomRow = i >= 45 && i <= 53;
            boolean isLeftColumn = i % 9 == 0;
            boolean isRightColumn = i % 9 == 8;

            if ((isTopRow || isBottomRow || isLeftColumn || isRightColumn) && travelMenu.getItem(i) == null) {
                travelMenu.setItem(i, filler);
            }
        }


        player.openInventory(travelMenu);
    }

    public void openRecipeMenu(Player player) {
        recipeMenu = Bukkit.createInventory(null, 54, "Recipes");

        // empty platine
        ItemStack emptyPlatine = Platine.emptyPlatine.clone();
        ItemMeta emtyPlatineMeta = emptyPlatine.getItemMeta();
        List<String> emPlatLore = new ArrayList<>();
        emPlatLore.add("");
        emPlatLore.add("§eClick to view the Recipe!");
        emtyPlatineMeta.setLore(emPlatLore);
        emptyPlatine.setItemMeta(emtyPlatineMeta);

        recipeMenu.setItem(10, emptyPlatine);

        // platine
        ItemStack platine = Platine.platine.clone();
        ItemMeta platineMeta = platine.getItemMeta();
        List<String> platLore = new ArrayList<>();
        platLore.add("");
        platLore.add("§eClick to view the Recipe!");
        platineMeta.setLore(platLore);
        platine.setItemMeta(platineMeta);

        recipeMenu.setItem(11, platine);

        // shrinkdevice
        ItemStack Shrinkdevice = de.illy.planCEvent.items.wands.shrinkdevice.Shrinkdevice.create().clone();
        ItemMeta ShrinkdeviceMeta = Shrinkdevice.getItemMeta();
        List<String> ShrinkdeviceLore = new ArrayList<>();
        ShrinkdeviceLore.add("");
        ShrinkdeviceLore.add("§eClick to view the Recipe!");
        ShrinkdeviceMeta.setLore(ShrinkdeviceLore);
        Shrinkdevice.setItemMeta(ShrinkdeviceMeta);

        recipeMenu.setItem(12, Shrinkdevice);

        // verstärkter string
        ItemStack verString = MiscItems.verstaerkterString.clone();
        ItemMeta verStringMeta = verString.getItemMeta();
        List<String> verStringLore = new ArrayList<>();
        verStringLore.add("");
        verStringLore.add("§eClick to view the Recipe!");
        verStringMeta.setLore(verStringLore);
        verString.setItemMeta(verStringMeta);

        recipeMenu.setItem(13, verString);

        // term
        ItemStack terminator = Terminator.create().clone();
        ItemMeta terminatorMeta = terminator.getItemMeta();
        List<String> terminatorLore = new ArrayList<>();
        terminatorLore.add("");
        terminatorLore.add("§eClick to view the Recipe!");
        terminatorMeta.setLore(terminatorLore);
        terminator.setItemMeta(terminatorMeta);

        recipeMenu.setItem(14, terminator);

        // hyperion handle
        ItemStack handle = MiscItems.hyperionHandle.clone();
        ItemMeta handleMeta = handle.getItemMeta();
        List<String> handleLore = new ArrayList<>();
        handleLore.add("");
        handleLore.add("§eClick to view the Recipe!");
        handleMeta.setLore(handleLore);
        handle.setItemMeta(handleMeta);

        recipeMenu.setItem(15, handle);

        // uncharged hype
        ItemStack unchHype = MiscItems.unChargedHype.clone();
        ItemMeta unchHypeMeta = unchHype.getItemMeta();
        List<String> unchHypeLore = new ArrayList<>();
        unchHypeLore.add("");
        unchHypeLore.add("§eClick to view the Recipe!");
        unchHypeMeta.setLore(unchHypeLore);
        unchHype.setItemMeta(unchHypeMeta);

        recipeMenu.setItem(16, unchHype);

        // hyperion
        ItemStack hyperion = Hyperion.create().clone();
        ItemMeta hypeMeta = hyperion.getItemMeta();
        List<String> hypeLore = new ArrayList<>();
        hypeLore.add("");
        hypeLore.add("§eClick to view the Recipe!");
        hypeMeta.setLore(hypeLore);
        hyperion.setItemMeta(hypeMeta);

        recipeMenu.setItem(19, hyperion);

        // slimeboots
        ItemStack slimeboots = Slimeboots.create().clone();
        ItemMeta slimebootsMeta = slimeboots.getItemMeta();
        List<String> slimebootsLore = new ArrayList<>();
        slimebootsLore.add("");
        slimebootsLore.add("§eClick to view the Recipe!");
        slimebootsMeta.setLore(slimebootsLore);
        slimeboots.setItemMeta(slimebootsMeta);

        recipeMenu.setItem(20, slimeboots);

        // enchFeather
        ItemStack enchFeather = MiscItems.enchFeather.clone();
        ItemMeta enchFeatherMeta = enchFeather.getItemMeta();
        List<String> enchFeatherLore = new ArrayList<>();
        enchFeatherLore.add("");
        enchFeatherLore.add("§eClick to view the Recipe!");
        enchFeatherMeta.setLore(enchFeatherLore);
        enchFeather.setItemMeta(enchFeatherMeta);

        recipeMenu.setItem(21, enchFeather);

        // druid armor
        ItemStack druidarmor = DruidArmor.DRUID_CHESTPLATE.clone();
        ItemMeta druidarmorMeta = druidarmor.getItemMeta();
        List<String> druidarmorLore = new ArrayList<>();
        druidarmorLore.add("");
        druidarmorLore.add("§7(Druid Armor Piece)");
        druidarmorLore.add("");
        druidarmorLore.add("§eClick to view the Recipe!");
        druidarmorMeta.setLore(druidarmorLore);
        druidarmor.setItemMeta(druidarmorMeta);

        recipeMenu.setItem(22, druidarmor);

        // ironrod
        ItemStack ironRod = MiscItems.ironRod.clone();
        ItemMeta ironRodMeta = ironRod.getItemMeta();
        List<String> ironRodLore = new ArrayList<>();
        ironRodLore.add("");
        ironRodLore.add("§eClick to view the Recipe!");
        ironRodMeta.setLore(ironRodLore);
        ironRod.setItemMeta(ironRodMeta);

        recipeMenu.setItem(23, ironRod);

        // enderwand
        ItemStack enderWand = Enderwand.create().clone();
        ItemMeta enderWandMeta = enderWand.getItemMeta();
        List<String> enderWandLore = new ArrayList<>();
        enderWandLore.add("");
        enderWandLore.add("§eClick to view the Recipe!");
        enderWandMeta.setLore(enderWandLore);
        enderWand.setItemMeta(enderWandMeta);

        recipeMenu.setItem(24, enderWand);


        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        recipeMenu.setItem(48, backArrow);

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        recipeMenu.setItem(49, close);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < recipeMenu.getSize(); i++) {
            boolean isTopRow = i >= 0 && i <= 8;
            boolean isBottomRow = i >= 45 && i <= 53;
            boolean isLeftColumn = i % 9 == 0;
            boolean isRightColumn = i % 9 == 8;

            if ((isTopRow || isBottomRow || isLeftColumn || isRightColumn) && recipeMenu.getItem(i) == null) {
                recipeMenu.setItem(i, filler);
            }
        }

        player.openInventory(recipeMenu);
    }

    public void openShop(Player player) {
        shop = Bukkit.createInventory(null, 54, "Shop");

        // relic container
        ItemStack relicContainer = RelicContainer.create().clone();
        ItemMeta relicContainerMeta = relicContainer.getItemMeta();
        List<String> lore = relicContainerMeta.getLore();
        lore.add("");
        lore.add("§ePurchase for 25 Coins!");
        relicContainerMeta.setLore(lore);
        relicContainer.setItemMeta(relicContainerMeta);
        shop.setItem(10, relicContainer);

        // back button
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backArrow.getItemMeta();
        backMeta.setDisplayName("§aGo Back");
        backArrow.setItemMeta(backMeta);

        shop.setItem(48, backArrow);

        // close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        close.setItemMeta(closeMeta);
        shop.setItem(49, close);

        // quests
        List<String> questLore = new ArrayList<>();
        int index = 1;
        for (Quest q : PlanCEvent.getInstance().getQuestManager().getQuests(player)) {
            questLore.add("§7" + index++ + ". " + q.getDisplay());
        }

        ItemStack questItem = new ItemStack(Material.PAPER);
        ItemMeta meta = questItem.getItemMeta();
        meta.setDisplayName("§aQuests");
        meta.setLore(questLore);
        questItem.setItemMeta(meta);
        shop.setItem(50, questItem);

        // fillers
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < shop.getSize(); i++) {
            boolean isTopRow = i >= 0 && i <= 8;
            boolean isBottomRow = i >= 45 && i <= 53;
            boolean isLeftColumn = i % 9 == 0;
            boolean isRightColumn = i % 9 == 8;

            if ((isTopRow || isBottomRow || isLeftColumn || isRightColumn) && shop.getItem(i) == null) {
                shop.setItem(i, filler);
            }
        }

        player.openInventory(shop);
    }

    public void openMainMenu(Player player) {
        mainMenu = Bukkit.createInventory(null, 54, "Menu");

        // player stats button
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setDisplayName("§r" + player.getName() + "§7's stats");
        skullMeta.setOwner(player.getName());
        playerHead.setItemMeta(skullMeta);
        mainMenu.setItem(13, playerHead);

        // skills button
        List<String> loreskills = new ArrayList<>();
        ItemStack skills = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta skillsMeta = skills.getItemMeta();
        skillsMeta.setDisplayName("§aYour Skills");
        loreskills.add("§7View your Skill progression and");
        loreskills.add("§7set your Skill points");
        loreskills.add("");
        loreskills.add("§eClick to view!");
        skillsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillsMeta.setLore(loreskills);
        skills.setItemMeta(skillsMeta);
        mainMenu.setItem(21, skills);

        // level button
        double playerLevel = LevelManager.getStoredLevel(player);
        double playerXp = LevelManager.getStoredXp(player);
        int nextLevelXp = 100;

        int totalBars = 20;
        double progressRatio = playerXp / nextLevelXp;
        int filledBars = (int) (progressRatio * totalBars);
        int emptyBars = totalBars - filledBars;

        StringBuilder bar = new StringBuilder("§3[§b");
        bar.append("━".repeat(filledBars)); // Filled part
        bar.append("§7");
        bar.append("━".repeat(emptyBars)); // Empty part
        bar.append("§3] §b" + (int) playerXp + "§7/§b" + nextLevelXp + " XP");


        String levelHeadBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkODg1YjMyYjBkZDJkNmI3ZjFiNTgyYTM0MTg2ZjhhNTM3M2M0NjU4OWEyNzM0MjMxMzJiNDQ4YjgwMzQ2MiJ9fX0=";
        List<String> lorelevel = new ArrayList<>();
        ItemStack level = GetHead.getCustomHead(levelHeadBase64, "§aLeveling");
        ItemMeta levelMeta = level.getItemMeta();
        lorelevel.add("§7Your Level: §8[" + LevelColor.getColor((int) playerLevel) + (int) playerLevel + "§8]");
        lorelevel.add("");
        lorelevel.add("§7Determine how far you've");
        lorelevel.add("§7progressed and earn rewards");
        lorelevel.add("§7from completing unique tasks.");
        lorelevel.add("");
        lorelevel.add("§7Progress to Level " + ((int)playerLevel + 1) + ":");
        lorelevel.add(bar.toString());
        //lorelevel.add("");
        //lorelevel.add("§eClick to view!");
        levelMeta.setLore(lorelevel);
        level.setItemMeta(levelMeta);
        mainMenu.setItem(22, level);

        // recipes button
        List<String> lorerecipes = new ArrayList<>();
        ItemStack recipes = new ItemStack(Material.BOOK);
        ItemMeta recipesMeta = recipes.getItemMeta();
        recipesMeta.setDisplayName("§aRecipe Book");
        //lorerecipes.add("§7Trough your adventure, you will");
        //lorerecipes.add("§7unlock recipes for all kinds of");
        lorerecipes.add("§7You can view how to");
        lorerecipes.add("§7craft items here.");
        //lorerecipes.add("");
        //lorerecipes.add("§7Recipe Book Unlocked: §e100.0§6%");
        //lorerecipes.add("§2────────§f────────  §e23§6/§e23");
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
        //loreenchanting.add("§eClick to open!");
        loreenchanting.add("§e§kaaa §r§eComing Soon §kaaa");
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

        // shop button
        List<String> lore_shop = new ArrayList<>();
        ItemStack shop = new ItemStack(Material.CHEST);
        ItemMeta shopMeta = shop.getItemMeta();
        shopMeta.setDisplayName("§aShop");
        lore_shop.add("§7Buy things.");
        lore_shop.add("");
        lore_shop.add("§eClick to view!");
        shopMeta.setLore(lore_shop);
        shop.setItemMeta(shopMeta);
        mainMenu.setItem(50, shop);

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
                case 48:
                    openFastTravelMenu(player);
                    break;
                case 13:
                    openStatsMenu(player);
                    break;
                case 32:
                    openRelicMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                case 50:
                    openShop(player);
                    break;
                case 21:
                    openSkillsMenu(player);
                    break;
                case 23:
                    openRecipeMenu(player);
                    break;
                case 31:
                    player.openWorkbench(null, true);
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
        } else if (e.getView().getTitle().equals("Skills")) {
            e.setCancelled(true);
            int slot = e.getSlot();
            String stat = null;
            boolean isRightClick = e.getClick().isRightClick();
            boolean isShiftClick = e.getClick().isShiftClick();
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;
            String displayName = clicked.getItemMeta().getDisplayName();

            switch (slot) {
                case 23:
                    stat = "stat_strength";
                    break;
                case 22:
                    stat = "stat_intelligence";
                    break;
                case 21:
                    stat = "stat_crit_chance";
                    break;
                case 30:
                    stat = "stat_crit_damage";
                    break;
                case 31:
                    stat = "stat_defense";
                    break;
                case 32:
                    stat = "stat_health";
                    break;
                case 20:
                    stat = "stat_speed";
                    break;
                case 48:
                    openMainMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }


            if (stat != null) {
                int pointsToModify = isShiftClick ? 10 : 1;

                if (isRightClick) {
                    int removed = 0;
                    for (int i = 0; i < pointsToModify; i++) {
                        if (LevelManager.removePoint(player, stat)) {
                            removed++;
                        } else {
                            break;
                        }
                    }
                    if (removed > 0) {
                        player.sendMessage("§aRemoved " + removed + " point" + (removed > 1 ? "s" : "") + " from " + displayName);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    } else {
                        player.sendMessage("§cNo points assigned to " + displayName);
                    }
                    openSkillsMenu(player);
                } else {
                    int available = LevelManager.getPointsLeft(player);
                    if (available <= 0) {
                        player.sendMessage("§cYou have no points left to assign!");
                        return;
                    }

                    int assigned = 0;
                    for (int i = 0; i < pointsToModify && LevelManager.getPointsLeft(player) > 0; i++) {
                        if (LevelManager.assignPoint(player, stat)) {
                            assigned++;
                        } else {
                            break;
                        }
                    }
                    if (assigned > 0) {
                        player.sendMessage("§aAssigned " + assigned + " point" + (assigned > 1 ? "s" : "") + " to " + displayName);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    } else {
                        player.sendMessage("§cFailed to assign point to " + displayName);
                    }
                    openSkillsMenu(player);
                }
            }
        } else if (e.getView().getTitle().equals("Fast Travel")) {
            e.setCancelled(true);
            int slot = e.getSlot();
            Location hub = new Location(Bukkit.getWorld("hub"), 0.5, 68, 8.5);
            Location survival = new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5);

            switch (slot) {
                case 10:
                    if (!player.isOnGround()) return;
                    if (player.isInLava()) return;
                    player.teleport(hub);
                    player.setGameMode(GameMode.ADVENTURE);
                    break;
                case 11:
                    if (!player.isOnGround()) return;
                    if (player.isInLava()) return;
                    player.teleport(survival);
                    player.setGameMode(GameMode.SURVIVAL);
                    break;
                case 48:
                    openMainMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }
        } else if (e.getView().getTitle().equals("Crafting Recipe")) {
            e.setCancelled(true);
            int slot = e.getSlot();

            switch (slot) {
                case 48:
                    openRecipeMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }
        } else if (e.getView().getTitle().equals("Recipes")) {
            e.setCancelled(true);
            int slot = e.getSlot();

            switch (slot) {
                case 10:
                    new RecipeViewer().openRecipeViewer(player, Platine.emptyPlatine, Recipes.ingredientsEmptyPlatine);
                    break;
                case 11:
                    new RecipeViewer().openRecipeViewer(player, Platine.platine, Recipes.ingredientsPlatine);
                    break;
                case 12:
                    new RecipeViewer().openRecipeViewer(player, Shrinkdevice.create(), Recipes.ingredientsGameboy);
                    break;
                case 13:
                    new RecipeViewer().openRecipeViewer(player, MiscItems.verstaerkterString, Recipes.ingredientsVerStrings);
                    break;
                case 14:
                    new RecipeViewer().openRecipeViewer(player, Terminator.create(), Recipes.ingredientsTerminator);
                    break;
                case 15:
                    new RecipeViewer().openRecipeViewer(player, MiscItems.hyperionHandle, Recipes.ingredientsHyperionHandle);
                    break;
                case 16:
                    new RecipeViewer().openRecipeViewer(player, MiscItems.unChargedHype, Recipes.ingredientsUnchHyperion);
                    break;
                case 19:
                    new RecipeViewer().openRecipeViewer(player, Hyperion.create(), Recipes.ingredientsHyperion);
                    break;
                case 20:
                    new RecipeViewer().openRecipeViewer(player, Slimeboots.create(), Recipes.ingredientsSlimeBoots);
                    break;
                case 21:
                    new RecipeViewer().openRecipeViewer(player, MiscItems.enchFeather, Recipes.ingredientsEnchFeather);
                    break;
                case 22:
                    new RecipeViewer().openRecipeViewer(player, DruidArmor.DRUID_CHESTPLATE, Recipes.ingredientsDruidArmor);
                    break;
                case 23:
                    new RecipeViewer().openRecipeViewer(player, MiscItems.ironRod, Recipes.ingredientsIronRod);
                    break;
                case 24:
                    new RecipeViewer().openRecipeViewer(player, Enderwand.create(), Recipes.ingredientsEnderwand);
                    break;
                case 48:
                    openMainMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                default:
                    break;
            }
        } else if (e.getView().getTitle().equals("Shop")) {
            e.setCancelled(true);
            int slot = e.getSlot();
            int amount = CountItems.countItemsInInventory(player, Coin.create());

            switch (slot) {
                case 10:
                    if (amount < 25) {
                        player.sendMessage("§cYou can't afford that");
                        return;
                    }
                    CountItems.removeCustomItem(player, Coin.create(), 25);
                    player.getInventory().addItem(RelicContainer.create());
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    break;
                case 48:
                    openMainMenu(player);
                    break;
                case 49:
                    player.closeInventory();
                    break;
                case 50:
                    PlanCEvent.getInstance().getQuestManager().tryDeliverItems(player);
                    openShop(player);
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
