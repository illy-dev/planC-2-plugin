package de.illy.planCEvent.listeners;

import de.illy.planCEvent.PlanCEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class QuestListener implements Listener {
    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            PlanCEvent.getInstance().getQuestManager().progressKill(killer, event.getEntityType());
        }
    }
}
