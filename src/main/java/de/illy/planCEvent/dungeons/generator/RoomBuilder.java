package de.illy.planCEvent.dungeons.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class RoomBuilder {
    public static void buildRoom(Room room, World world, int x, int y, int z) {
        switch (room.getRoomType()) {
            case TREASURE:
                buildTreasureRoom(world, x, y, z);
                break;
            case MONSTER:
                buildMonsterRoom(world, x, y, z);
                break;
            case EMPTY:
                buildEmptyRoom(world, x, y, z);
                break;
            case BOSS:
                buildBossRoom(world, x, y, z);
                break;
            default:
                break;
        }
    }

    private static void buildTreasureRoom(World world, int x, int y, int z) {
        // Add treasure (chests, loot) to the room
        world.getBlockAt(x, y, z).setType(Material.CHEST);.
    }

    private static void buildMonsterRoom(World world, int x, int y, int z) {
        // Add monsters to the room
        world.spawnEntity(new Location(world, x, y, z), EntityType.ZOMBIE);
    }

    private static void buildEmptyRoom(World world, int x, int y, int z) {
        // fill with air
        world.getBlockAt(x, y, z).setType(Material.STONE);
    }

    private static void buildBossRoom(World world, int x, int y, int z)
        world.getBlockAt(x, y, z).setType(Material.DIAMOND_BLOCK);
    }
}
