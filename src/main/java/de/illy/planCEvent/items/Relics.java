package de.illy.planCEvent.items;

import de.illy.planCEvent.util.GetHead;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Relics {

    public static final ItemStack RELIC_PHANTOM;
    public static final ItemStack RELIC_FIRE;
    public static final ItemStack RELIC_VOID;
    public static final ItemStack RELIC_COIN;
    public static final ItemStack RELIC_RAGE;
    public static final ItemStack RELIC_MAGNET;

    static String base1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZiZjZhM2I2MGUyMDY0MDI1ZTJjNzNkZTJiNzUxOThiNjJkMzU4MmExMDZlZDgzOWI2MDcwNjA4ODY5NmYxNSJ9fX0=";
    static String base2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU5YTYxZTExM2NjYmVkYWVjNTdjYjk2ODY4MGNmYWNhMGQ5ODdmZjJkMDgzZjVhZWYwNTg2YTUwMzE2YjI4ZCJ9fX0=";
    static String base3 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM4Y2NkNWY4NjNkODJiYjA5N2I5MjZiYzVmNGNjYTk3YjE5ZjQ2ZTExYjNhM2E1OWQwMDFhZGI4OTg4Njc3MyJ9fX0=";
    static String base4 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY4MjRkMTYyMmQwMDQ1NzgxNmJhMDJjN2ZhNjYwMmQ3ZDZkMTFkZmEzYzk2ODRlMGQ5NDQxMmVkNTllODQyNCJ9fX0=";
    static String base5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ2MjBlNGUzZDNhYmZlZDZhZDgxYTU4YTU2YmNkMDg1ZDllOWVmYzgwM2NhYmIyMWZhNmM5ZTM5NjllMmQyZSJ9fX0=";
    static String base6RARE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUyZDYxMmViZTZjMzk5OTZiNWE5MDVhNzdmNzJkMjhmNmRlZjgxZmU4Yjk3NTU2ZTI2MmQyNzJmODdlOWJiMCJ9fX0=";
    static String base7 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVmNDg2MWFhNWIyMmVlMjhhOTBlNzVkYWI0NWQyMjFlZmQxNGMwYjFlY2M4ZWU5OThmYjY3ZTQzYmI4ZjNkZSJ9fX0=";
    static String base8 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM4Y2NkNWY4NjNkODJiYjA5N2I5MjZiYzVmNGNjYTk3YjE5ZjQ2ZTExYjNhM2E1OWQwMDFhZGI4OTg4Njc3MyJ9fX0=";
    static String base9 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI3Mzc0MGQ0NTRkZTk2MjQ4NDcxMmY5ODM1ZTM1MTE5YjM3YWI4NjdmYTY5ODJkNWNjMWYzMzNjMjMzNGU1OSJ9fX0=";
    static String base10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkzM2NjYWFlZWZhODNhNjFmNWYzZmM5NDMwYTcwOGQ1Nzc4OTA5NjA3MDljN2I5YzY2ZjIxNTBiZDUyMzU2MSJ9fX0=";

    static {
        RELIC_PHANTOM = new CustomItemBuilder(GetHead.getCustomHead(
                base9, "§5Phantom Relic"))
                .setAbilityName("§6Peaceful Night")
                .addAbilityDescriptionLine("§7Monsters don't chase you at Night.")
                .setRarity("§5§l✦ EPIC RELIC")
                .addTag("is_relic")
                .setRelicId("phantom_relic")
                .build();

        RELIC_FIRE = new CustomItemBuilder(GetHead.getCustomHead(
                base2, "§5Lava Relic"))
                .setAbilityName("§6Fire Immunity")
                .addAbilityDescriptionLine("§7Immune to fire damage")
                .setRarity("§5§l✦ EPIC RELIC")
                .addTag("is_relic")
                .setRelicId("fire_relic")
                .build();

        RELIC_COIN = new CustomItemBuilder(GetHead.getCustomHead(
                base7, "§6Coin Relic"))
                .setAbilityName("§6Coin Duplication")
                .addAbilityDescriptionLine("§7Get §a+1§7 Coin more from Rewards")
                .addAbilityDescriptionLine("§7(stackable)")
                .setRarity("§6§l✦ LEGENDARY RELIC")
                .addTag("is_relic")
                .setRelicId("coin_relic")
                .build();

        RELIC_VOID = new CustomItemBuilder(GetHead.getCustomHead(
                base10, "§d§kaaa §r§dVoid Relic §kaaa"))
                .setAbilityName("§6Black hole")
                .addAbilityDescriptionLine("§7Killing an enemy spawns a Black hole")
                .addAbilityDescriptionLine("§7that drags mobs nearby inside.")
                .setRarity("§d§l✦ MYTHIC RELIC")
                .addTag("is_relic")
                .setRelicId("void_relic")
                .build();

        RELIC_RAGE = new CustomItemBuilder(GetHead.getCustomHead(
                base8, "§5Rage Relic"))
                .setAbilityName("§6Rage Mode")
                .addAbilityDescriptionLine("§7Receive temporary buffs when health")
                .addAbilityDescriptionLine("§7drops below 20%.")
                .setRarity("§5§l✦ EPIC RELIC")
                .addTag("is_relic")
                .setRelicId("rage_relic")
                .build();

        RELIC_MAGNET = new CustomItemBuilder(GetHead.getCustomHead(
                base8, "§5Magnet Relic"))
                .setAbilityName("§6Item Magnet")
                .addAbilityDescriptionLine("§7Pulls dropped nearby Items")
                .addAbilityDescriptionLine("§7towards you.")
                .setRarity("§5§l✦ EPIC RELIC")
                .addTag("is_relic")
                .setRelicId("magnet_relic")
                .build();
    }
}
