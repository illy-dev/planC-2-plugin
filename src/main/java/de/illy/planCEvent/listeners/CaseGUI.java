package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static de.illy.planCEvent.util.GetHead.getCustomHead;

public class CaseGUI implements Listener {

    private static final Map<ItemStack, Integer> WEIGHTED_LOOT = new LinkedHashMap<>();

    private static final String[] RELIC_BASE64S = {
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZiZjZhM2I2MGUyMDY0MDI1ZTJjNzNkZTJiNzUxOThiNjJkMzU4MmExMDZlZDgzOWI2MDcwNjA4ODY5NmYxNSJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU5YTYxZTExM2NjYmVkYWVjNTdjYjk2ODY4MGNmYWNhMGQ5ODdmZjJkMDgzZjVhZWYwNTg2YTUwMzE2YjI4ZCJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM4Y2NkNWY4NjNkODJiYjA5N2I5MjZiYzVmNGNjYTk3YjE5ZjQ2ZTExYjNhM2E1OWQwMDFhZGI4OTg4Njc3MyJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY4MjRkMTYyMmQwMDQ1NzgxNmJhMDJjN2ZhNjYwMmQ3ZDZkMTFkZmEzYzk2ODRlMGQ5NDQxMmVkNTllODQyNCJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ2MjBlNGUzZDNhYmZlZDZhZDgxYTU4YTU2YmNkMDg1ZDllOWVmYzgwM2NhYmIyMWZhNmM5ZTM5NjllMmQyZSJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUyZDYxMmViZTZjMzk5OTZiNWE5MDVhNzdmNzJkMjhmNmRlZjgxZmU4Yjk3NTU2ZTI2MmQyNzJmODdlOWJiMCJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVmNDg2MWFhNWIyMmVlMjhhOTBlNzVkYWI0NWQyMjFlZmQxNGMwYjFlY2M4ZWU5OThmYjY3ZTQzYmI4ZjNkZSJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIxMTRhODAzNTc0NjNmZTJmNTllMzk3YWFiOWZjNjZkNDgyYTY1ZDUyNGY4ODcwZDIxYzcyNGMxOGVjZjc1NyJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI3Mzc0MGQ0NTRkZTk2MjQ4NDcxMmY5ODM1ZTM1MTE5YjM3YWI4NjdmYTY5ODJkNWNjMWYzMzNjMjMzNGU1OSJ9fX0=",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkzM2NjYWFlZWZhODNhNjFmNWYzZmM5NDMwYTcwOGQ1Nzc4OTA5NjA3MDljN2I5YzY2ZjIxNTBiZDUyMzU2MSJ9fX0="
    };
    private static final int[] RELIC_WEIGHTS = {15, 12, 10, 8, 5, 1, 7, 11, 7, 1};

    static {
        for (int i = 0; i < RELIC_BASE64S.length; i++) {
            WEIGHTED_LOOT.put(getCustomHead(RELIC_BASE64S[i], ChatColor.LIGHT_PURPLE + "Relic #" + (i + 1)), RELIC_WEIGHTS[i]);
        }
    }

    private static final int INVENTORY_SIZE = 9;
    private static final String INVENTORY_TITLE = ChatColor.BLUE + "Relic Container";

    private static final List<Integer> CASE_SLOTS = Arrays.asList(2, 3, 4, 5, 6);
    private static final int CENTER_SLOT = 4;

    private static final List<Integer> FILLER_SLOTS = Arrays.asList(0, 1, 7, 8);

    private static final Map<UUID, CaseSpinTask> runningTasks = new HashMap<>();

    public static void openCase(Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_TITLE);

        for (int slot : CASE_SLOTS) {
            inventory.setItem(slot, getWeightedRandomItem());
        }

        ItemStack filler = createFillerPane();
        for (int slot : FILLER_SLOTS) {
            inventory.setItem(slot, filler);
        }

        player.openInventory(inventory);

        CaseSpinTask task = new CaseSpinTask(player, inventory);
        task.runTaskTimer(PlanCEvent.getInstance(), 0L, task.getCurrentDelay());
        runningTasks.put(player.getUniqueId(), task);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_TITLE)) return;

        int slot = event.getRawSlot();

        if (slot >= 0 && slot < INVENTORY_SIZE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_TITLE)) return;

        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (runningTasks.containsKey(uuid)) {
            CaseSpinTask task = runningTasks.get(uuid);
            task.stopAndGivePrize();
            runningTasks.remove(uuid);
        }
    }

    private static class CaseSpinTask extends BukkitRunnable {
        private final Player player;
        private final Inventory inventory;
        private int tickCount = 0;
        private final int maxTicks = 50; // 10 seconds at 20 TPS
        @Getter
        private int currentDelay = 1; // starting delay in ticks (fast)
        private final int maxDelay = 6; // max delay in ticks (slow)
        private int runsWithCurrentDelay = 0; // count runs to increase delay gradually

        private final Random random = new Random();

        public CaseSpinTask(Player player, Inventory inventory) {
            this.player = player;
            this.inventory = inventory;
        }

        @Override
        public void run() {
            if (!player.isOnline() || !player.getOpenInventory().getTitle().equals(INVENTORY_TITLE)) {
                stopAndGivePrize();
                cancel();
                return;
            }

            if (tickCount >= maxTicks) {
                givePrizeAndCleanup();
                cancel();
                return;
            }

            List<ItemStack> currentItems = new ArrayList<>();
            for (int slot : CASE_SLOTS) {
                currentItems.add(inventory.getItem(slot));
            }

            for (int i = 0; i < CASE_SLOTS.size() - 1; i++) {
                inventory.setItem(CASE_SLOTS.get(i), currentItems.get(i + 1));
            }

            inventory.setItem(CASE_SLOTS.get(CASE_SLOTS.size() - 1), getWeightedRandomItem());

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);

            tickCount++;
            runsWithCurrentDelay++;

            if (runsWithCurrentDelay >= 10 && currentDelay < maxDelay) {
                runsWithCurrentDelay = 0;
                currentDelay++;

                cancel();

                CaseSpinTask newTask = new CaseSpinTask(player, inventory);
                newTask.currentDelay = this.currentDelay;
                newTask.tickCount = this.tickCount;
                newTask.runsWithCurrentDelay = 0;
                newTask.runTaskTimer(PlanCEvent.getInstance(), currentDelay, currentDelay);

                runningTasks.put(player.getUniqueId(), newTask);
            }
        }

        public void stopAndGivePrize() {
            if (isCancelled()) return;

            cancel();

            ItemStack prize = inventory.getItem(CENTER_SLOT);
            if (prize == null) {
                prize = getWeightedRandomItem();
            }

            inventory.setItem(CENTER_SLOT, prize);

            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1f, 1f);
            player.sendMessage( player.getPlayerListName() + "§e opened a Relic container and got §6" + getDisplayName(prize));

            player.getInventory().addItem(prize);

            player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation().add(0, 1, 0), 30, 0.5, 0.5, 0.5, 0.1);
            int weight = getWeightForItem(prize);
            if (weight == 1) {
                player.getWorld().spawnParticle(Particle.FIREWORK, player.getLocation().add(0, 1, 0), 50, 0.5, 0.5, 0.5, 0.2);
            }
        }

        private void givePrizeAndCleanup() {
            stopAndGivePrize();
            runningTasks.remove(player.getUniqueId());
        }
    }

    private static ItemStack getWeightedRandomItem() {
        int totalWeight = WEIGHTED_LOOT.values().stream().mapToInt(i -> i).sum();
        int randomWeight = new Random().nextInt(totalWeight);

        for (Map.Entry<ItemStack, Integer> entry : WEIGHTED_LOOT.entrySet()) {
            randomWeight -= entry.getValue();
            if (randomWeight < 0) {
                return entry.getKey().clone();
            }
        }
        return new ItemStack(Material.DIRT);
    }

    private static ItemStack createNamedItem(Material material, String name, int weight) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            if (weight <= 3) {
                meta.addEnchant(Enchantment.LOOTING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    private static ItemStack createFillerPane() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = filler.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
        }
        return filler;
    }

    private static String getDisplayName(ItemStack item) {
        if (item == null) return "Nothing";
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            return meta.getDisplayName();
        }
        return item.getType().toString().toLowerCase().replace("_", " ");
    }

    private static int getWeightForItem(ItemStack item) {
        for (Map.Entry<ItemStack, Integer> entry : WEIGHTED_LOOT.entrySet()) {
            if (entry.getKey().isSimilar(item)) {
                return entry.getValue();
            }
        }
        return Integer.MAX_VALUE;
    }
}
