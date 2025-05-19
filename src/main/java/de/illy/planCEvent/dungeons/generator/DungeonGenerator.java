package de.illy.planCEvent.dungeons.generator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import java.util.*;

public class DungeonGenerator {

    // Room size constants
    private static final int ROOM_SIZE_SMALL = 30;
    private static final int ROOM_SIZE_LARGE_WIDTH = 63;
    private static final int ROOM_SIZE_LARGE_LENGTH = 63;
    private static final int ROOM_SIZE_LARGE_WIDTH_60x30 = 63;
    private static final int ROOM_SIZE_LARGE_LENGTH_60x30 = 30;

    // Layout constants
    private static final int GAP = 3;
    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 5;

    // Maze generation constants
    private static final int MAX_CONNECTIONS = 2;
    private static final int DEAD_END_CHANCE = 35;
    private static final int LARGE_ROOM_CHANCE = 20;
    private static final int SHAPED_ROOM_CHANCE = 10;

    // Grid tracking
    private boolean[][] dungeonGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];
    private boolean[][] roomOccupied = new boolean[GRID_WIDTH][GRID_HEIGHT];
    private int[][] connectionCount = new int[GRID_WIDTH][GRID_HEIGHT];
    private List<Connection> connections = new ArrayList<>();
    private RoomType[][] roomTypes = new RoomType[GRID_WIDTH][GRID_HEIGHT];
    private boolean[][] shapedRooms = new boolean[GRID_WIDTH][GRID_HEIGHT];

    // World reference
    private World world = Bukkit.getWorld("dungeon");
    private Random rand = new Random();
    private int startY = 64;

    // Special rooms
    private int[] entranceRoom = new int[2];
    private int[] bloodRoom = new int[2];
    private int[] fairyRoom = new int[2];
    private List<int[]> puzzleRooms = new ArrayList<>();

    private enum RoomType {
        ENTRANCE(Material.EMERALD_BLOCK),
        BLOOD(Material.NETHER_WART_BLOCK),
        FAIRY(Material.GOLD_BLOCK),
        PUZZLE(Material.IRON_BLOCK),
        NORMAL(Material.DIAMOND_BLOCK),
        SHAPED_NORMAL(Material.DIAMOND_BLOCK);

        private final Material floorMaterial;

        RoomType(Material floorMaterial) {
            this.floorMaterial = floorMaterial;
        }

        public Material getFloorMaterial() {
            return floorMaterial;
        }
    }

    private class Connection {
        int[] room1;
        int[] room2;
        int direction; // 0 = north, 1 = east, 2 = south, 3 = west

        Connection(int[] r1, int[] r2, int dir) {
            this.room1 = r1;
            this.room2 = r2;
            this.direction = dir;
        }
    }

    public void generateDungeon(boolean isLowerFloor) {
        int attempts = 0;
        final int MAX_ATTEMPTS = 20;

        do {
            resetGrids();
            placeSpecialRooms();
            placeFairyRoom();
            placePuzzleRooms(isLowerFloor);
            fillNormalRooms(isLowerFloor);
            generateMazeStructure();
            attempts++;

            if (validateDungeonLayout()) {
                placeDungeonInWorld();
                return;
            }

        } while (attempts < MAX_ATTEMPTS);

        // Final attempt with forced connectivity
        generateGuaranteedValidLayout(isLowerFloor);
        placeDungeonInWorld();
    }

    private boolean validateDungeonLayout() {
        // Check all special rooms have exactly one connection
        if (connectionCount[entranceRoom[0]][entranceRoom[1]] != 1 ||
                connectionCount[bloodRoom[0]][bloodRoom[1]] != 1) {
            return false;
        }

        for (int[] puzzleRoom : puzzleRooms) {
            if (connectionCount[puzzleRoom[0]][puzzleRoom[1]] != 1) {
                return false;
            }
        }

        // Verify all rooms are accessible from entrance
        return validateDungeonAccessibility();
    }

    private void generateGuaranteedValidLayout(boolean isLowerFloor) {
        resetGrids();
        placeSpecialRooms();
        placeFairyRoom();
        placePuzzleRooms(isLowerFloor);
        fillNormalRooms(isLowerFloor);

        // Create guaranteed path from entrance to blood room
        createGuaranteedPath(entranceRoom, bloodRoom);

        // Connect all other rooms to this path
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (dungeonGrid[x][z] && !isConnected(new int[]{x, z})) {
                    connectToNearestConnectedRoom(new int[]{x, z});
                }
            }
        }

        // Ensure special rooms have exactly one connection
        ensureSingleConnection(entranceRoom);
        ensureSingleConnection(bloodRoom);
        for (int[] puzzleRoom : puzzleRooms) {
            ensureSingleConnection(puzzleRoom);
        }
    }

    private void createGuaranteedPath(int[] start, int[] end) {
        int[] current = start.clone();

        while (!Arrays.equals(current, end)) {
            int[] next = new int[]{current[0], current[1]};

            // Move toward target with some randomness
            if (current[0] != end[0] && (current[1] == end[1] || rand.nextBoolean())) {
                next[0] += (current[0] < end[0]) ? 1 : -1;
            } else {
                next[1] += (current[1] < end[1]) ? 1 : -1;
            }

            if (isValidRoom(next[0], next[1])) {
                connectRooms(current, next, getDirection(current, next));
                current = next;
            }
        }
    }

    private void resetGrids() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                dungeonGrid[x][z] = false;
                roomOccupied[x][z] = false;
                connectionCount[x][z] = 0;
                roomTypes[x][z] = null;
                shapedRooms[x][z] = false;
            }
        }
        connections.clear();
        puzzleRooms.clear();
    }

    private void placeSpecialRooms() {
        // Place entrance room on a random wall
        int entranceWall = rand.nextInt(4);
        switch (entranceWall) {
            case 0: // Top wall
                entranceRoom[0] = rand.nextInt(GRID_WIDTH);
                entranceRoom[1] = 0;
                break;
            case 1: // Right wall
                entranceRoom[0] = GRID_WIDTH - 1;
                entranceRoom[1] = rand.nextInt(GRID_HEIGHT);
                break;
            case 2: // Bottom wall
                entranceRoom[0] = rand.nextInt(GRID_WIDTH);
                entranceRoom[1] = GRID_HEIGHT - 1;
                break;
            case 3: // Left wall
                entranceRoom[0] = 0;
                entranceRoom[1] = rand.nextInt(GRID_HEIGHT);
                break;
        }
        dungeonGrid[entranceRoom[0]][entranceRoom[1]] = true;
        roomTypes[entranceRoom[0]][entranceRoom[1]] = RoomType.ENTRANCE;

        // Place blood room on opposite wall
        int bloodWall = (entranceWall + 2) % 4;
        switch (bloodWall) {
            case 0: // Top wall
                bloodRoom[0] = rand.nextInt(GRID_WIDTH);
                bloodRoom[1] = 0;
                break;
            case 1: // Right wall
                bloodRoom[0] = GRID_WIDTH - 1;
                bloodRoom[1] = rand.nextInt(GRID_HEIGHT);
                break;
            case 2: // Bottom wall
                bloodRoom[0] = rand.nextInt(GRID_WIDTH);
                bloodRoom[1] = GRID_HEIGHT - 1;
                break;
            case 3: // Left wall
                bloodRoom[0] = 0;
                bloodRoom[1] = rand.nextInt(GRID_HEIGHT);
                break;
        }
        dungeonGrid[bloodRoom[0]][bloodRoom[1]] = true;
        roomTypes[bloodRoom[0]][bloodRoom[1]] = RoomType.BLOOD;
    }

    private void placeFairyRoom() {
        // Get all adjacent positions to blood room
        List<int[]> possibleFairyPositions = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;

                int nx = bloodRoom[0] + dx;
                int ny = bloodRoom[1] + dy;

                if (nx >= 0 && nx < GRID_WIDTH && ny >= 0 && ny < GRID_HEIGHT &&
                        !dungeonGrid[nx][ny]) {
                    possibleFairyPositions.add(new int[]{nx, ny});
                }
            }
        }

        if (!possibleFairyPositions.isEmpty()) {
            int[] pos = possibleFairyPositions.get(rand.nextInt(possibleFairyPositions.size()));
            dungeonGrid[pos[0]][pos[1]] = true;
            roomTypes[pos[0]][pos[1]] = RoomType.FAIRY;
            fairyRoom = pos;
        }
    }

    private void placePuzzleRooms(boolean isLowerFloor) {
        List<int[]> validPositions = new ArrayList<>();

        // Only consider wall positions for puzzle rooms
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (!dungeonGrid[x][z] && isValidForPuzzleRoom(x, z) && isOnEdge(x, z)) {
                    validPositions.add(new int[]{x, z});
                }
            }
        }

        Collections.shuffle(validPositions);

        // Determine number of puzzle rooms based on floor level
        int maxPuzzle = isLowerFloor ? 2 : 5;
        int numPuzzle = Math.min(validPositions.size(), rand.nextInt(maxPuzzle) + 1);

        for (int i = 0; i < numPuzzle && !validPositions.isEmpty(); i++) {
            int[] pos = validPositions.remove(0);
            dungeonGrid[pos[0]][pos[1]] = true;
            roomTypes[pos[0]][pos[1]] = RoomType.PUZZLE;
            puzzleRooms.add(pos);
        }
    }

    private boolean isValidForPuzzleRoom(int x, int z) {
        int entranceDist = Math.abs(x - entranceRoom[0]) + Math.abs(z - entranceRoom[1]);
        int bloodDist = Math.abs(x - bloodRoom[0]) + Math.abs(z - bloodRoom[1]);
        return entranceDist >= 2 && bloodDist >= 2;
    }

    private void fillNormalRooms(boolean isLowerFloor) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (!dungeonGrid[x][z]) {
                    dungeonGrid[x][z] = true;
                    roomTypes[x][z] = RoomType.NORMAL;
                }
            }
        }

        int targetShaped = isLowerFloor ? 2 : rand.nextInt(4) + 2;
        placeShapedRooms(targetShaped);
    }

    private void placeShapedRooms(int targetShaped) {
        List<int[][]> shapes = new ArrayList<>();
        shapes.add(new int[][]{{0,0}, {1,0}, {0,1}}); // L-shape
        shapes.add(new int[][]{{0,0}, {1,0}, {0,1}, {1,1}}); // 2x2 square
        shapes.add(new int[][]{{0,0}, {1,0}}); // Horizontal line
        shapes.add(new int[][]{{0,0}, {0,1}}); // Vertical line

        int shapedCount = 0;
        int attempts = 0;
        final int maxAttempts = 50;

        while (shapedCount < targetShaped && attempts < maxAttempts) {
            attempts++;

            List<int[]> normalAnchors = new ArrayList<>();
            for (int x = 0; x < GRID_WIDTH; x++) {
                for (int z = 0; z < GRID_HEIGHT; z++) {
                    if (roomTypes[x][z] == RoomType.NORMAL && !shapedRooms[x][z]) {
                        normalAnchors.add(new int[]{x, z});
                    }
                }
            }

            if (normalAnchors.isEmpty()) break;

            int[] anchor = normalAnchors.get(rand.nextInt(normalAnchors.size()));
            int[][] shape = shapes.get(rand.nextInt(shapes.size()));

            boolean fits = true;
            List<int[]> shapePositions = new ArrayList<>();
            shapePositions.add(anchor);

            for (int i = 1; i < shape.length; i++) {
                int nx = anchor[0] + shape[i][0];
                int nz = anchor[1] + shape[i][1];

                if (nx < 0 || nx >= GRID_WIDTH || nz < 0 || nz >= GRID_HEIGHT ||
                        roomTypes[nx][nz] != RoomType.NORMAL || shapedRooms[nx][nz]) {
                    fits = false;
                    break;
                }
                shapePositions.add(new int[]{nx, nz});
            }

            if (fits) {
                for (int[] pos : shapePositions) {
                    shapedRooms[pos[0]][pos[1]] = true;
                }
                roomTypes[anchor[0]][anchor[1]] = RoomType.SHAPED_NORMAL;
                shapedCount++;
            }
        }
    }

    private void generateMazeStructure() {
        createGuaranteedPath(entranceRoom, bloodRoom);
        addRandomConnections();
        ensureConnectivity();
    }

    private void addRandomConnections() {
        // Add extra connections to normal rooms
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (isNormalRoom(x, z)) {
                    while (connectionCount[x][z] < 1 ||
                            (connectionCount[x][z] < 2 && rand.nextBoolean())) {
                        addRandomConnection(x, z);
                    }
                }
            }
        }

        // Add some dead ends
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (isNormalRoom(x, z) && connectionCount[x][z] > 1 &&
                        rand.nextInt(100) < DEAD_END_CHANCE) {
                    List<Connection> conns = getConnectionsForRoom(x, z);
                    if (!conns.isEmpty()) {
                        Connection toRemove = conns.get(rand.nextInt(conns.size()));
                        disconnectRooms(toRemove.room1, toRemove.room2);
                    }
                }
            }
        }
    }

    private boolean isNormalRoom(int x, int z) {
        return roomTypes[x][z] == RoomType.NORMAL ||
                roomTypes[x][z] == RoomType.SHAPED_NORMAL;
    }

    private void ensureConnectivity() {
        boolean[][] visited = new boolean[GRID_WIDTH][GRID_HEIGHT];
        floodFill(entranceRoom, visited);

        // Connect any unvisited rooms
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (dungeonGrid[x][z] && !visited[x][z]) {
                    connectToNearestConnectedRoom(new int[]{x, z});
                }
            }
        }
    }

    private void floodFill(int[] start, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (Connection conn : getConnectionsForRoom(current[0], current[1])) {
                int[] neighbor = (Arrays.equals(conn.room1, current)) ? conn.room2 : conn.room1;
                if (!visited[neighbor[0]][neighbor[1]]) {
                    visited[neighbor[0]][neighbor[1]] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    private void connectToNearestConnectedRoom(int[] room) {
        boolean[][] checked = new boolean[GRID_WIDTH][GRID_HEIGHT];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(room);
        checked[room[0]][room[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int[] neighbor = getNeighbor(current, dir);
                if (isValidRoom(neighbor[0], neighbor[1])) {
                    if (isConnected(neighbor)) {
                        connectRooms(current, neighbor, dir);
                        return;
                    }
                    if (!checked[neighbor[0]][neighbor[1]]) {
                        checked[neighbor[0]][neighbor[1]] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    private int[] getNeighbor(int[] room, int direction) {
        switch (direction) {
            case 0: return new int[]{room[0], room[1]-1}; // North
            case 1: return new int[]{room[0]+1, room[1]}; // East
            case 2: return new int[]{room[0], room[1]+1}; // South
            case 3: return new int[]{room[0]-1, room[1]}; // West
            default: return room.clone();
        }
    }

    private int getDirection(int[] room1, int[] room2) {
        if (room1[0] == room2[0]) {
            return room1[1] < room2[1] ? 2 : 0; // South or North
        } else {
            return room1[0] < room2[0] ? 1 : 3; // East or West
        }
    }

    private void addRandomConnection(int x, int z) {
        List<int[]> possibleNeighbors = new ArrayList<>();
        List<Integer> possibleDirections = new ArrayList<>();

        if (x > 0 && canConnect(new int[]{x, z}, new int[]{x-1, z}, 3)) {
            possibleNeighbors.add(new int[]{x-1, z});
            possibleDirections.add(3);
        }
        if (x < GRID_WIDTH-1 && canConnect(new int[]{x, z}, new int[]{x+1, z}, 1)) {
            possibleNeighbors.add(new int[]{x+1, z});
            possibleDirections.add(1);
        }
        if (z > 0 && canConnect(new int[]{x, z}, new int[]{x, z-1}, 0)) {
            possibleNeighbors.add(new int[]{x, z-1});
            possibleDirections.add(0);
        }
        if (z < GRID_HEIGHT-1 && canConnect(new int[]{x, z}, new int[]{x, z+1}, 2)) {
            possibleNeighbors.add(new int[]{x, z+1});
            possibleDirections.add(2);
        }

        if (!possibleNeighbors.isEmpty()) {
            int index = rand.nextInt(possibleNeighbors.size());
            connectRooms(new int[]{x, z}, possibleNeighbors.get(index), possibleDirections.get(index));
        }
    }

    private boolean canConnect(int[] room1, int[] room2, int direction) {
        if (connectionCount[room1[0]][room1[1]] >= MAX_CONNECTIONS ||
                connectionCount[room2[0]][room2[1]] >= MAX_CONNECTIONS) {
            return false;
        }

        // Check if connection already exists
        for (Connection conn : connections) {
            if ((conn.room1[0] == room1[0] && conn.room1[1] == room1[1] &&
                    conn.room2[0] == room2[0] && conn.room2[1] == room2[1]) ||
                    (conn.room1[0] == room2[0] && conn.room1[1] == room2[1] &&
                            conn.room2[0] == room1[0] && conn.room2[1] == room1[1])) {
                return false;
            }
        }

        return true;
    }

    private void ensureSingleConnection(int[] room) {
        int x = room[0];
        int z = room[1];

        while (connectionCount[x][z] > 1) {
            List<Connection> roomConnections = getConnectionsForRoom(x, z);
            if (!roomConnections.isEmpty()) {
                Connection toRemove = roomConnections.get(rand.nextInt(roomConnections.size()));
                disconnectRooms(toRemove.room1, toRemove.room2);
            }
        }

        if (connectionCount[x][z] == 0) {
            addRandomConnection(x, z);
        }
    }

    private List<Connection> getConnectionsForRoom(int x, int z) {
        List<Connection> result = new ArrayList<>();
        for (Connection conn : connections) {
            if ((conn.room1[0] == x && conn.room1[1] == z) ||
                    (conn.room2[0] == x && conn.room2[1] == z)) {
                result.add(conn);
            }
        }
        return result;
    }

    private void connectRooms(int[] room1, int[] room2, int direction) {
        connections.add(new Connection(room1, room2, direction));
        connectionCount[room1[0]][room1[1]]++;
        connectionCount[room2[0]][room2[1]]++;
    }

    private void disconnectRooms(int[] room1, int[] room2) {
        Iterator<Connection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            Connection conn = iterator.next();
            if ((conn.room1[0] == room1[0] && conn.room1[1] == room1[1] &&
                    conn.room2[0] == room2[0] && conn.room2[1] == room2[1]) ||
                    (conn.room1[0] == room2[0] && conn.room1[1] == room2[1] &&
                            conn.room2[0] == room1[0] && conn.room2[1] == room1[1])) {
                iterator.remove();
                connectionCount[room1[0]][room1[1]]--;
                connectionCount[room2[0]][room2[1]]--;
                break;
            }
        }
    }

    private boolean isSpecialRoom(int x, int z) {
        return (x == entranceRoom[0] && z == entranceRoom[1]) ||
                (x == bloodRoom[0] && z == bloodRoom[1]) ||
                (x == fairyRoom[0] && z == fairyRoom[1]) ||
                puzzleRooms.stream().anyMatch(pos -> pos[0] == x && pos[1] == z);
    }

    private boolean isOnEdge(int x, int z) {
        return x == 0 || x == GRID_WIDTH-1 || z == 0 || z == GRID_HEIGHT-1;
    }

    private boolean isValidRoom(int x, int z) {
        return x >= 0 && x < GRID_WIDTH && z >= 0 && z < GRID_HEIGHT && dungeonGrid[x][z];
    }

    private boolean isConnected(int[] room) {
        return connectionCount[room[0]][room[1]] > 0;
    }

    private boolean validateDungeonAccessibility() {
        boolean[][] visited = new boolean[GRID_WIDTH][GRID_HEIGHT];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(entranceRoom);
        visited[entranceRoom[0]][entranceRoom[1]] = true;

        int accessibleRooms = 1;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (Connection conn : getConnectionsForRoom(current[0], current[1])) {
                int[] neighbor = (Arrays.equals(conn.room1, current)) ? conn.room2 : conn.room1;
                if (!visited[neighbor[0]][neighbor[1]]) {
                    visited[neighbor[0]][neighbor[1]] = true;
                    queue.add(neighbor);
                    accessibleRooms++;
                }
            }
        }

        return accessibleRooms == countPlacedRooms();
    }

    private int countPlacedRooms() {
        int count = 0;
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (dungeonGrid[x][z]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void placeDungeonInWorld() {
        boolean largeRoom60x60Placed = false;
        boolean largeRoom60x30Placed = false;

        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int z = 0; z < GRID_HEIGHT; z++) {
                if (!dungeonGrid[x][z] || roomOccupied[x][z]) continue;

                int roomWidth = ROOM_SIZE_SMALL;
                int roomLength = ROOM_SIZE_SMALL;

                if (!largeRoom60x60Placed && rand.nextInt(100) < LARGE_ROOM_CHANCE &&
                        !isSpecialRoom(x, z) && !isOnEdge(x, z)) {
                    roomWidth = ROOM_SIZE_LARGE_WIDTH;
                    roomLength = ROOM_SIZE_LARGE_LENGTH;
                    largeRoom60x60Placed = true;
                    markGridAsOccupied(x, z, 2, 2);
                } else if (!largeRoom60x30Placed && rand.nextInt(100) < LARGE_ROOM_CHANCE &&
                        !isSpecialRoom(x, z) && !isOnEdge(x, z)) {
                    roomWidth = ROOM_SIZE_LARGE_WIDTH_60x30;
                    roomLength = ROOM_SIZE_LARGE_LENGTH_60x30;
                    largeRoom60x30Placed = true;
                    markGridAsOccupied(x, z, 2, 1);
                }

                int worldX = x * (ROOM_SIZE_SMALL + GAP);
                int worldZ = z * (ROOM_SIZE_SMALL + GAP);

                Material floorMaterial = roomTypes[x][z].getFloorMaterial();
                buildRoom(worldX, startY, worldZ, roomWidth, roomLength, floorMaterial);
                buildConnections(worldX, worldZ, roomWidth, roomLength, x, z);
            }
        }
    }

    private void markGridAsOccupied(int x, int z, int widthInCells, int lengthInCells) {
        for (int dx = 0; dx < widthInCells; dx++) {
            for (int dz = 0; dz < lengthInCells; dz++) {
                if (x + dx < GRID_WIDTH && z + dz < GRID_HEIGHT) {
                    roomOccupied[x + dx][z + dz] = true;
                }
            }
        }
    }

    private void buildRoom(int x, int y, int z, int width, int length, Material floorMaterial) {
        if (world == null) {
            world = Bukkit.getWorld("dungeon");
            if (world == null) return;
        }

        for (int dx = 0; dx < width; dx++) {
            for (int dz = 0; dz < length; dz++) {
                world.getBlockAt(x + dx, y, z + dz).setType(floorMaterial);
            }
        }
    }

    private void buildConnections(int worldX, int worldZ, int roomWidth, int roomLength, int gridX, int gridZ) {
        if (roomWidth > ROOM_SIZE_SMALL || roomLength > ROOM_SIZE_SMALL) {
            return;
        }

        for (Connection conn : connections) {
            if ((conn.room1[0] == gridX && conn.room1[1] == gridZ) ||
                    (conn.room2[0] == gridX && conn.room2[1] == gridZ)) {

                int[] otherRoom = (conn.room1[0] == gridX && conn.room1[1] == gridZ) ?
                        conn.room2 : conn.room1;
                int direction = (conn.room1[0] == gridX && conn.room1[1] == gridZ) ?
                        conn.direction : (conn.direction + 2) % 4;

                int neighborWidth = ROOM_SIZE_SMALL;
                int neighborLength = ROOM_SIZE_SMALL;
                if (roomOccupied[otherRoom[0]][otherRoom[1]]) {
                    for (int dx = 0; dx < GRID_WIDTH; dx++) {
                        for (int dz = 0; dz < GRID_HEIGHT; dz++) {
                            if (roomOccupied[dx][dz] && dx >= otherRoom[0] && dz >= otherRoom[1]) {
                                neighborWidth = (dx - otherRoom[0] + 1) * (ROOM_SIZE_SMALL + GAP) - GAP;
                                neighborLength = (dz - otherRoom[1] + 1) * (ROOM_SIZE_SMALL + GAP) - GAP;
                                break;
                            }
                        }
                    }
                }

                switch (direction) {
                    case 0: // North
                        buildNorthConnection(worldX, worldZ, roomWidth, neighborLength);
                        break;
                    case 1: // East
                        buildEastConnection(worldX, worldZ, roomLength, neighborWidth);
                        break;
                    case 2: // South
                        buildSouthConnection(worldX, worldZ, roomWidth, neighborLength);
                        break;
                    case 3: // West
                        buildWestConnection(worldX, worldZ, roomLength, neighborWidth);
                        break;
                }
            }
        }
    }

    private void buildNorthConnection(int roomX, int roomZ, int roomWidth, int neighborLength) {
        int connectionCenterX = roomX + roomWidth / 2;
        int connectionCenterZ = roomZ;
        int connectionWidth = Math.min(roomWidth, neighborLength);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -GAP; dz < 0; dz++) {
                int blockX = connectionCenterX + dx;
                int blockZ = connectionCenterZ + dz;
                if (isInWorldBounds(blockX, blockZ)) {
                    world.getBlockAt(blockX, startY, blockZ).setType(Material.STONE);
                }
            }
        }
    }

    private void buildEastConnection(int roomX, int roomZ, int roomLength, int neighborWidth) {
        int connectionCenterX = roomX + ROOM_SIZE_SMALL;
        int connectionCenterZ = roomZ + roomLength / 2;
        int connectionLength = Math.min(roomLength, neighborWidth);

        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = 0; dx < GAP; dx++) {
                int blockX = connectionCenterX + dx;
                int blockZ = connectionCenterZ + dz;
                if (isInWorldBounds(blockX, blockZ)) {
                    world.getBlockAt(blockX, startY, blockZ).setType(Material.STONE);
                }
            }
        }
    }

    private void buildSouthConnection(int roomX, int roomZ, int roomWidth, int neighborLength) {
        int connectionCenterX = roomX + roomWidth / 2;
        int connectionCenterZ = roomZ + ROOM_SIZE_SMALL;
        int connectionWidth = Math.min(roomWidth, neighborLength);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = 0; dz < GAP; dz++) {
                int blockX = connectionCenterX + dx;
                int blockZ = connectionCenterZ + dz;
                if (isInWorldBounds(blockX, blockZ)) {
                    world.getBlockAt(blockX, startY, blockZ).setType(Material.STONE);
                }
            }
        }
    }

    private void buildWestConnection(int roomX, int roomZ, int roomLength, int neighborWidth) {
        int connectionCenterX = roomX;
        int connectionCenterZ = roomZ + roomLength / 2;
        int connectionLength = Math.min(roomLength, neighborWidth);

        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = -GAP; dx < 0; dx++) {
                int blockX = connectionCenterX + dx;
                int blockZ = connectionCenterZ + dz;
                if (isInWorldBounds(blockX, blockZ)) {
                    world.getBlockAt(blockX, startY, blockZ).setType(Material.STONE);
                }
            }
        }
    }

    private boolean isInWorldBounds(int x, int z) {
        return x >= 0 && z >= 0 &&
                x < GRID_WIDTH * (ROOM_SIZE_SMALL + GAP) &&
                z < GRID_HEIGHT * (ROOM_SIZE_SMALL + GAP);
    }
}