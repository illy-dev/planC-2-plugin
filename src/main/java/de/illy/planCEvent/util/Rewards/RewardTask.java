package de.illy.planCEvent.util.Rewards;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.util.AfkManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RewardTask {
    private final RewardManager rewardManager;

    public RewardTask(RewardManager rewardManager) {
        this.rewardManager = rewardManager;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    rewardManager.rewardPlayer(player);
                }
            }
        }.runTaskTimer(PlanCEvent.getInstance(), 0L, 20L * 60 * 10);
    }
}
