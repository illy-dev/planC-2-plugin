package de.illy.planCEvent.dungeons.generator;

public enum RoomType {
    EMPTY(3, 3),   // A simple empty room with 3x3 grid
    TREASURE(4, 4), // A treasure room with a 4x4 grid
    MONSTER(3, 3), // A monster room with a 3x3 grid
    BOSS(6, 6);    // A boss room with a 6x6 grid

    private final int width;
    private final int height;

    RoomType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

