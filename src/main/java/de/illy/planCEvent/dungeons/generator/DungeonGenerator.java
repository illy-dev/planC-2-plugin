package de.illy.planCEvent.dungeons.generator;

import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.block.Block;
import java.util.*;

public class DungeonGenerator {
    // Grid dimensions (5x6)
    private static final int GRID_SIZE_X = 5;
    private static final int GRID_SIZE_Y = 6;
    private final Room[][] grid = new Room[GRID_SIZE_X][GRID_SIZE_Y];

    private List<Room> mainPath = new ArrayList<>();
    private final Map<Room, RoomShapeHandler> shapeHandlers = new LinkedHashMap<>();
    private Random random;
    private World world;
    private int startY;

    // Room sizes
    private final int smallRoomSize = 30;
    private final int largeRoom60x60Size = 63;
    private final int largeRoom30x60Width = 30;
    private final int largeRoom30x60Length = 63;
    private final int largeRoom90x30Width = 96;
    private final int largeRoom90x30Length = 30;
    private final int gap = 3;

    public DungeonGenerator(World world, int startY) {
        this.world = world;
        this.startY = startY;
    }

    public void generate(long seed) {
        this.random = new Random(seed);

        initializeGrid();
        placeStartAndEndRooms();
        placeLargeRooms();
        generateMainPath();
        placeFairyRoom();
        generateAndPlaceShapes();
        removeIntraShapeConnections();
        connectAllRooms();
        placeSpecialRooms();
        placeDungeonInWorld();
    }

    private void initializeGrid() {
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                grid[x][y] = new Room(new Point(x, y), RoomType.BROWN);
            }
        }
    }

    private void placeStartAndEndRooms() {
        Point startCoords = getRandomWallPosition(random);
        Point endCoords;

        do {
            endCoords = getRandomWallPosition(random);
        } while (isSameWall(startCoords, endCoords) || distance(startCoords, endCoords) < 4);

        getRoomAt(startCoords).setType(RoomType.GREEN);
        getRoomAt(endCoords).setType(RoomType.RED);
    }

    private void placeLargeRooms() {
        // Place 60x60 room (2x2 grid cells)
        placeSpecificLargeRoom(2, 2, RoomType.LARGE_60x60);

        // Place 30x60 room (1x2 grid cells)
        placeSpecificLargeRoom(1, 2, RoomType.LARGE_30x60);

        // Place 90x30 room (3x1 grid cells)
        placeSpecificLargeRoom(3, 1, RoomType.LARGE_90x30);
    }

    private void placeSpecificLargeRoom(int gridWidth, int gridHeight, RoomType roomType) {
        int attempts = 0;
        int maxAttempts = 100;

        while (attempts++ < maxAttempts) {
            int x = random.nextInt(GRID_SIZE_X - gridWidth + 1);
            int y = random.nextInt(GRID_SIZE_Y - gridHeight + 1);

            if (canPlaceLargeRoom(x, y, gridWidth, gridHeight)) {
                markAsLargeRoom(x, y, gridWidth, gridHeight, roomType);
                return;
            }
        }
        throw new RuntimeException("Failed to place " + roomType + " after " + maxAttempts + " attempts");
    }

    private boolean canPlaceLargeRoom(int x, int y, int gridWidth, int gridHeight) {
        for (int dx = 0; dx < gridWidth; dx++) {
            for (int dy = 0; dy < gridHeight; dy++) {
                Room room = grid[x + dx][y + dy];
                if (room.getType() != RoomType.BROWN || room.getRoomWidth() > 1 || room.getRoomHeight() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void markAsLargeRoom(int x, int y, int gridWidth, int gridHeight, RoomType roomType) {
        for (int dx = 0; dx < gridWidth; dx++) {
            for (int dy = 0; dy < gridHeight; dy++) {
                Room room = grid[x + dx][y + dy];
                room.setType(roomType);
                room.setRoomSize(gridWidth, gridHeight);
                if (dx == 0 && dy == 0) {
                    room.setMainRoom(true);
                }
            }
        }
    }

    private void generateMainPath() {
        Room startRoom = getRoomOfType(RoomType.GREEN);
        Room endRoom = getRoomOfType(RoomType.RED);

        // First generate connections using binary tree algorithm
        generateBinaryTreeConnections();

        // Then find path from start to end
        this.mainPath = findPath(startRoom, endRoom);

        // Ensure the path exists and is of reasonable length
        if (mainPath == null || mainPath.size() < 5 || mainPath.size() > 20) {
            // If path is invalid, fall back to original method
            mainPath = DungeonUtils.generateMainPath(random, grid, startRoom, endRoom, 10, 14);
        }

        // Mark connections along main path
        for (int i = 0; i < mainPath.size() - 1; i++) {
            Room current = mainPath.get(i);
            Room next = mainPath.get(i + 1);
            current.addConnection(next);
            next.addConnection(current);
        }
    }

    private void generateBinaryTreeConnections() {
        // Choose a connection pattern (NORTH/WEST, NORTH/EAST, SOUTH/WEST, or SOUTH/EAST)
        int pattern = random.nextInt(4);

        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room current = grid[x][y];
                List<Room> possibleConnections = new ArrayList<>();

                // Add possible connections based on chosen pattern
                switch (pattern) {
                    case 0: // NORTH/WEST
                        if (y > 0) possibleConnections.add(grid[x][y-1]); // North
                        if (x > 0) possibleConnections.add(grid[x-1][y]); // West
                        break;
                    case 1: // NORTH/EAST
                        if (y > 0) possibleConnections.add(grid[x][y-1]); // North
                        if (x < GRID_SIZE_X-1) possibleConnections.add(grid[x+1][y]); // East
                        break;
                    case 2: // SOUTH/WEST
                        if (y < GRID_SIZE_Y-1) possibleConnections.add(grid[x][y+1]); // South
                        if (x > 0) possibleConnections.add(grid[x-1][y]); // West
                        break;
                    case 3: // SOUTH/EAST
                        if (y < GRID_SIZE_Y-1) possibleConnections.add(grid[x][y+1]); // South
                        if (x < GRID_SIZE_X-1) possibleConnections.add(grid[x+1][y]); // East
                        break;
                }

                // Randomly choose one of the possible connections
                if (!possibleConnections.isEmpty()) {
                    Room neighbor = possibleConnections.get(random.nextInt(possibleConnections.size()));
                    current.addConnection(neighbor);
                    neighbor.addConnection(current);
                }
            }
        }

        // Ensure start and end rooms are connected to the main path
        Room startRoom = getRoomOfType(RoomType.GREEN);
        Room endRoom = getRoomOfType(RoomType.RED);

        if (startRoom != null && !startRoom.getConnectedRooms().isEmpty()) {
            Room neighbor = startRoom.getConnectedRooms().iterator().next();
            mainPath.add(startRoom);
            mainPath.add(neighbor);
        }

        if (endRoom != null && !endRoom.getConnectedRooms().isEmpty()) {
            Room neighbor = endRoom.getConnectedRooms().iterator().next();
            mainPath.add(neighbor);
            mainPath.add(endRoom);
        }
    }

    private List<Room> findPath(Room start, Room end) {
        // Simple BFS to find path from start to end
        Map<Room, Room> cameFrom = new HashMap<>();
        Queue<Room> queue = new LinkedList<>();
        Set<Room> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Room current = queue.poll();

            if (current == end) {
                // Reconstruct path
                List<Room> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = cameFrom.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (Room neighbor : current.getConnectedRooms()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return null; // No path found
    }

    private void placeFairyRoom() {
        if (mainPath.size() < 7) return;

        List<Room> candidates = mainPath.subList(3, mainPath.size() - 3);
        if (!candidates.isEmpty()) {
            candidates.get(random.nextInt(candidates.size())).setType(RoomType.PINK);
        }
    }

    private void generateAndPlaceShapes() {
        RoomShape[] shapes = Arrays.stream(RoomShape.values())
                .filter(shape -> !shape.isAvoid())
                .toArray(RoomShape[]::new);

        for (int i = 0; i < 100; i++) {
            RoomShape shape = shapes[random.nextInt(shapes.length)];
            int rotation = random.nextInt(4);
            Point coords = new Point(random.nextInt(GRID_SIZE_X), random.nextInt(GRID_SIZE_Y));
            if (canPlaceShape(shape, coords, rotation)) {
                placeShapeAt(shape, coords, rotation);
            }
        }

        // Mark remaining as single rooms
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];
                if (room.getType() == RoomType.BROWN && !shapeHandlers.containsKey(room)) {
                    RoomShapeHandler handler = new RoomShapeHandler(RoomShape.SINGLE, 0);
                    handler.addRoom(room);
                    shapeHandlers.put(room, handler);
                }
            }
        }
    }

    private boolean canPlaceShape(RoomShape shape, Point coords, int rotation) {
        Point[] points = shape.getRotatedPoints(rotation);
        for (Point p : points) {
            int x = coords.x() + p.x();
            int y = coords.y() + p.y();
            if (x < 0 || x >= GRID_SIZE_X || y < 0 || y >= GRID_SIZE_Y ||
                    grid[x][y].getType() != RoomType.BROWN ||
                    shapeHandlers.containsKey(grid[x][y])) {
                return false;
            }
        }
        return true;
    }

    private void placeShapeAt(RoomShape shape, Point coords, int rotation) {
        RoomShapeHandler handler = new RoomShapeHandler(shape, rotation);
        Point[] points = shape.getRotatedPoints(rotation);
        for (Point p : points) {
            Room room = grid[coords.x() + p.x()][coords.y() + p.y()];
            handler.addRoom(room);
            shapeHandlers.put(room, handler);
        }
    }

    private void removeIntraShapeConnections() {
        for (RoomShapeHandler handler : shapeHandlers.values()) {
            for (Room room : handler.getRooms()) {
                room.getConnectedRooms().removeIf(handler.getRooms()::contains);
            }
        }
    }

    private void connectAllRooms() {
        Set<Room> connected = new HashSet<>(mainPath);
        List<Room> toConnect = new ArrayList<>(mainPath);

        int maxAttempts = 1000;
        int attempts = 0;

        while (hasUnconnectedRooms() && attempts++ < maxAttempts) {
            Room current = toConnect.get(random.nextInt(toConnect.size()));

            for (Room neighbor : getNeighbors(current)) {
                if (canConnect(current, neighbor, connected)) {
                    current.addConnection(neighbor);
                    neighbor.addConnection(current);
                    connected.add(neighbor);
                    toConnect.add(neighbor);
                    break;
                }
            }
        }
    }

    private boolean canConnect(Room a, Room b, Set<Room> connected) {
        if (!a.getType().equals(RoomType.BROWN)) return false;
        if (!b.getType().equals(RoomType.BROWN)) return false;
        if (areConnected(a, b)) return false;
        return connected.contains(a) || connected.contains(b);
    }

    private boolean areConnected(Room a, Room b) {
        return a.getConnectedRooms().contains(b) || b.getConnectedRooms().contains(a);
    }

    private boolean hasUnconnectedRooms() {
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];
                if (room.getType().equals(RoomType.BROWN)) {
                    if (room.getConnectedRooms().isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Room> getNeighbors(Room room) {
        List<Room> neighbors = new ArrayList<>();
        Point p = room.getCoordinates();

        if ((room.getRoomWidth() > 1 || room.getRoomHeight() > 1) && !room.isMainRoom()) {
            return neighbors;
        }

        if (p.x() > 0) neighbors.add(grid[p.x()-1][p.y()]);
        if (p.x() < GRID_SIZE_X-1) neighbors.add(grid[p.x()+1][p.y()]);
        if (p.y() > 0) neighbors.add(grid[p.x()][p.y()-1]);
        if (p.y() < GRID_SIZE_Y-1) neighbors.add(grid[p.x()][p.y()+1]);

        return neighbors;
    }

    private void placeSpecialRooms() {
        List<Room> candidates = new ArrayList<>();
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];
                if (room.getType() == RoomType.BROWN &&
                        shapeHandlers.get(room).getShape() == RoomShape.SINGLE &&
                        room.getConnectedRooms().size() == 1) {
                    candidates.add(room);
                }
            }
        }

        Collections.shuffle(candidates, random);
        int[] counts = {1, 1, 3}; // YELLOW, ORANGE, PURPLE
        RoomType[] types = {RoomType.YELLOW, RoomType.ORANGE, RoomType.PURPLE};

        int index = 0;
        for (int i = 0; i < counts.length && index < candidates.size(); i++) {
            for (int j = 0; j < counts[i] && index < candidates.size(); j++) {
                candidates.get(index++).setType(types[i]);
            }
        }
    }

    private void placeDungeonInWorld() {
        // First place all rooms
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];

                if ((room.getRoomWidth() > 1 || room.getRoomHeight() > 1) && !room.isMainRoom()) {
                    continue;
                }

                Material material = getMaterialForRoomType(room.getType());
                int worldX = x * (smallRoomSize + gap);
                int worldZ = y * (smallRoomSize + gap);

                int sizeX, sizeZ;
                if (room.getType() == RoomType.LARGE_60x60) {
                    sizeX = largeRoom60x60Size;
                    sizeZ = largeRoom60x60Size;
                } else if (room.getType() == RoomType.LARGE_30x60) {
                    sizeX = largeRoom30x60Width;
                    sizeZ = largeRoom30x60Length;
                } else if (room.getType() == RoomType.LARGE_90x30) {
                    sizeX = largeRoom90x30Width;
                    sizeZ = largeRoom90x30Length;
                } else {
                    sizeX = smallRoomSize;
                    sizeZ = smallRoomSize;
                }

                // Place floor
                for (int dx = 0; dx < sizeX; dx++) {
                    for (int dz = 0; dz < sizeZ; dz++) {
                        world.getBlockAt(worldX + dx, startY, worldZ + dz).setType(material);
                    }
                }
            }
        }

        // Then place connections between rooms
        placeRoomConnections();
    }

    private void placeRoomConnections() {
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];
                int roomSize = getRoomSize(room);
                int worldX = x * (smallRoomSize + gap);
                int worldZ = y * (smallRoomSize + gap);

                for (Room connectedRoom : room.getConnectedRooms()) {
                    Point other = connectedRoom.getCoordinates();

                    // Only create connections in one direction to avoid duplicates
                    if (x > other.x() || (x == other.x() && y > other.y())) {
                        continue;
                    }

                    int otherWorldX = other.x() * (smallRoomSize + gap);
                    int otherWorldZ = other.y() * (smallRoomSize + gap);
                    int otherRoomSize = getRoomSize(connectedRoom);

                    if (x == other.x()) {
                        // Vertical connection (north/south)
                        int centerX = worldX + roomSize/2;
                        int startZ, endZ;

                        if (y < other.y()) { // Current room is north of connected room
                            startZ = worldZ + roomSize;
                            endZ = otherWorldZ;
                        } else { // Current room is south of connected room
                            startZ = otherWorldZ + otherRoomSize;
                            endZ = worldZ;
                        }

                        // Place a 3-block wide corridor
                        for (int dz = startZ; dz < endZ; dz++) {
                            for (int dx = centerX - 1; dx <= centerX + 1; dx++) {
                                world.getBlockAt(dx, startY, dz).setType(Material.OAK_PLANKS);
                            }
                        }
                    } else if (y == other.y()) {
                        // Horizontal connection (east/west)
                        int centerZ = worldZ + roomSize/2;
                        int startX, endX;

                        if (x < other.x()) { // Current room is west of connected room
                            startX = worldX + roomSize;
                            endX = otherWorldX;
                        } else { // Current room is east of connected room
                            startX = otherWorldX + otherRoomSize;
                            endX = worldX;
                        }

                        // Place a 3-block wide corridor
                        for (int dx = startX; dx < endX; dx++) {
                            for (int dz = centerZ - 1; dz <= centerZ + 1; dz++) {
                                world.getBlockAt(dx, startY, dz).setType(Material.OAK_PLANKS);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getRoomSize(Room room) {
        if (room.getType() == RoomType.LARGE_60x60) return largeRoom60x60Size;
        if (room.getType() == RoomType.LARGE_30x60) return largeRoom30x60Width;
        if (room.getType() == RoomType.LARGE_90x30) return largeRoom90x30Width;
        return smallRoomSize;
    }

    private Material getMaterialForRoomType(RoomType type) {
        return switch (type) {
            case GREEN -> Material.EMERALD_BLOCK;
            case RED -> Material.REDSTONE_BLOCK;
            case PINK -> Material.PINK_WOOL;
            case YELLOW -> Material.YELLOW_WOOL;
            case ORANGE -> Material.ORANGE_WOOL;
            case PURPLE -> Material.PURPLE_WOOL;
            case LARGE_60x60 -> Material.QUARTZ_BLOCK;
            case LARGE_30x60 -> Material.SMOOTH_STONE;
            case LARGE_90x30 -> Material.BRICKS;
            default -> Material.STONE;
        };
    }

    private Point getRandomWallPosition(Random random) {
        int side = random.nextInt(4);
        return switch (side) {
            case 0 -> new Point(random.nextInt(GRID_SIZE_X), 0); // North wall
            case 1 -> new Point(random.nextInt(GRID_SIZE_X), GRID_SIZE_Y-1); // South wall
            case 2 -> new Point(0, random.nextInt(GRID_SIZE_Y)); // West wall
            default -> new Point(GRID_SIZE_X-1, random.nextInt(GRID_SIZE_Y)); // East wall
        };
    }

    private boolean isSameWall(Point a, Point b) {
        return (a.x() == 0 && b.x() == 0) || // West wall
                (a.x() == GRID_SIZE_X-1 && b.x() == GRID_SIZE_X-1) || // East wall
                (a.y() == 0 && b.y() == 0) || // North wall
                (a.y() == GRID_SIZE_Y-1 && b.y() == GRID_SIZE_Y-1); // South wall
    }

    private double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2));
    }

    private Room getRoomAt(Point point) {
        return grid[point.x()][point.y()];
    }

    private Room getRoomOfType(RoomType type) {
        for (int x = 0; x < GRID_SIZE_X; x++) {
            for (int y = 0; y < GRID_SIZE_Y; y++) {
                Room room = grid[x][y];
                if (room.getType() == type) {
                    return room;
                }
            }
        }
        return null;
    }

    public static class Room {
        private final Point coordinates;
        private RoomType type;
        private RoomShape shape;
        private final Set<Room> connectedRooms = new HashSet<>();
        private int roomWidth = 1;
        private int roomHeight = 1;
        private boolean isMainRoom = false;

        public Room(Point coordinates, RoomType type) {
            this.coordinates = coordinates;
            this.type = type;
        }

        public Point getCoordinates() { return coordinates; }
        public RoomType getType() { return type; }
        public void setType(RoomType type) { this.type = type; }
        public RoomShape getShape() { return shape; }
        public void setShape(RoomShape shape) { this.shape = shape; }
        public Set<Room> getConnectedRooms() { return connectedRooms; }
        public void addConnection(Room room) { connectedRooms.add(room); }
        public void removeConnection(Room room) { connectedRooms.remove(room); }
        public int getRoomWidth() { return roomWidth; }
        public int getRoomHeight() { return roomHeight; }
        public void setRoomSize(int width, int height) {
            this.roomWidth = width;
            this.roomHeight = height;
        }
        public boolean isMainRoom() { return isMainRoom; }
        public void setMainRoom(boolean mainRoom) { isMainRoom = mainRoom; }
    }

    public enum RoomType {
        GREEN, RED, PINK, YELLOW, ORANGE, PURPLE, BROWN,
        LARGE_60x60, LARGE_30x60, LARGE_90x30
    }

    public static class Point {
        private final int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }
        public int x() { return x; }
        public int y() { return y; }
        public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point p = (Point)o;
            return x == p.x() && y == p.y();
        }
    }

    public static class RoomShapeHandler {
        private final RoomShape shape;
        private final int rotation;
        private final List<Room> rooms = new ArrayList<>();

        public RoomShapeHandler(RoomShape shape, int rotation) {
            this.shape = shape;
            this.rotation = rotation;
        }

        public RoomShape getShape() { return shape; }
        public int getRotation() { return rotation; }
        public List<Room> getRooms() { return rooms; }
        public void addRoom(Room room) { rooms.add(room); }
    }

    public enum RoomShape {
        SINGLE(false), LINE_2x1(false), L_SHAPE_1(false), SQUARE(false);

        private final boolean avoid;
        RoomShape(boolean avoid) { this.avoid = avoid; }
        public boolean isAvoid() { return avoid; }
        public Point[] getRotatedPoints(int rotation) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(0, 0));

            switch (this) {
                case LINE_2x1:
                    points.add(new Point(1, 0));
                    break;
                case L_SHAPE_1:
                    points.add(new Point(1, 0));
                    points.add(new Point(0, 1));
                    break;
                case SQUARE:
                    points.add(new Point(1, 0));
                    points.add(new Point(0, 1));
                    points.add(new Point(1, 1));
                    break;
            }

            Point[] result = new Point[points.size()];
            for (int i = 0; i < points.size(); i++) {
                Point p = points.get(i);
                int x = p.x();
                int y = p.y();

                for (int r = 0; r < rotation; r++) {
                    int newX = -y;
                    int newY = x;
                    x = newX;
                    y = newY;
                }

                result[i] = new Point(x, y);
            }

            return result;
        }
    }
}