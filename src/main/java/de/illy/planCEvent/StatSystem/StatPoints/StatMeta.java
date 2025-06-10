package de.illy.planCEvent.StatSystem.StatPoints;

public class StatMeta {
    public final double perPoint;
    public final String symbol;
    public final boolean isPercent;

    public StatMeta(double perPoint, String symbol, boolean isPercent) {
        this.perPoint = perPoint;
        this.symbol = symbol;
        this.isPercent = isPercent;
    }
}

