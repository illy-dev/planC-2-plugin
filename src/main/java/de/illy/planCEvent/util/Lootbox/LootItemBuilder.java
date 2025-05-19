package de.illy.planCEvent.util.Lootbox;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootItemBuilder {

    private ItemStack item;
    private double spawnRate = 1.0;
    private int minWave = 1;
    private int maxWave = Integer.MAX_VALUE;
    private int minAmount = 1;
    private int maxAmount = 1;

    public static LootItemBuilder create(Material material) {
        return new LootItemBuilder().item(new ItemStack(material));
    }

    public static LootItemBuilder create(ItemStack itemStack) {
        return new LootItemBuilder().item(itemStack);
    }

    public LootItemBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }

    public LootItemBuilder spawnRate(double rate) {
        this.spawnRate = rate;
        return this;
    }

    public LootItemBuilder waveRange(int min, int max) {
        this.minWave = min;
        this.maxWave = max;
        return this;
    }

    public LootItemBuilder amountRange(int min, int max) {
        this.minAmount = min;
        this.maxAmount = max;
        return this;
    }

    public LootItem build() {
        return new LootItem(item, spawnRate, minWave, maxWave, minAmount, maxAmount);
    }
}
