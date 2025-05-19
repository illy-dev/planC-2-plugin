package de.illy.planCEvent.util.Lootbox;

import org.bukkit.inventory.ItemStack;

public class LootItem {

    private final ItemStack item;
    private final double spawnRate;
    private final int minWave;
    private final int maxWave;
    private final int minAmount;
    private final int maxAmount;

    public LootItem(ItemStack item, double spawnRate, int minWave, int maxWave, int minAmount, int maxAmount) {
        this.item = item;
        this.spawnRate = spawnRate;
        this.minWave = minWave;
        this.maxWave = maxWave;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getSpawnRate() {
        return spawnRate;
    }

    public int getMinWave() {
        return minWave;
    }

    public int getMaxWave() {
        return maxWave;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
