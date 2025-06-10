package de.illy.planCEvent.StatSystem.StatPoints;

import java.util.HashMap;
import java.util.Map;

public class StatDefinitions {
    public static final Map<String, StatMeta> STAT_INFO = new HashMap<>();

    static {
        STAT_INFO.put("stat_strength", new StatMeta(1, "❁", false));
        STAT_INFO.put("stat_intelligence", new StatMeta(2, "✎", false));
        STAT_INFO.put("stat_crit_chance", new StatMeta(0.2, "☣", true));
        STAT_INFO.put("stat_crit_damage", new StatMeta(1, "☠", true));
        STAT_INFO.put("stat_defense", new StatMeta(1, "❈", false));
        STAT_INFO.put("stat_health", new StatMeta(5, "❤", false));
        STAT_INFO.put("stat_speed", new StatMeta(0.015, "✦", false));
    }
}

