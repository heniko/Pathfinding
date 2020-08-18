package heniko.pathfinding.domain;

import heniko.pathfinding.util.ColouredNode;
import heniko.pathfinding.util.Mathematics;
import heniko.pathfinding.util.Node;

/**
 * Pathfinding algorithm.
 *
 * @author Niko Hernesniemi
 */
public abstract class Pathfinder {

    protected final double SQRT2 = Math.sqrt(2);
    protected final Node start;
    protected final Node end;
    protected final int sizeX;
    protected final int sizeY;
    protected final boolean[][] isWall;
    protected final List<ColouredNode> changes;
    protected final Heuristic heuristic;
    protected double pathLength;

    /**
     * Constructor for abstract class Pathfinder.
     *
     * @param start Start node
     * @param end End node
     * @param sizeX Size of the graph in x-axis
     * @param sizeY Size of the boar in y-axis
     * @param isWall Information about walls for algorithm
     * @param changes Logger for keeping track of the changes needed in order to
     * visualise the algorithm
     * @param heuristic Heuristic for calculating h-value of the node
     */
    public Pathfinder(Node start, Node end, int sizeX, int sizeY, boolean[][] isWall, List<ColouredNode> changes, Heuristic heuristic) {
        this.start = start;
        this.end = end;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.isWall = isWall;
        this.changes = changes;
        this.heuristic = heuristic;
        this.pathLength = 0;
    }

    /**
     * Solves path from start node to end node.
     *
     * @return Path as a list of nodes. Contains start and end nodes. Empty list
     * will be returned is path doesn't exist.
     */
    public abstract List<Node> solve();

    /**
     * Gets the path length. If no path exists returns 0. If graph isn't solved
     * using solve() before getting graph length it will also be 0.
     *
     * @return Length of the path.
     */
    public double getPathLength() {
        return pathLength;
    }

    /**
     * Gets adjacency list for given node.
     *
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @return List containing the adjacent nodes.
     */
    protected List<Node> getAdjList(int x, int y) {
        List<Node> adjList = new List<>();
        /*
        Edge numbers
        7 |  0  | 1
        6 |node | 2
        5 |  4  | 3
        (For now at least) we are not going to allow skipping corners
        so if for example 2 is a wall then 1 would not be adjacent.

        Offsets:
        x-1,y+1|x,y+1|x+1,y+1
        x-1,y  |node |x+1,y
        x-1,y-1|x,y-1|x+1,y-1
         */
        boolean e0 = false, e2 = false, e4 = false, e6 = false;

        // Check vertical and horizon
        if (isInsideGraph(x, y + 1) && !isWall[x][y + 1]) {
            e0 = true;
        }
        if (isInsideGraph(x + 1, y) && !isWall[x + 1][y]) {
            e2 = true;
        }
        if (isInsideGraph(x, y - 1) && !isWall[x][y - 1]) {
            e4 = true;
        }
        if (isInsideGraph(x - 1, y) && !isWall[x - 1][y]) {
            e6 = true;
        }

        // Add vertical and horizontal adjacent nodes
        if (e0) {
            adjList.add(new Node(x, y + 1));
        }
        if (e2) {
            adjList.add(new Node(x + 1, y));
        }
        if (e4) {
            adjList.add(new Node(x, y - 1));
        }
        if (e6) {
            adjList.add(new Node(x - 1, y));
        }

        // Add diagonal adjacent nodes to list
        if (e0 && e2 && !isWall[x + 1][y + 1]) {
            adjList.add(new Node(x + 1, y + 1));
        }
        if (e2 && e4 && !isWall[x + 1][y - 1]) {
            adjList.add(new Node(x + 1, y - 1));
        }
        if (e4 && e6 && !isWall[x - 1][y - 1]) {
            adjList.add(new Node(x - 1, y - 1));
        }
        if (e0 && e6 && !isWall[x - 1][y + 1]) {
            adjList.add(new Node(x - 1, y + 1));
        }
        return adjList;
    }

    /**
     * Checks if given node is inside the graph.
     *
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @return True if given node is inside the graph.
     */
    protected boolean isInsideGraph(int x, int y) {
        return !(x < 0 || y < 0 || x >= sizeX || y >= sizeY);
    }

    /**
     * Checks that both source and destination are inside the graph. Then checks
     * if edge exists between the nodes.
     *
     * @param sx Source node x-coordinate.
     * @param sy Source node y-coordinate.
     * @param dx Destination node x-coordinate.
     * @param dy Destination node y-coordinate.
     * @return True if edge exists between nodes.
     */
    protected boolean hasEdge(int sx, int sy, int dx, int dy) {
        // If source or destination is outside graph there is no edge
        if (!isInsideGraph(dx, dy) || !isInsideGraph(sx, sy)) {
            return false;
        }
        // We need to check that directions in x and y axis are walkable and also
        // our destination is walkable.
        int dirX = dx - sx;
        int dirY = dy - sy;
        boolean xWalkable = !isWall[sx + dirX][sy];
        boolean yWalkable = !isWall[sx][sy + dirY];
        boolean dWalkable = !isWall[dx][dy];
        return xWalkable && yWalkable && dWalkable;
    }

    /**
     * Calculates normalised direction in one axis.
     *
     * @param src Source.
     * @param dest Destination.
     * @return Normalised direction.
     */
    protected int getNormalizedDirection(int src, int dest) {
        // Should return -1, 0 or 1 as direction
        int dx = dest - src;
        // Math.max to prevent division by zero
        return dx / Mathematics.max(Mathematics.abs(dx), 1);
    }

    /**
     * Logs GUI changes needed for visualising pathfinding algorithms.
     *
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @param color Change type.
     */
    protected void logChange(int x, int y, int color) {
        changes.enqueue(new ColouredNode(x, y, color));
    }
}
