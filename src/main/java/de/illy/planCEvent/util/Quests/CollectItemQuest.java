package de.illy.planCEvent.util.Quests;

import org.bukkit.Material;

public class CollectItemQuest extends Quest {
    private final Material itemType;

    public CollectItemQuest(Material item, int goal) {
        super("Collect " + goal + " " + item.name(), goal);
        this.itemType = item;
    }

    public Material getItemType() {
        return itemType;
    }

    public int getAmountRemaining() {
        return goal - progress;
    }

    @Override
    public String getDisplay() {
        return formatDisplay("Collect " + goal + " " + itemType.name() + " (" + progress + "/" + goal + ")");
    }
}


