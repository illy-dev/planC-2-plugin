package de.illy.planCEvent.util.Quests;

import de.illy.planCEvent.items.CoinSystem.Coin;
import de.illy.planCEvent.util.RelicHelper;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class QuestManager {
    private final Map<UUID, List<Quest>> playerQuests = new HashMap<>();
    private final Random random = new Random();
    private final Map<UUID, Set<Quest>> rewardedQuests = new HashMap<>();

    private final List<EntityType> validEntities = List.of(EntityType.ZOMBIE, EntityType.CHICKEN, EntityType.ENDERMAN, EntityType.BAT, EntityType.DROWNED);
    private final List<Material> collectItems = List.of(Material.DIAMOND, Material.OCHRE_FROGLIGHT, Material.PACKED_ICE, Material.EMERALD, Material.CANDLE, Material.PHANTOM_MEMBRANE);

    public List<Quest> getQuests(Player player) {
        UUID uuid = player.getUniqueId();
        if (!playerQuests.containsKey(uuid)) {
            assignQuests(player);
        }
        return playerQuests.get(uuid);
    }

    public void assignQuests(Player player) {
        List<Quest> quests = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            EntityType type = validEntities.get(random.nextInt(validEntities.size()));
            quests.add(new KillQuest(type, 5 + random.nextInt(6)));
        }
        Material item = collectItems.get(random.nextInt(collectItems.size()));
        quests.add(new CollectItemQuest(item, 3 + random.nextInt(4)));
        playerQuests.put(player.getUniqueId(), quests);
    }

    public void progressKill(Player player, EntityType type) {
        for (Quest q : getQuests(player)) {
            if (!q.isCompleted() && q instanceof KillQuest kill && kill.getEntityType() == type) {
                q.addProgress(1);
            }
        }
        checkCompletion(player);
    }

    public void tryDeliverItems(Player player) {
        for (Quest q : getQuests(player)) {
            if (!(q instanceof CollectItemQuest collectQuest)) continue;

            if (collectQuest.isCompleted()) {
                player.sendMessage("§aYou already delivered all required items.");
                continue;
            }

            Material item = collectQuest.getItemType();
            int needed = collectQuest.getAmountRemaining();
            int available = countItem(player, item);

            if (available >= needed) {
                removeItems(player, item, needed);
                collectQuest.addProgress(needed);
                player.sendMessage("§aDelivered " + needed + " " + item.name() + "!");
            } else if (available > 0) {
                removeItems(player, item, available);
                collectQuest.addProgress(available);
                player.sendMessage("§aDelivered " + available + " " + item.name() + "!");
            } else {
                player.sendMessage("§cYou don't have any " + item.name() + " to deliver.");
            }
        }

        checkCompletion(player);
    }



    private void checkCompletion(Player player) {
        List<Quest> quests = getQuests(player);
        Set<Quest> rewarded = rewardedQuests.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>());

        int coinsToGive = 0;
        boolean allCompleted = true;

        for (Quest q : quests) {
            if (q.isCompleted()) {
                if (!rewarded.contains(q)) {
                    coinsToGive += 5;
                    rewarded.add(q);
                }
            } else {
                allCompleted = false;
            }
        }

        if (allCompleted && coinsToGive > 0) {
            coinsToGive += 3;
            player.sendMessage("§aAll quests completed! +3 bonus coins!");
            assignQuests(player);
            rewardedQuests.remove(player.getUniqueId());
        }

        if (coinsToGive > 0) {
            giveCoins(player, coinsToGive);
        }
    }



    private int countItem(Player player, Material material) {
        return Arrays.stream(player.getInventory().getContents())
                .filter(i -> i != null && i.getType() == material)
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

    private void removeItems(Player player, Material type, int amount) {
        int remaining = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && remaining > 0; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() == type) {
                int amt = item.getAmount();
                if (amt <= remaining) {
                    contents[i] = null;
                    remaining -= amt;
                } else {
                    item.setAmount(amt - remaining);
                    remaining = 0;
                }
            }
        }
        player.getInventory().setContents(contents);
    }

    private void giveCoins(Player player, int amount) {
        player.sendMessage("§6You earned " + amount + " coins!");
        int coinAmount = amount + RelicHelper.getCoinRelicCount(player);

        for (int i = 0; i < coinAmount; i++) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), Coin.create());

            } else {
                player.getInventory().addItem(Coin.create());
            }
        }
    }

    public void showQuests(Player player) {
        player.sendMessage("§eYour Quests:");
        int index = 1;
        for (Quest q : getQuests(player)) {
            player.sendMessage(" §7" + (index++) + ". " + q.getDisplay());
        }
    }
}