package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.StatSystem.LevelManager;
import de.illy.planCEvent.StatSystem.ManaRegenTask;
import de.illy.planCEvent.StatSystem.SpeedManager;
import de.illy.planCEvent.StatSystem.StatAPI;
import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerConnectionEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        List<ItemStack> relics = PlanCEvent.getInstance().getRelicManager().loadRelics(player);
        for (ItemStack relic : relics) {
            if (RelicHelper.isRelicItem(relic)) {
                RelicHelper.applyRelicEffect(player, relic);
            }
        }

        LevelManager.loadPlayerData(player);
        SpeedManager speedManager = PlanCEvent.getInstance().getSpeedManager();
        speedManager.applySpeed(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        LevelManager.savePlayerData(player);

        List<ItemStack> relics = PlanCEvent.getInstance().getRelicManager().loadRelics(player);

        for (ItemStack relic : relics) {
            if (relic != null && RelicHelper.isRelicItem(relic)) {
                RelicHelper.removeRelicEffect(player, relic);
            }
        }
        ManaRegenTask.rageCooldowns.remove(player.getUniqueId());
    }
}
