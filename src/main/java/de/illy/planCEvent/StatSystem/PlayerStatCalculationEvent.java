package de.illy.planCEvent.StatSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStatCalculationEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String statName;
    private double modifiedValue;

    public PlayerStatCalculationEvent(Player player, String statName, double value) {
        this.player = player;
        this.statName = statName;
        this.modifiedValue = value;
    }

    public Player getPlayer() { return player; }
    public String getStatName() { return statName; }
    public double getModifiedValue() { return modifiedValue; }
    public void setModifiedValue(double value) { this.modifiedValue = value; }

    @Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
