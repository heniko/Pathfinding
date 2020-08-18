package heniko.pathfinding.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Niko Hernesniemi
 */
public class MathematicsTest {

    @Test
    public void maxTest() {
        assertEquals(1, Mathematics.max(1, 0));
        assertEquals(1, Mathematics.max(0, 1));
        assertEquals(1.2, Mathematics.max(1.2, 0), 0.000001);
        assertEquals(2332.3, Mathematics.max(78, 2332.3), 0.000001);
    }

    @Test
    public void minTest() {
        assertEquals(0, Mathematics.min(1, 0));
        assertEquals(0, Mathematics.min(0, 1));
        assertEquals(0, Mathematics.min(1.2, 0), 0.000001);
        assertEquals(78, Mathematics.min(78, 2332.3), 0.000001);
    }

    @Test
    public void absTest() {
        assertEquals(1, Mathematics.abs(1));
        assertEquals(1, Mathematics.abs(-1));
        assertEquals(1.62, Mathematics.abs(-1.62), 0.000001);
        assertEquals(1.312, Mathematics.abs(1.312), 0.00001);
    }

    @Test
    public void sqrtTest() {
        assertEquals(2, Mathematics.sqrt(4), 0.0001);
        assertEquals(1.414213, Mathematics.sqrt(2), 0.0001);
        assertEquals(0.774596, Mathematics.sqrt(0.6), 0.0001);
    }
}
