package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;
import java.util.Map;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static void drawAHexagonal(TETile[][] tiles, int from_w, int from_h, int size, TETile pattern) {
        for (int height = 0; height < size; height++) {
            for (int width = size - 1 - height; width <  2 * size + height - 1; width++) {
                tiles[width + from_w][height + from_h] = pattern;
                tiles[width + from_w][2 * size - height - 1 + from_h] = pattern;
            }
        }
    }

    private static HashMap<Integer, int[]> overallShape(int size) {
        HashMap<Integer, int[]> index = new HashMap<>();
        int k = 0;
        for (int num = 0; num < 3; num++) {
            int wid = 12 - num * 5;
            int height = num * 3;
            for (int i = 0; i < num+1; i++){
                int h = 1;
                if(num == 1) {
                    h = 4;
                } else {
                    h = 3;
                }
                for (int j = 0; j < h; j++)
                {
                    int[] w_h = new int[]{wid + (size*3+1)*i, height + j * 2 * size};
                    index.put(k, w_h);
                    k ++;
                }
            }
        }
        int[] w_h = new int[]{12, 24};
        index.put(20, w_h);
        return index;
    }

    private static TETile patternTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.SAND;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawMultiHexagonal(TETile[][] tiles, int size) {
        Map<Integer, int[]> index = overallShape(size);
        for (Map.Entry<Integer, int[]> entry : index.entrySet()) {
            int from_w = entry.getValue()[0];
            int from_h = entry.getValue()[1];
            drawAHexagonal(tiles, from_w, from_h, size, patternTile());
        }

    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexagonTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexagonTiles[x][y] = Tileset.NOTHING;
            }
        }

        drawMultiHexagonal(hexagonTiles, 3);
        ter.renderFrame(hexagonTiles);
    }


}
