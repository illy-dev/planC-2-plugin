package de.illy.planCEvent.util.Rewards;

import de.illy.planCEvent.items.CoinSystem.Coin;
import de.illy.planCEvent.util.AfkManager;
import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RewardManager {

    private final AfkManager afkManager;

    public RewardManager(AfkManager afkManager) {
        this.afkManager = afkManager;
    }

    public void rewardPlayer(Player player) {
        if (afkManager.isAfk(player)) return;

        int coinAmount = 1 + RelicHelper.getCoinRelicCount(player);

        for (int i = 0; i < coinAmount; i++) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), Coin.create());

            } else {
                player.getInventory().addItem(Coin.create());
            }
        }
        player.sendMessage("§eYou received §a" + coinAmount + "§e Coin(s) for staying online for 10 minutes!");
    }
}
