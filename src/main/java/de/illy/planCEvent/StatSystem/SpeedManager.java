package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class SpeedManager implements Listener {

    private static final double MAX_SPEED_MODIFIER = 4.0;
    private final JavaPlugin plugin;

    public SpeedManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public double getTotalSpeedModifier(Player player) {
        NamespacedKey speedKey = new NamespacedKey(PlanCEvent.getInstance(), "stat_speed");
        double baseSpeed = StatAPI.getTotalStat(player, "stat_speed");
        double itemSpeed = StatUtils.getPlayerItemStat(player, speedKey);
        double totalSpeed = baseSpeed + itemSpeed;
        if (totalSpeed > MAX_SPEED_MODIFIER) totalSpeed = MAX_SPEED_MODIFIER;
        return totalSpeed;
    }

    public void applySpeed(Player player) {
        if (player.isFlying() || !player.isOnGround() || player.getGameMode() != GameMode.SURVIVAL) return;
        double baseSpeed;

        if (player.isSneaking()) {
            baseSpeed = 0.17;
        } else if (player.isSprinting()) {
            baseSpeed = 0.26;
        } else {
            baseSpeed = 0.2;
        }

        double speedModifier = getTotalSpeedModifier(player);
        double newSpeed = baseSpeed * (speedModifier);

        if (newSpeed > 1.0) newSpeed = 1.0;

        player.setWalkSpeed((float) newSpeed);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        applySpeed(event.getPlayer());
        if (event.getPlayer().getWorld() == Bukkit.getWorld("hub")) {
            if (event.getPlayer().getY() < 55) {
                //event.getPlayer().setVelocity(new Vector(0, 5, 0));
                event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(new Vector(0, 0.5, 0)));
            }
        }
    }
}

