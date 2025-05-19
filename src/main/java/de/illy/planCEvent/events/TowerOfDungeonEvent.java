package de.illy.planCEvent.events;

import de.illy.planCEvent.util.Inventory.InventoryManager;
import de.illy.planCEvent.util.Lootbox.ChestLoot;
import de.illy.planCEvent.util.Lootbox.LootItemBuilder;
import de.illy.planCEvent.util.PlayerManager;
import de.illy.planCEvent.util.Wave.Wave;
import de.illy.planCEvent.util.Wave.WaveManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

import static de.illy.planCEvent.util.ChatUtil.Praefix;

public class TowerOfDungeonEvent {
    // ------------- Chest Loot -------------
    ChestLoot chestLoot = new ChestLoot();

    private void initChestLoot() {
        chestLoot.addLootItem(LootItemBuilder.create(Material.ARROW)
                .spawnRate(0.75)
                .waveRange(1, 10)
                .amountRange(10, 20)
                .build());

        chestLoot.addLootItem(LootItemBuilder.create(Material.GOLDEN_APPLE)
                .spawnRate(0.05)
                .build());

    }

    // --------------------------------------

    private final WaveManager waveManager;
    private final Map<UUID, Location> initialPlayerLocs = new HashMap<>();
    private final Map<UUID, Integer> playerWaveCounter = new HashMap<>();
    private final Map<UUID, List<LivingEntity>> playerMobs = new HashMap<>();
    private final Map<UUID, Long> playerStartTimes = new HashMap<>();
    private final Map<UUID, Long> playerCompletionTimes = new HashMap<>();
    private final int baseY = -37;

    private int currentWave = 0;

    public TowerOfDungeonEvent(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public Map<UUID, List<LivingEntity>> getPlayerMobs() {
        return playerMobs;
    }

    public boolean allWavesCompleted() {
        for (UUID uuid : playerWaveCounter.keySet()) {
            int waveIndex = playerWaveCounter.get(uuid);
            if (waveIndex < waveManager.getWaveCount()) {
                return false;
            }
        }
        return true;
    }

    private Location getWaveLocationForPlayer(Player p, int waveIndex) {
        int playerIndex = new ArrayList<>(PlayerManager.getPlayers()).indexOf(p);
        int yOffset = baseY + (18 * waveIndex);
        return new Location(p.getWorld(), 616 + (18 * playerIndex), yOffset, 1559.5);
    }


    public void startEvent(World eventWorld) {
        currentWave = 0;
        waveManager.loadWaves();
        initialPlayerLocs.clear();
        int currPlayer = 0;
        for (Player p : PlayerManager.getPlayers()) {
            int eventPlayerX = 616 + (18 * currPlayer);
            Location eventPlayerLoc = new Location(eventWorld, eventPlayerX, -37, 1559);
            initialPlayerLocs.put(p.getUniqueId(), p.getLocation());
            InventoryManager.saveInventory(p);

            p.getInventory().clear();
            p.sendMessage(Praefix + ChatColor.BLUE + "Teleporting to Event...");
            p.teleport(eventPlayerLoc);
            playerWaveCounter.put(p.getUniqueId(), 0);
            updateWaveScoreboard(p, 0);

            currPlayer++;
        }
    }

    public void stopEvent() {
        int currPlayerStop = 0;
        for (Player p : PlayerManager.getPlayers()) {
            Location returnLoc = initialPlayerLocs.get(p.getUniqueId());

            if (returnLoc != null) {

                double radius = 20;
                int eventPlayerX = 616 + (18 * currPlayerStop);
                Location eventPlayerLoc = new Location(p.getWorld(), eventPlayerX, -37, 1559);
                for (Entity entity : p.getWorld().getEntities()) {
                    if (!(entity instanceof LivingEntity)) continue;
                    if (!(entity instanceof Monster || entity instanceof Phantom || entity instanceof Shulker)) continue;
                    if (entity.getLocation().distance(eventPlayerLoc) <= radius) {
                        entity.remove();
                    }
                }

                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Praefix + ChatColor.BLUE + "Teleporting back...");
                p.teleport(returnLoc);
                InventoryManager.restoreInventory(p);
                InventoryManager.clearSavedInventory(p.getUniqueId());

                Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
                p.setScoreboard(board);
                p.setPlayerListName(p.getName());

                currPlayerStop++;
            } else {
                p.sendMessage(Praefix + ChatColor.YELLOW + "No return location stored.");
            }

        }
        initialPlayerLocs.clear();
        playerStartTimes.clear();
        playerCompletionTimes.clear();
        PlayerManager.clearDisqualifiedPlayers();
    }

    public void spawnLootBox(Player p) {
        initChestLoot();
        UUID uuid = p.getUniqueId();
        int waveIndex = playerWaveCounter.getOrDefault(uuid, 0);

        Location baseLoc = getWaveLocationForPlayer(p, waveIndex-1);
        Location chestLoc = baseLoc.clone().add(0, -1, 1.5);
        chestLoc.getBlock().setType(Material.CHEST);

        Block chestBlock = chestLoc.getBlock();

        if (chestBlock.getState() instanceof Chest chest) {
            Inventory inventory = chest.getInventory();
            inventory.clear();

            for (ItemStack item : chestLoot.generateChest(waveIndex)) {
                inventory.addItem(item);
            }
        }
    }


    public void spawnNextWaveForPlayer(Player p) {
        UUID uuid = p.getUniqueId();
        int waveIndex = playerWaveCounter.getOrDefault(uuid, 0);
        playerStartTimes.putIfAbsent(uuid, System.currentTimeMillis());

        Wave wave = waveManager.getWave(waveIndex);

        // All waves completed
        if (wave == null) {
            long startTime = playerStartTimes.getOrDefault(uuid, System.currentTimeMillis());
            long durationMillis = System.currentTimeMillis() - startTime;

            long minutes = (durationMillis / 1000) / 60;
            long seconds = (durationMillis / 1000) % 60;

            p.sendMessage(ChatColor.GREEN + "✔ You have successfully cleared all floors!");
            p.sendMessage(ChatColor.AQUA + "⏱ Time taken: " + minutes + "m " + seconds + "s");

            playerCompletionTimes.put(uuid, durationMillis);
            updateWaveScoreboard(p, waveManager.getWaveCount());

            if (allWavesCompleted()) {
                displayFinalScoreboard();
            }
            return;
        }

        Location newLoc = getWaveLocationForPlayer(p, waveIndex);

        p.teleport(newLoc);
        if (waveIndex != 0 ) {
            p.sendMessage(ChatColor.YELLOW + "Next floor unlocking in 10 seconds...");
        }
        // Delay 10 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                spawnMobsForWave(p, wave, newLoc);
                p.sendMessage("§eWave " + (waveIndex + 1) + " started!");
                updateWaveScoreboard(p, waveIndex);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("PlanCEvent"), 200L); // 10 sec delay

        playerWaveCounter.put(uuid, waveIndex + 1);
    }


    private void spawnMobsForWave(Player p, Wave wave, Location center) {
        List<Location> spawnLocations = new ArrayList<>();
        int totalMobs = wave.getTotalMobsCount();
        int mobIndex = 0;

        for (Map.Entry<EntityType, Integer> entry : wave.getMobs().entrySet()) {
            int mobCount = entry.getValue();
            for (int i = 0; i < mobCount; i++) {
                double angle = Math.toRadians((360.0 / totalMobs) * mobIndex);
                double dx = Math.sin(angle) * 7;
                double dz = Math.cos(angle) * 7;
                spawnLocations.add(center.clone().add(dx, 0, dz));
                mobIndex++;
            }
        }

        List<LivingEntity> spawnedMobs = new ArrayList<>();
        for (int i = 0; i < spawnLocations.size(); i++) {
            Location spawnLoc = spawnLocations.get(i);
            EntityType mobType = new ArrayList<>(wave.getMobs().keySet()).get(i % wave.getMobs().size());
            Entity entity = p.getWorld().spawnEntity(spawnLoc, mobType);
            if (entity instanceof LivingEntity) {
                spawnedMobs.add((LivingEntity) entity);
            }
        }

        playerMobs.put(p.getUniqueId(), spawnedMobs);
    }


    private void updateWaveScoreboard(Player p, int currentWaveIndex) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("wave_" + p.getName(), "dummy", ChatColor.BLUE + "Event Info");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);


        int totalWaves = waveManager.getWaveCount();
        int score = totalWaves;

        for (int i = 0; i < totalWaves; i++) {
            String label;
            if (i < currentWaveIndex) {
                label = ChatColor.GREEN + "✔ Wave " + (i + 1);
            } else if (i == currentWaveIndex) {
                label = ChatColor.YELLOW + "▶ Wave " + (i + 1);
            } else {
                label = ChatColor.GRAY + "• Wave " + (i + 1);
            }

            obj.getScore(label).setScore(score--);
            score--;
        }

        p.setScoreboard(board);
    }

    public void markPlayerAsEliminated(Player p) {
        UUID uuid = p.getUniqueId();

        playerWaveCounter.put(uuid, waveManager.getWaveCount());
        playerMobs.remove(uuid);
        p.setGameMode(GameMode.SPECTATOR);

        Scoreboard emptyBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        p.setScoreboard(emptyBoard);

        long durationMillis = System.currentTimeMillis() - playerStartTimes.get(uuid);
        playerCompletionTimes.put(uuid, durationMillis);

        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has been eliminated!");

        if (allWavesCompleted()) {
            displayFinalScoreboard();
        }
    }

    private void displayFinalScoreboard() {
        Set<UUID> allParticipants = PlayerManager.getAllEventPlayerUUIDs();

        List<UUID> finishedPlayers = new ArrayList<>();
        List<UUID> eliminatedPlayers = new ArrayList<>();
        List<UUID> disqualifiedPlayers = new ArrayList<>();

        for (UUID uuid : allParticipants) {
            if (PlayerManager.isDisqualified(uuid)) {
                disqualifiedPlayers.add(uuid);
            } else if (playerWaveCounter.getOrDefault(uuid, 0) >= waveManager.getWaveCount()) {
                finishedPlayers.add(uuid);
            } else {
                eliminatedPlayers.add(uuid);
            }

            // Ensure all players have a time (for sorting)
            playerCompletionTimes.putIfAbsent(uuid, Long.MAX_VALUE);
        }

        Comparator<UUID> timeComparator = Comparator.comparingLong(uuid -> playerCompletionTimes.getOrDefault(uuid, Long.MAX_VALUE));

        finishedPlayers.sort(timeComparator);
        eliminatedPlayers.sort(timeComparator);

        Bukkit.broadcastMessage(ChatColor.GOLD + "╔═══════════════════════╗");
        Bukkit.broadcastMessage(ChatColor.GOLD + "  🏆 " + ChatColor.BOLD + "Tower of Dungeon - Final Rankings");
        Bukkit.broadcastMessage(ChatColor.GOLD + "╚═══════════════════════╝");

        // Show finished players
        if (!finishedPlayers.isEmpty()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "✔ Finished Players:");
            int rank = 1;
            for (UUID uuid : finishedPlayers) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                long time = playerCompletionTimes.get(uuid);
                long minutes = (time / 1000) / 60;
                long seconds = (time / 1000) % 60;

                String placePrefix = switch (rank) {
                    case 1 -> ChatColor.GOLD + "🥇 1st";
                    case 2 -> ChatColor.GRAY + "🥈 2nd";
                    case 3 -> ChatColor.DARK_RED + "🥉 3rd";
                    default -> ChatColor.DARK_PURPLE + "#" + rank;
                };

                Bukkit.broadcastMessage(placePrefix + ChatColor.YELLOW + " - " + player.getName() + ChatColor.WHITE + " (" + ChatColor.AQUA + minutes + "m " + seconds + "s" + ChatColor.WHITE + ")");
                rank++;
            }
        }

        // Show eliminated players
        if (!eliminatedPlayers.isEmpty()) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.YELLOW + "❌ Eliminated Players:");
            for (UUID uuid : eliminatedPlayers) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                long time = playerCompletionTimes.get(uuid);
                long minutes = (time / 1000) / 60;
                long seconds = (time / 1000) % 60;

                Bukkit.broadcastMessage(ChatColor.YELLOW + " - " + player.getName() + ChatColor.WHITE + " (" + ChatColor.AQUA + minutes + "m " + seconds + "s" + ChatColor.WHITE + ")");
            }
        }

        // Show disqualified players
        if (!disqualifiedPlayers.isEmpty()) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.RED + "🚫 Disqualified Players:");
            for (UUID uuid : disqualifiedPlayers) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                Bukkit.broadcastMessage(ChatColor.RED + " - " + player.getName());
            }
        }
    }



}

