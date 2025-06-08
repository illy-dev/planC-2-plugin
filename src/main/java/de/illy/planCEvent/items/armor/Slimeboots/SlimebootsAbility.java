package de.illy.planCEvent.items.armor.Slimeboots;

import de.illy.planCEvent.items.ItemAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SlimebootsAbility implements ItemAbility {
    private static final Set<UUID> activePlayers = new HashSet<>();

    @Override
    public void execute(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 2, false, false));
        activePlayers.add(player.getUniqueId());
    }

    @Override
    public void remove(Player player) {
        player.removePotionEffect(PotionEffectType.JUMP_BOOST);
        activePlayers.remove(player.getUniqueId());
    }

    public static boolean hasFallDamageImmunity(Player player) {
        return activePlayers.contains(player.getUniqueId());
    }
}
