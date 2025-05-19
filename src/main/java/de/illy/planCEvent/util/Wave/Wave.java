package de.illy.planCEvent.util.Wave;

import org.bukkit.entity.EntityType;
import java.util.Map;

public class Wave {
    private final Map<EntityType, Integer> mobs;

    public Wave(Map<EntityType, Integer> mobs) {
        this.mobs = mobs;
    }

    public Map<EntityType, Integer> getMobs() {
        return mobs;
    }

    public int getTotalMobsCount() {
        return mobs.values().stream().mapToInt(Integer::intValue).sum();
    }
}
