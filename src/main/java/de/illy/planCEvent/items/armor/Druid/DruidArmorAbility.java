package de.illy.planCEvent.items.armor.Druid;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.items.ItemAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DruidArmorAbility implements ItemAbility {

    private final Plugin plugin = PlanCEvent.getInstance();

    // Keep track of running tasks per player so we can cancel them on remove
    private final ConcurrentHashMap<UUID, BukkitRunnable> runningTasks = new ConcurrentHashMap<>();

    private final List<Biome> forestBiomes = List.of(
            Biome.FOREST, Biome.DARK_FOREST, Biome.BIRCH_FOREST,
            Biome.JUNGLE, Biome.CHERRY_GROVE, Biome.TAIGA,
            Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA,
            Biome.OLD_GROWTH_BIRCH_FOREST, Biome.BAMBOO_JUNGLE,
            Biome.PALE_GARDEN
    );

    @Override
    public void execute(Player player) {
        BukkitRunnable existingTask = runningTasks.get(player.getUniqueId());
        if (existingTask != null) {
            existingTask.cancel();
        }

        BukkitRunnable task = new BukkitRunnable() {
            boolean sentMessage = false;

            @Override
            public void run() {
                Biome currentBiome = player.getWorld().getBiome(player.getLocation());
                if (forestBiomes.contains(currentBiome)) {
                    if (!player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1, true, true));
                    }
                    if (!sentMessage) {
                        sentMessage = true;
                    }
                } else {
                    // Remove regeneration and reset message flag
                    player.removePotionEffect(PotionEffectType.REGENERATION);
                    sentMessage = false;
                }
            }
        };

        // Schedule the task to run every 20 ticks (1 second)
        task.runTaskTimer(PlanCEvent.getInstance(), 0L, 20L);

        runningTasks.put(player.getUniqueId(), task);
    }

    @Override
    public void remove(Player player) {
        // Cancel and remove the task if running
        BukkitRunnable task = runningTasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
        player.removePotionEffect(PotionEffectType.REGENERATION);
    }
}
