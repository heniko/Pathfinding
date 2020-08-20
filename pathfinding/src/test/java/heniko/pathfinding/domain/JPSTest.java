package heniko.pathfinding.domain;

import heniko.pathfinding.util.Node;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for jump point search.
 *
 * @author Niko Hernesniemi
 */
public class JPSTest {

    @Test
    public void smallStraightPathTest() {
        int sizeX = 6;
        int sizeY = 6;
        boolean[][] isWall = new boolean[sizeX][sizeY];
        Node start = new Node(0, 0);
        Node end = new Node(5, 5);
        double wanted = 7.07106;

        JPS jps = new JPS(start, end, sizeX, sizeY, isWall, new List<>(), new EuclideanHeuristic());
        jps.solve();
        assertTrue(Math.abs(wanted - jps.getPathLength()) < 0.01);
    }

    @Test
    public void smallGraphWithObstacles() {
        int sizeX = 6;
        int sizeY = 6;
        boolean[][] isWall = new boolean[sizeX][sizeY];
        for (int y = 0; y < 5; y++) {
            isWall[3][y] = true;
        }
        Node start = new Node(0, 0);
        Node end = new Node(5, 0);
        double wanted = 13.2426;

        JPS jps = new JPS(start, end, sizeX, sizeY, isWall, new List<>(), new EuclideanHeuristic());
        jps.solve();
        assertTrue(Math.abs(wanted - jps.getPathLength()) < 0.01);
    }
}
