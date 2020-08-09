package graph;

import algorithms.AStar;
import algorithms.DiagonalHeuristic;
import algorithms.DjikstraHeuristic;
import algorithms.EuclideanHeuristic;
import algorithms.JPS;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Handles changes to graph.
 *
 * @author Niko Hernesniemi
 */
public final class Graph {

    private int startX, startY, endX, endY, sizeX, sizeY;
    private int[][] guiState;
    private boolean[][] isWall;
    private LinkedList<ColouredNode> changes;
    private ArrayList<Node> path;

    /**
     * Constructor for Graph.
     *
     * @param guiState
     * @param sizeX Width of the graph
     * @param sizeY Height of the graph
     */
    public Graph(int[][] guiState, int sizeX, int sizeY) {
        this.startX = 0;
        this.startY = 0;
        this.endX = sizeX - 1;
        this.endY = sizeY - 1;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.guiState = guiState;
        this.isWall = new boolean[sizeX][sizeY];
        changeNode(this.startX, this.startY, 1);
        changeNode(this.endX, this.endY, 2);
        this.changes = new LinkedList<>();
        this.path = new ArrayList<>();
    }

    /**
     * Gets isWall array.
     *
     * @return 2d array that contains information about walls
     */
    public boolean[][] getIsWall() {
        return isWall;
    }

    /**
     * Gets width of the graph.
     *
     * @return width of the graph
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Gets height of the graph.
     *
     * @return height of the graph
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Gets end x-coordinate.
     *
     * @return end x-coordinate
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Gets end y-coordinate.
     *
     * @return end y-coordinate
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Gets start x-coordinate.
     *
     * @return start x-coordinate
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Gets start y-coordinate.
     *
     * @return start y-coordinate
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Changes node type in given coordinates if change is valid.
     *
     * @param x x-coordinate of change
     * @param y y-coordinate of change
     * @param type new type of node. 1 = start, 2 = end, 3 = wall and by default
     * other values will change node to empty
     */
    public void changeNode(int x, int y, int type) {
        /*
        0 = Empty
        1 = Start
        2 = End
        3 = Wall
         */
        switch (type) {
            case 1:
                setStart(x, y);
                break;
            case 2:
                setEnd(x, y);
                break;
            case 3:
                setWall(x, y);
                break;
            default:
                setEmpty(x, y);
        }
    }

    private void setStart(int x, int y) {
        if (!isEnd(x, y)) {
            // Change old start to empty in guiState
            guiState[startX][startY] = 0;
            // Change isWall of new start to false since wall can't be over start
            isWall[x][y] = false;
            // Change start position
            startX = x;
            startY = y;
            // Change start position in guiState
            guiState[x][y] = 1;
        }
    }

    private void setEnd(int x, int y) {
        if (!isStart(x, y)) {
            guiState[endX][endY] = 0;
            isWall[x][y] = false;
            endX = x;
            endY = y;
            guiState[x][y] = 2;
        }
    }

    private void setWall(int x, int y) {
        // Check that wall is not set over start or end
        if (!isStart(x, y) && !isEnd(x, y)) {
            isWall[x][y] = true;
            guiState[x][y] = 3;
        }
    }

    private void setEmpty(int x, int y) {
        // Check that empty spot is not set over start or end
        if (!isStart(x, y) && !isEnd(x, y)) {
            isWall[x][y] = false;
            guiState[x][y] = 0;
        }
    }

    private boolean isStart(int x, int y) {
        return x == startX && y == startY;
    }

    private boolean isEnd(int x, int y) {
        return x == endX && y == endY;
    }

    /**
     * Solves graph using algorithm given as parameter. 0 / Default = Djikstra,
     * 1 = Euclidean distance A*, 2 = Diagonal distance A*, 3 = JPS.
     *
     * @param algorithm chosen algorithm.
     */
    public void solve(int algorithm) {
        clean();

        Node start = new Node(startX, startY);
        Node end = new Node(endX, endY);

        switch (algorithm) {
            case 1:
                path = new AStar(start, end, sizeX, sizeY, isWall, changes, new EuclideanHeuristic()).solve();
                break;
            case 2:
                path = new AStar(start, end, sizeX, sizeY, isWall, changes, new DiagonalHeuristic()).solve();
                break;
            case 3:
                path = new JPS(start, end, sizeX, sizeY, isWall, changes, new EuclideanHeuristic()).solve();
                break;
            default:
                path = new AStar(start, end, sizeX, sizeY, isWall, changes, new DjikstraHeuristic()).solve();
                break;
        }
    }

    /**
     * Visualisation tick will advance algorithm simulation by one change and at
     * the end path will be drawn.
     */
    public void visualisationTick() {
        // First we visualise how algorithm discovers and handles nodes and
        // after that we show the path algorithm found
        if (!changes.isEmpty()) {
            ColouredNode change = changes.poll();
            int cx = change.getX();
            int cy = change.getY();
            if (!isStart(cx, cy) && !isEnd(cx, cy)) {
                guiState[cx][cy] = change.getColor();
            }
        } else if (!path.isEmpty()) {
            for (Node node : path) {
                int x = node.getX();
                int y = node.getY();
                if (!isStart(x, y) && !isEnd(x, y)) {
                    guiState[x][y] = 4;
                }
            }
            path = new ArrayList<>();
            changes = new LinkedList<>();
        }
    }

    /**
     * Cleans changes made by pathfinding algorithm.
     */
    public void clean() {
        path = new ArrayList<>();
        changes = new LinkedList<>();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                guiState[x][y] = isWall[x][y] ? 3 : 0;
            }
        }
        guiState[startX][startY] = 1;
        guiState[endX][endY] = 2;
    }
}
