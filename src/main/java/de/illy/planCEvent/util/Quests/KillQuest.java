package de.illy.planCEvent.util.Quests;

import org.bukkit.entity.EntityType;

public class KillQuest extends Quest {
    private final EntityType entityType;

    public KillQuest(EntityType type, int goal) {
        super("Kill " + goal + " " + type.name(), goal);
        this.entityType = type;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public String getDisplay() {
        return formatDisplay("Kill " + goal + " " + entityType.name() + " (" + progress + "/" + goal + ")");
    }
}
