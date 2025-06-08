package de.illy.planCEvent.items;

import de.illy.planCEvent.util.GetHead;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Relics {

    public static final ItemStack RELIC_MOON;
    public static final ItemStack RELIC_FIRE;
    public static final ItemStack RELIC_VOID;
    public static final ItemStack RELIC_LIGHT;

    static String base1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZiZjZhM2I2MGUyMDY0MDI1ZTJjNzNkZTJiNzUxOThiNjJkMzU4MmExMDZlZDgzOWI2MDcwNjA4ODY5NmYxNSJ9fX0=";
    static String base2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU5YTYxZTExM2NjYmVkYWVjNTdjYjk2ODY4MGNmYWNhMGQ5ODdmZjJkMDgzZjVhZWYwNTg2YTUwMzE2YjI4ZCJ9fX0=";
    static String base3 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM4Y2NkNWY4NjNkODJiYjA5N2I5MjZiYzVmNGNjYTk3YjE5ZjQ2ZTExYjNhM2E1OWQwMDFhZGI4OTg4Njc3MyJ9fX0=";
    static String base4 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY4MjRkMTYyMmQwMDQ1NzgxNmJhMDJjN2ZhNjYwMmQ3ZDZkMTFkZmEzYzk2ODRlMGQ5NDQxMmVkNTllODQyNCJ9fX0=";
    static String base5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ2MjBlNGUzZDNhYmZlZDZhZDgxYTU4YTU2YmNkMDg1ZDllOWVmYzgwM2NhYmIyMWZhNmM5ZTM5NjllMmQyZSJ9fX0=";
    static String base6 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUyZDYxMmViZTZjMzk5OTZiNWE5MDVhNzdmNzJkMjhmNmRlZjgxZmU4Yjk3NTU2ZTI2MmQyNzJmODdlOWJiMCJ9fX0=";
    static String base7 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVmNDg2MWFhNWIyMmVlMjhhOTBlNzVkYWI0NWQyMjFlZmQxNGMwYjFlY2M4ZWU5OThmYjY3ZTQzYmI4ZjNkZSJ9fX0=";
    static String base8 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIxMTRhODAzNTc0NjNmZTJmNTllMzk3YWFiOWZjNjZkNDgyYTY1ZDUyNGY4ODcwZDIxYzcyNGMxOGVjZjc1NyJ9fX0=";
    static String base9 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI3Mzc0MGQ0NTRkZTk2MjQ4NDcxMmY5ODM1ZTM1MTE5YjM3YWI4NjdmYTY5ODJkNWNjMWYzMzNjMjMzNGU1OSJ9fX0=";
    static String base10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkzM2NjYWFlZWZhODNhNjFmNWYzZmM5NDMwYTcwOGQ1Nzc4OTA5NjA3MDljN2I5YzY2ZjIxNTBiZDUyMzU2MSJ9fX0=";

    static {
        RELIC_MOON = new CustomItemBuilder(GetHead.getCustomHead(
                base1, "§5Moon Relic"))
                .setAbilityName("§6Flame Burst")
                .addAbilityDescriptionLine("§7wichtige beschreibung")
                .setRarity("§5✦ Epic Relic")
                .addTag("is_relic")
                .build();

        RELIC_FIRE = new CustomItemBuilder(GetHead.getCustomHead(
                base2, "§5Lava Relic"))
                .setAbilityName("§6§lFire Immunity")
                .addAbilityDescriptionLine("§7Immune to fire damage")
                .setRarity("§5✦ Epic Relic")
                .addTag("is_relic")
                .setRelicId("fire_relic")
                .build();

        RELIC_VOID = new CustomItemBuilder(GetHead.getCustomHead(
                base3, "§5Strength Relic"))
                .setAbilityName("§6stärke")
                .addAbilityDescriptionLine("§7ok")
                .setRarity("§5✦ Epic Relic")
                .addTag("is_relic")
                .build();

        RELIC_LIGHT = new CustomItemBuilder(GetHead.getCustomHead(
                base10, "§d§kaaa §r§dVoid Relic §kaaa"))
                .setAbilityName("§6Black hole")
                .addAbilityDescriptionLine("§7Killing an enemy spawns a Black hole")
                .addAbilityDescriptionLine("§7that drags mobs nearby inside.")
                .setRarity("§d✦ Mythic Relic")
                .addTag("is_relic")
                .build();
    }
}
