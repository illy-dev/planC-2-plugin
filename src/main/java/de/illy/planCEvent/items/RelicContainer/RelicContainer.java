package de.illy.planCEvent.items.RelicContainer;

import de.illy.planCEvent.items.AbilityTrigger;
import de.illy.planCEvent.items.CustomItemBuilder;
import de.illy.planCEvent.util.GetHead;
import org.bukkit.inventory.ItemStack;

public class RelicContainer {
    static String RelicContainerBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1Y2Q2NzIyMWZhYTRiOTFmNjg0NzE1NzM5ODc4N2YxYjAwMzY1YmEyYjRhN2E2MmVhMmY2MzI3MDQwMTQ4OSJ9fX0=";
    public static ItemStack create() {
        ItemStack relicHead = GetHead.getCustomHead(RelicContainerBase64, "Relic Container");

        return new CustomItemBuilder(relicHead)
                .setDisplayName("§5Relic Container")
                .addCustomEnchantmentLore("§7Open to receive one random Relic!")
                .setRarity("§5§lEPIC")
                .setAbility(AbilityTrigger.RIGHT_CLICK)
                .setAbilityClass(RelicContainerAbility.class)
                .setUnbreakable(true)
                .addTag("is_relic_container")
                .build();
    }


}
