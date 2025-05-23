package de.illy.planCEvent.dungeons.generator;

public class Room {
    private final int width;
    private final int height;
    private final RoomType roomType;
    public Room(int width, int height, RoomType roomType) {
        this.width = width;
        this.height = height;
        this.roomType = roomType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}

