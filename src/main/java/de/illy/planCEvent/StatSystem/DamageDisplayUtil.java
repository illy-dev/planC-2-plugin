package de.illy.planCEvent.StatSystem;
import de.illy.planCEvent.PlanCEvent;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageDisplayUtil {

    private static final char[] GRADIENT_COLORS = {
            'f', // White
            'e', // Yellow
            '6', // Orange
            'c', // Red
            '6', // Orange
            'e', // Yellow
            'f'  // White
    };

    public static String gradientDamageText(String text) {
        int len = text.length();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);

            double pos = (double) i / (len - 1);

            int colorIndex = (int) Math.round(pos * (GRADIENT_COLORS.length - 1));
            char colorCode = GRADIENT_COLORS[colorIndex];

            builder.append('§').append(colorCode).append(c);
        }

        return builder.toString();
    }

    public static void showDamage(LivingEntity target, int damage, boolean isCrit) {
        if (target.isInvulnerable()) return;
        Location loc = target.getEyeLocation().clone().add(0, 0.5, 0);


        ArmorStand armorStand = target.getWorld().spawn(loc, ArmorStand.class, stand -> {
            stand.setVisible(false);
            stand.setInvulnerable(true);
            stand.setGravity(false);
            stand.setCanPickupItems(false);
            stand.setCustomNameVisible(true);
            stand.setMarker(true);
        });

        String damageText = "";

        if (isCrit) {
            damageText = "§f◇" + DamageDisplayUtil.gradientDamageText(String.valueOf(damage)) + "§f◇";
        } else {
            damageText = "§7" + damage;
        }

        armorStand.setCustomName(damageText);

        // removed nach 2 sekunden◇
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(PlanCEvent.getInstance(), 40L);
    }
}

