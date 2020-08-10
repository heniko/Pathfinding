package heniko.pathfinding.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for Euclidean heuristic
 *
 * @author Niko Hernesniemi
 */
public class EuclideanHeuristicTest {

    @Test
    public void givesCorrectLength() {
        Heuristic h = new EuclideanHeuristic();
        double res = h.getHValue(0, 2, 6, 16);
        double wanted = 15.231546;
        assertTrue(Math.abs(res - wanted) < 0.01);
    }

}
