import org.junit.Test;
import org.junit.Assert;

public class TestOffByN {
    OffByN offBy5 = new OffByN(5);

    @Test
    public void testEqualChars() {
        Assert.assertTrue(offBy5.equalChars('a', 'f'));
        Assert.assertTrue(offBy5.equalChars('f', 'a'));
        Assert.assertFalse(offBy5.equalChars('f', 'h'));

    }
}
