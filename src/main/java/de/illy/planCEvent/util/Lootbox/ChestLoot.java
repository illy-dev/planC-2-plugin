package de.illy.planCEvent.util.Lootbox;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestLoot {

    private final List<LootItem> lootItems = new ArrayList<>();
    private final Random random = new Random();

    public void addLootItem(LootItem item) {
        lootItems.add(item);
    }

    public List<ItemStack> generateChest(int wave) {
        List<ItemStack> generated = new ArrayList<>();

        for (LootItem loot : lootItems) {
            if (wave >= loot.getMinWave() && wave <= loot.getMaxWave()) {
                if (random.nextDouble() < loot.getSpawnRate()) {
                    ItemStack clone = loot.getItem().clone();
                    int amount = loot.getMinAmount() + random.nextInt(loot.getMaxAmount() - loot.getMinAmount() + 1);
                    clone.setAmount(amount);
                    generated.add(clone);
                }
            }
        }

        return generated;
    }
}
