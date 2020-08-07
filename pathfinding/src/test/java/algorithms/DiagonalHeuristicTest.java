package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for Diagonal distance heuristic
 *
 * @author Niko Hernesniemi
 */
public class DiagonalHeuristicTest {

    @Test
    public void givesCorrectLength() {
        Heuristic h = new DiagonalHeuristic();
        double res = h.getHValue(0, 2, 6, 16);
        double wanted = 16.48528;
        assertTrue(Math.abs(res - wanted) < 0.01);
    }
}
