package de.illy.planCEvent.dungeons.generator;

import java.util.*;

public class DungeonUtils {
    public static List<DungeonGenerator.Room> generateMainPath(Random random, DungeonGenerator.Room[][] grid, DungeonGenerator.Room start, DungeonGenerator.Room end,
                                                               int minLength, int maxLength) {
        List<DungeonGenerator.Room> path = new ArrayList<>();
        path.add(start);

        DungeonGenerator.Point current = start.getCoordinates();
        DungeonGenerator.Point target = end.getCoordinates();

        while (!current.equals(target)) {
            int dx = Integer.compare(target.x(), current.x());
            int dy = Integer.compare(target.y(), current.y());

            if (random.nextDouble() < 0.7) {
                if (random.nextBoolean() && dx != 0) {
                    current = new DungeonGenerator.Point(current.x() + dx, current.y());
                } else if (dy != 0) {
                    current = new DungeonGenerator.Point(current.x(), current.y() + dy);
                }
            } else {
                if (random.nextBoolean()) {
                    current = new DungeonGenerator.Point(current.x() + random.nextInt(3) - 1, current.y());
                } else {
                    current = new DungeonGenerator.Point(current.x(), current.y() + random.nextInt(3) - 1);
                }
            }

            current = new DungeonGenerator.Point(
                    Math.max(0, Math.min(grid.length - 1, current.x())),
                    Math.max(0, Math.min(grid[0].length - 1, current.y()))
            );

            DungeonGenerator.Room nextRoom = grid[current.x()][current.y()];
            if (!path.contains(nextRoom)) {
                path.add(nextRoom);
            }
        }

        while (path.size() < minLength) {
            int insertPos = random.nextInt(path.size() - 1) + 1;
            DungeonGenerator.Point prev = path.get(insertPos - 1).getCoordinates();
            DungeonGenerator.Point next = path.get(insertPos).getCoordinates();
            DungeonGenerator.Point mid = new DungeonGenerator.Point((prev.x() + next.x()) / 2, (prev.y() + next.y()) / 2);
            path.add(insertPos, grid[mid.x()][mid.y()]);
        }

        return path;
    }
}