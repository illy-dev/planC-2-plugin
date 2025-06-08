package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegenTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ManaManager.getManaMap().containsKey(player.getUniqueId())) {
                ManaManager.resetMana(player);
            }

            double currentMana = ManaManager.getMana(player);
            double maxMana = ManaManager.getMaxMana(player);

            double manaGained = maxMana * 0.02;
            ManaManager.addMana(player, manaGained);

            String actionBarText = String.format("§b%.0f/%.0f✎ Mana", ManaManager.getMana(player), maxMana);
            String actionBarText3 = String.format("§a%.0f❈ Defense     ", StatAPI.getTotalStat(player, "stat_defense"));
            String actionBarText2 = String.format("§c%.0f/%.0f❤     ", CustomHealthManager.getHealth(player), CustomHealthManager.getMaxHealth(player));
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText2 + actionBarText3 + actionBarText));
        }
    }

    public static void startTask() {
        new ManaRegenTask().runTaskTimer(PlanCEvent.getInstance(), 0L, 20L);
    }
}
