package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for Djikstra heuristic
 *
 * @author Niko Hernesniemi
 */
public class DjikstraHeuristicTest {

    @Test
    public void returnsZero() {
        Heuristic h = new DjikstraHeuristic();
        assertTrue(Math.abs(0 - h.getHValue(0, 32, 1321, 13134)) < 0.01);
    }
}
