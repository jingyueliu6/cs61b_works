package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;
import java.util.Random;

public class Room {
    private int leftConnerX;
    private int leftConnerY;
    private int width;
    private int height;
    private double centerX;
    private double centerY;
    private static TETile[][] world;
    private static final int MAX_WIDTH = 3;
    private static final int MAX_HEIGHT = 2;

    public Room(Random random, TETile[][] world) {
        this.world = world;
        width = RandomUtils.uniform(random, MAX_WIDTH) + 3;
        height = RandomUtils.uniform(random, MAX_HEIGHT) + 2;
        leftConnerX = RandomUtils.uniform(random, world.length - width - 1) + 1;
        leftConnerY = RandomUtils.uniform(random, world[0].length - height - 1) + 1;
        centerX = ((double) leftConnerX) + (((double) width) / 2.0);
        centerY = ((double) leftConnerY) + (((double) height) / 2.0);
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getCenterY() {
        return this.centerY;
    }

    public double getWidth() {
        return (double) this.width;
    }

    public double getHeight() {
        return (double) this.height;
    }

    public String relationship(Room otherRoom) {
        double dirX = this.centerX - otherRoom.getCenterX();
        double dirY = this.centerY - otherRoom.getCenterY();
        if (Math.abs(dirX) > Math.abs(dirY)) {
            if (dirX <= 0) {
                return "left";
            }
            else {
                return "right";
            }
        }
        else {
            if(dirY <= 0) {
                return "down";
            }
            else {
                return "up";
            }
        }
    }

    public void renderRoom() {
        for (int x = leftConnerX; x <= leftConnerX + width; x += 1) {
            for (int y = leftConnerY; y <= leftConnerY + height; y += 1) {
                    world[x][y] = Tileset.FLOOR;
            }
        }
    }

    private static boolean checkCollision(Room room1, Room room2) {
        double distanceX = Math.abs(room1.getCenterX() - room2.getCenterX());
        double distanceY = Math.abs(room1.getCenterY() - room2.getCenterY());
        double minWidth = room1.getWidth() / 2.0 + room2.getWidth() / 2.0;
        double minHeight = room1.getHeight() / 2.0 + room2.getHeight() / 2.0;
        if (distanceX < minWidth || distanceY < minHeight) {
            return true;
        }
        return false;
    }

    private static boolean collisionChecking(Room room, List<Room> rooms) {
        if (rooms.isEmpty()) {
            return false;
        }
        for (Room r : rooms) {
            if(checkCollision(room, r)) {
                return true;
            }
        }
        return false;
    }

    public static List<Room> generateMultiRooms (Random random, TETile[][] world, int n) {
        List<Room> rooms = new ArrayList<>();
        int i = 0;
        while(i < n) {
            Room room = new Room(random, world);
            if (!collisionChecking(room, rooms)) {
                rooms.add(room);
                room.renderRoom();
            }
            i += 1;
        }
//        System.out.println("success in generating " + n +  " rooms");
        return rooms;
    }

    public static void connectRooms (Random random, List<Room> rooms) {
        Room[] roomNum = new Room[rooms.size()];
        int i = 0;
        for (Room r : rooms) {
            roomNum[i] = r;
            i += 1;
        }
        /* ensure every room is connected */
        for (int j = 0; j < rooms.size()-1; j += 1) {
            Hallway hallway = new Hallway(roomNum[j], roomNum[j+1], world);
            hallway.renderHallway();
        }
        Hallway hallwayRound = new Hallway(roomNum[0], roomNum[rooms.size()-1], world);
        hallwayRound.renderHallway();

        /* for one random hallway */
        int r1 = RandomUtils.uniform(random, rooms.size() / 2);
        int r2 = RandomUtils.uniform(random, rooms.size() / 2) + rooms.size() / 2 - 1;
        Hallway hallwayRandom = new Hallway(roomNum[r1], roomNum[r2], world);
        hallwayRandom.renderHallway();
    }

    public static void buildWall(TETile[][] world) {
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        for(int i = 1; i < worldWidth-1; i += 1) {
            for (int j = 1; j < worldHeight-1; j += 1) {
                if(world[i][j] == Tileset.FLOOR) {
                    int left = i-1;
                    int right = i+1;
                    int up = j+1;
                    int down = j-1;
                    if (world[left][j] != Tileset.FLOOR) {
                        world[left][j] = Tileset.WALL;
                    }
                    if (world[left][up] != Tileset.FLOOR) {
                        world[left][up] = Tileset.WALL;
                    }
                    if (world[left][down] != Tileset.FLOOR) {
                        world[left][down] = Tileset.WALL;
                    }
                    if (world[i][up] != Tileset.FLOOR) {
                        world[i][up] = Tileset.WALL;
                    }
                    if (world[i][down] != Tileset.FLOOR) {
                        world[i][down] = Tileset.WALL;
                    }
                    if (world[right][j] != Tileset.FLOOR) {
                        world[right][j] = Tileset.WALL;
                    }
                    if (world[right][up] != Tileset.FLOOR) {
                        world[right][up] = Tileset.WALL;
                    }
                    if (world[right][down] != Tileset.FLOOR) {
                        world[right][down] = Tileset.WALL;
                    }
                }
            }
        }
    }

}
