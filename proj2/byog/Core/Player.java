package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Player {
    public int positionX;
    public int positionY;
    public int life;
    public Room room;
    public TETile[][] world;

    public Player(Random random, int life, List<Room> rooms, TETile[][] world) {
        this.life = life;
        int roomNum = rooms.size();
        int n = RandomUtils.uniform(random, roomNum);
        room = rooms.get(n);
        positionX = (int) room.getCenterX();
        positionY = (int) room.getCenterY();
        this.world = world;
    }

    public void renderPlayer(){
        world[positionX][positionY] = Tileset.PLAYER;
    }

    private boolean positionQualify(int x, int y) {
        if(x <= 0 || x >= world.length || y <= 0 || y >= world[0].length) {
            return false;
        }
        if (world[x][y] == Tileset.WALL) {
            return false;
        }
        return true;
    }

    public void move(int x, int y) {
        int newX = positionX + x;
        int newY = positionY + y;
        if (positionQualify(newX, newY)) {
            world[positionX][positionY] = Tileset.FLOOR;
            positionX = newX;
            positionY = newY;
            renderPlayer();
        }
    }
}
