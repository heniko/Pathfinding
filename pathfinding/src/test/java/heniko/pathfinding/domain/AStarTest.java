package heniko.pathfinding.domain;

import heniko.pathfinding.util.ColouredNode;
import heniko.pathfinding.util.Node;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for A*
 *
 * @author Niko Hernesniemi
 */
public class AStarTest {

    @Test
    public void smallStraightPathTest() {
        int sizeX = 6;
        int sizeY = 6;
        boolean[][] isWall = new boolean[sizeX][sizeY];
        Node start = new Node(0, 0);
        Node end = new Node(5, 5);
        double wanted = 7.07106;

        AStar djikstra = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new DjikstraHeuristic());
        djikstra.solve();
        assertTrue(Math.abs(wanted - djikstra.getPathLength()) < 0.01);

        AStar diag = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new DiagonalHeuristic());
        diag.solve();
        assertTrue(Math.abs(wanted - diag.getPathLength()) < 0.01);

        AStar euc = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new EuclideanHeuristic());
        euc.solve();
        assertTrue(Math.abs(wanted - euc.getPathLength()) < 0.01);
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

        AStar djikstra = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new DjikstraHeuristic());
        djikstra.solve();
        assertTrue(Math.abs(wanted - djikstra.getPathLength()) < 0.01);

        AStar diag = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new DiagonalHeuristic());
        diag.solve();
        assertTrue(Math.abs(wanted - diag.getPathLength()) < 0.01);

        AStar euc = new AStar(start, end, sizeX, sizeY, isWall, new LinkedList<ColouredNode>(), new EuclideanHeuristic());
        euc.solve();
        assertTrue(Math.abs(wanted - euc.getPathLength()) < 0.01);
    }
}
