package de.illy.planCEvent.StatSystem;

import de.illy.planCEvent.PlanCEvent;
import de.illy.planCEvent.util.HelixEffect;
import de.illy.planCEvent.util.RelicHelper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ManaRegenTask extends BukkitRunnable {
    public static final Map<UUID, Long> rageCooldowns = new HashMap<>();
    private static final long RAGE_COOLDOWN_MS = 30 * 1000;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ManaManager.getManaMap().containsKey(player.getUniqueId())) {
                ManaManager.resetMana(player);
            }

            double maxMana = ManaManager.getMaxMana(player);
            double maxHp = HealthManager.getMaxHealth(player);

            double hpGained = 0.75 + (maxHp/100);
            HealthManager.addHealth(player, hpGained);


            UUID uuid = player.getUniqueId();
            long now = System.currentTimeMillis();

            if (HealthManager.getHealth(player) < (maxHp * 0.2) && RelicHelper.playerHasRelic(player, "rage")) {
                if (!rageCooldowns.containsKey(uuid) || now - rageCooldowns.get(uuid) >= RAGE_COOLDOWN_MS) {
                    rageCooldowns.put(uuid, now);

                    player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
                    player.addPotionEffect(PotionEffectType.DARKNESS.createEffect(400, 1));
                    player.addPotionEffect(PotionEffectType.SPEED.createEffect(400, 2));
                    player.addPotionEffect(PotionEffectType.STRENGTH.createEffect(400, 2));
                    new HelixEffect(PlanCEvent.getInstance(), player).start();
                    player.sendMessage("§cRage activated!");
                }
            }


            double manaGained = maxMana * 0.02;
            ManaManager.addMana(player, manaGained);

            String actionBarText = String.format("§b%.0f/%.0f✎ Mana", ManaManager.getMana(player), maxMana);
            String actionBarText3 = String.format("§a%.0f❈ Defense     ", StatAPI.getTotalStat(player, "stat_defense"));
            String actionBarText2 = String.format("§c%.0f/%.0f❤     ", HealthManager.getHealth(player), HealthManager.getMaxHealth(player));
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText2 + actionBarText3 + actionBarText));
        }
    }

    public static void startTask() {
        new ManaRegenTask().runTaskTimer(PlanCEvent.getInstance(), 0L, 20L);
    }
}
