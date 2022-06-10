package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final Set<Character> moveDir = new HashSet<>() {{add('w'); add('a'); add('s'); add('d');}};
    public  Player player;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    private void drawStartUI() {
        StdDraw.setCanvasSize(WIDTH*16, (HEIGHT+1) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT+1);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        Font font = new Font("Monaco", Font.PLAIN, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B: THE GAME");

        Font smallFont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Quit (Q)");

        StdDraw.show();
    }

    private void drawFrame() {
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StringBuilder health = new StringBuilder();
        for (int i = 0; i < player.life; i++) {
            health.append('*');
        }
        StdDraw.textLeft(1, HEIGHT-1, "Health: " + health.toString());
        StdDraw.show();
    }

    private char getFirstChar() {
        char c;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 'n' || c == 'l' || c == 'q') {
                break;
            }
        }
        return c;
    }


    public TETile[][] playWithKeyboard() {
        drawStartUI();
        TETile[][] finalWorldFrame = null;
        char firstChar = getFirstChar();
        switch (firstChar) {
            case 'n': return newGame();
            case 'l': return loadGame();
            case 'q': System.exit(0);
        }
        return finalWorldFrame;
    }

    private TETile[][] newGame() {
        Random random = new Random();
        long seed = (long) RandomUtils.uniform(random, 1000000);
        ter.initialize(WIDTH, HEIGHT + 1);
        TETile[][] world = generateWorld(seed);
        ter.renderFrame(world);
        return world;
    }

    private TETile[][] loadGame() {
        TETile[][] finalWorldFrame = getSavedGame();
        return finalWorldFrame;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        input = input.toLowerCase();
        char firstChar = input.charAt(0);
        switch (firstChar) {
            case 'n': return newGame(input);
            case 'l': return loadGame(input);
            case 'q': System.exit(0);
        }
        return finalWorldFrame;
    }

    /* make a method to generate a new game */
    private long getSeed(String input) {
        int index = 1;
        StringBuffer seedStr = new StringBuffer();
        for (; index<input.length(); index++) {
            if (moveDir.contains(input.charAt(index))) {
                break;
            }
            else {
                seedStr.insert(seedStr.length(), input.charAt(index));
            }
        }
        long seed =  Long.parseLong(seedStr.toString());
        return seed;
    }

    private TETile[][] newGame(String input) {
        long seed = getSeed(input);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = generateWorld(seed);
        ter.renderFrame(world);
        return world;
    }

    private TETile[][] getSavedGame() {
        TETile[][] finalWorldFrame = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("savefile.txt"));
            finalWorldFrame = (TETile[][]) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalWorldFrame;
    }

    private void saveGame(TETile[][] finalWorldFrame, Player player) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.txt"));
            out.writeObject(finalWorldFrame);
            out.writeObject(player);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TETile[][] loadGame(String input) {
        TETile[][] finalWorldFrame = getSavedGame();
        return finalWorldFrame;
    }

    private TETile[][] generateWorld(long seed) {
        Random random = new Random(seed);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int maxRoomNumber = RandomUtils.uniform(random, 10) + 15;
        List<Room> rooms = Room.generateMultiRooms(random, world, maxRoomNumber);
        Room.connectRooms(random, rooms);
        Room.buildWall(world);
        player = new Player(random, 5, rooms, world);
        player.renderPlayer();
        boolean gameOver = play(world);
        if (gameOver) {
            saveGame(world, player);
        }
        return world;
    }

    private boolean flashStep(char c) {
        switch (c) {
            case 'w': {
                player.move(0, 1);
                return false;
            }
            case 'a': {
                player.move(-1, 0);
                return false;
            }
            case 's': {
                player.move(0, -1);
                return false;
            }
            case 'd': {
                player.move(1, 0);
                return false;
            }
            case 'q': return true;
            }
        return false;
    }

    private  char getMoveStep()  {
        char c;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
                }
            c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if(c == 'q') {
                   return c;
                }
            if (c == 'w' || c == 'a' || c == 's' || c =='d') {
                break;
            }
        }
        return c;

    }

    private boolean play(TETile[][] world) {
        boolean gameOver = false;
        ter.renderFrame(world);
        while(!gameOver) {
            StdDraw.pause(200);
            char c = getMoveStep();
            gameOver = flashStep(c);
            drawFrame();
            ter.renderFrame(world);
            StdDraw.pause(200);
        }
        return gameOver;
    }

    public static void main(String[] args) {
////        test for getSeed method
//        String i = "n12332wda";
//        long seed = getSeed(i);
//        System.out.println(seed);

        Game game = new Game();
        game.playWithInputString("n1653wasdw");
    }
}
