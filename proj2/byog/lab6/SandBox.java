package byog.lab6;
import java.util.Random;

public class SandBox {
    public static void main(String[] args) {
        char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random rand = new Random(1);
        String s = "";
        while(s.length() < 8) {
            s += CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        System.out.println(s);
    }
}
