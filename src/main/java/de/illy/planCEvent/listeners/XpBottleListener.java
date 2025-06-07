package de.illy.planCEvent.listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class XpBottleListener implements Listener {

    @EventHandler
    public void onBottleThrow(PlayerLaunchProjectileEvent event) {
        if (!(event.getProjectile() instanceof ThrownExpBottle)) return;

        ItemStack thrownItem = event.getItemStack();
        ThrownExpBottle bottle = (ThrownExpBottle) event.getProjectile();

        if (IsGrandExp(thrownItem)) {
            bottle.setMetadata("expBottleType", new FixedMetadataValue(PlanCEvent.getInstance(), "grand"));
        } else if (IsTitanicExp(thrownItem)) {
            bottle.setMetadata("expBottleType", new FixedMetadataValue(PlanCEvent.getInstance(), "titanic"));
        } else if (IsColossalExp(thrownItem)) {
            bottle.setMetadata("expBottleType", new FixedMetadataValue(PlanCEvent.getInstance(), "colossal"));
        }
    }

    @EventHandler
    public void onBottleBreak(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof ThrownExpBottle)) return;

        ThrownExpBottle bottle = (ThrownExpBottle) event.getEntity();
        if (!(bottle.getShooter() instanceof Player)) return;
        Player player = (Player) bottle.getShooter();

        if (!bottle.hasMetadata("expBottleType")) return;

        String type = bottle.getMetadata("expBottleType").get(0).asString();

        switch (type) {
            case "grand":
                player.giveExp(5345);
                break;
            case "titanic":
                player.giveExp(496595);
                break;
            case "colossal":
                player.giveExp(1797845);
        }
    }



    private static boolean IsGrandExp(ItemStack main) {
        if (main == null || !main.hasItemMeta()) return false;

        var meta = main.getItemMeta();
        var container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_grand");

        return container.has(key, PersistentDataType.BYTE);
    }

    private static boolean IsTitanicExp(ItemStack main) {
        if (main == null || !main.hasItemMeta()) return false;

        var meta = main.getItemMeta();
        var container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_titanic");

        return container.has(key, PersistentDataType.BYTE);
    }

    private static boolean IsColossalExp(ItemStack main) {
        if (main == null || !main.hasItemMeta()) return false;

        var meta = main.getItemMeta();
        var container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PlanCEvent.getInstance(), "is_colossal");

        return container.has(key, PersistentDataType.BYTE);
    }
}
