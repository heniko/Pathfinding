package heniko.pathfinding.controller;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit tests for Graph class
 *
 * @author Niko Hernesniemi
 */
public class GraphTest {

    private int sizeX;
    private int sizeY;
    private int[][] guiState;
    private Graph g;

    @Before
    public void beforeTest() {
        sizeX = 4;
        sizeY = 6;
        guiState = new int[sizeX][sizeY];
        g = new Graph(guiState, sizeX, sizeY);
    }

    @Test
    public void constructortest() {
        // Start positions are correct
        assertEquals(g.getStartX(), 0);
        assertEquals(g.getStartY(), 0);
        // End positions are correct
        assertEquals(g.getEndX(), sizeX - 1);
        assertEquals(g.getEndY(), sizeY - 1);
        // UI shows correct places for start and end
        assertEquals(guiState[0][0], 1);
        assertEquals(guiState[sizeX - 1][sizeY - 1], 2);
        // Height and width are correct
        assertEquals(g.getSizeX(), sizeX);
        assertEquals(g.getSizeY(), sizeY);
    }

    @Test
    public void setStartTest() {
        // Start position changes
        g.changeNode(1, 2, 1);
        assertEquals(g.getStartX(), 1);
        assertEquals(g.getStartY(), 2);
        // Changes are also made to guiState
        assertEquals(guiState[1][2], 1);
        assertEquals(guiState[0][0], 0);
        // Trying to place start over end results in start not moving
        g.changeNode(g.getEndX(), g.getEndY(), 1);
        assertEquals(g.getStartX(), 1);
        assertEquals(g.getStartY(), 2);
        assertEquals(guiState[1][2], 1);
        // Also end should not move
        assertEquals(guiState[g.getEndX()][g.getEndY()], 2);
        // Walls are removed under new start
        g.changeNode(2, 2, 3);
        g.changeNode(2, 2, 1);
        boolean[][] isWall = g.getIsWall();
        assertEquals(isWall[2][2], false);
    }

    @Test
    public void setEndTest() {
        // End position changes
        g.changeNode(1, 2, 2);
        assertEquals(g.getEndX(), 1);
        assertEquals(g.getEndY(), 2);
        // Changes are also made to guiState
        assertEquals(guiState[1][2], 2);
        assertEquals(guiState[sizeX - 1][sizeY - 1], 0);
        // Can't place end over start
        g.changeNode(g.getStartX(), g.getStartY(), 2);
        assertEquals(g.getEndX(), 1);
        assertEquals(g.getEndY(), 2);
        assertEquals(guiState[1][2], 2);
        assertEquals(guiState[0][0], 1);
        // Walls are removed under new end
        g.changeNode(3, 3, 3);
        g.changeNode(3, 3, 2);
        boolean[][] isWall = g.getIsWall();
        assertEquals(isWall[3][3], false);
    }

    @Test
    public void setWallTest() {
        // Walls can be placed
        boolean[][] isWall = g.getIsWall();
        assertEquals(isWall[3][3], false);
        g.changeNode(3, 3, 3);
        assertEquals(isWall[3][3], true);
        // Changes are also made to guiState
        assertEquals(guiState[3][3], 3);
        // Walls can't be placed over start or end
        g.changeNode(g.getStartX(), g.getStartY(), 3);
        assertEquals(isWall[g.getStartX()][g.getStartY()], false);
        assertEquals(guiState[g.getStartX()][g.getStartY()], 1);
        g.changeNode(g.getEndX(), g.getEndY(), 3);
        assertEquals(isWall[g.getEndX()][g.getEndY()], false);
        assertEquals(guiState[g.getEndX()][g.getEndY()], 2);
    }

    @Test
    public void setEmptyTest() {
        // Changing wall to empty works
        boolean[][] isWall = g.getIsWall();
        g.changeNode(3, 3, 3);
        g.changeNode(3, 3, 0);
        assertEquals(isWall[3][3], false);
        // Changes are also made to guiState
        assertEquals(guiState[3][3], 0);
        // Can't place empty over start or end (or at least it doesn't mess up guiState)
        g.changeNode(g.getStartX(), g.getStartY(), 0);
        assertEquals(guiState[g.getStartX()][g.getStartY()], 1);
        g.changeNode(g.getEndX(), g.getEndY(), 0);
        assertEquals(guiState[g.getEndX()][g.getEndY()], 2);
    }
}
