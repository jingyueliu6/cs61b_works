import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void TestEqualChars() {
        char a = 'a';
        char b = 'b';
        char c = 'c';
        assertFalse(offByOne.equalChars(a, 'b'));
        assertTrue(offByOne.equalChars(c, 'c'));

    }
}
