package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {
    private int startRoomX;
    private int startRoomY;
    private int endRoomX;
    private int endRoomY;

    private TETile[][] world;

    public Hallway(Room startRoom, Room endRoom1, TETile[][] world) {
        startRoomX = (int) startRoom.getCenterX();
        startRoomY = (int) startRoom.getCenterY();
        endRoomX = (int) endRoom1.getCenterX();
        endRoomY = (int) endRoom1.getCenterY();
        this.world = world;
    }

    public void renderHallway() {
        /* from start to room1  go from x direction */
        int startX;
        int endX;
        int pY;
        if (startRoomX <= endRoomX) {
            startX = startRoomX;
            endX = endRoomX;
        }
        else {
            startX = endRoomX;
            endX =  startRoomX;
        }

        for (int x = startX; x <= endX; x+= 1) {
            world[x][startRoomY] = Tileset.FLOOR;
        }

        int startY;
        int endY;
        if (startRoomY <= endRoomY) {
            startY = startRoomY;
            endY = endRoomY;
        }
        else {
            startY = endRoomY;
            endY =  startRoomY;
        }

        for (int y = startY; y <= endY; y += 1) {
            world[endRoomX][y] = Tileset.FLOOR;
        }
    }
}
