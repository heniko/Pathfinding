package heniko.pathfinding.domain;

import heniko.pathfinding.util.ColouredNode;
import heniko.pathfinding.util.Node;
import heniko.pathfinding.util.PriorityNode;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * {@inheritDoc} This implementation uses jump point search.
 *
 * @author Niko Hernesniemi
 */
public final class JPS extends Pathfinder {

    /**
     * Constructor for jump point search pathfinding algorithm.
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
    public JPS(Node start, Node end, int sizeX, int sizeY, boolean[][] isWall, LinkedList<ColouredNode> changes, Heuristic heuristic) {
        super(start, end, sizeX, sizeY, isWall, changes, heuristic);
    }

    /**
     * {@inheritDoc} Jump point search pathfinding algorithm will be used.
     */
    @Override
    public List<Node> solve() {
        Heuristic jumpCostHeuristic = new DiagonalHeuristic();
        boolean[][] opened = new boolean[sizeX][sizeY];
        boolean[][] closed = new boolean[sizeX][sizeY];
        PriorityQueue<PriorityNode> openList = new PriorityQueue<>();
        Node[][] parent = new Node[sizeX][sizeY];
        double[][] gScore = new double[sizeX][sizeY];

        gScore[start.getX()][start.getY()] = 0;
        opened[start.getX()][start.getY()] = true;
        openList.add(new PriorityNode(start.getX(), start.getY(), heuristic.getHValue(start.getX(), start.getY(), end.getX(), end.getY())));

        while (!openList.isEmpty()) {
            PriorityNode current = openList.poll();

            int cx = current.getX();
            int cy = current.getY();

            if (closed[cx][cy]) {
                continue;
            }

            changes.add(new ColouredNode(cx, cy, 6));

            closed[cx][cy] = true;

            if (cx == end.getX() && cy == end.getY()) {
                pathLength = gScore[end.getX()][end.getY()];
                return reconstructPath(parent);
            }

            List<Node> adjList = getAdjList(cx, cy, parent);
            for (int i = 0; i < adjList.size(); i++) {
                Node neighbor = adjList.get(i);
                int nx = neighbor.getX();
                int ny = neighbor.getY();

                Node jp = jump(nx, ny, cx, cy);

                if (jp != null) {
                    int jx = jp.getX();
                    int jy = jp.getY();

                    if (closed[nx][ny]) {
                        continue;
                    }

                    double jg = gScore[cx][cy] + jumpCostHeuristic.getHValue(cx, cy, jx, jy);

                    if (!opened[jx][jy]) {
                        changes.add(new ColouredNode(jx, jy, 5));
                    }

                    if (!opened[jx][jy] || jg < gScore[jx][jy]) {
                        gScore[jx][jy] = jg;
                        double jh = heuristic.getHValue(jx, jy, end.getX(), end.getY());
                        parent[jx][jy] = new Node(cx, cy);
                        opened[jx][jy] = true;
                        openList.add(new PriorityNode(jx, jy, jg + jh));
                    }
                }
            }
        }
        return new List<>();
    }

    private Node jump(int x, int y, int px, int py) {

        // Check that it is possible to travel from parent to current
        if (!hasEdge(px, py, x, y)) {
            return null;
        }

        changes.add(new ColouredNode(x, y, 7));

        /*
        Node will be jump point if:
        It is the ending
        OR
        Has forced neihbors
        P: Parent
        C: Current
        #: Wall

        Forced JP example for vertical/horizontal
        | |P|#|
        | |C| |
        | | | |

        For diagonals we need to do horizontal and vertical checks with jump()
        |P| |#| |
        | |C| |1|
        |#| | | |
        C is forced JP because of node 1
         */
        if (end.getX() == x && end.getY() == y) {
            return new Node(x, y);
        }
        // Directions
        int dx = x - px;
        int dy = y - py;

        if (dx != 0 && dy != 0) {
            if (jump(x + dx, y, x, y) != null || jump(x, y + dy, x, y) != null) {
                return new Node(x, y);
            }
        } else if (dx != 0) {
            /*
            x, y is JP if there is wall over parent and no wall over current
            |#|?| |
            |P|C| |
            | | | |
            OR
            x, y is JP if there is wall under parent and no wall under current
            | | | |
            |P|C| |
            |#|?| |
             */

            boolean topForcedJP = isInsideGraph(x, y + 1) && isWall[x - dx][y + 1] && !isWall[x][y + 1];
            boolean bottomForcedJP = isInsideGraph(x, y - 1) && isWall[x - dx][y - 1] && !isWall[x][y - 1];

            if (topForcedJP || bottomForcedJP) {
                return new Node(x, y);
            }
        } else if (dy != 0) {
            boolean leftForcedJP = isInsideGraph(x + 1, y) && isWall[x + 1][y - dy] && !isWall[x + 1][y];
            boolean rightForcedJP = isInsideGraph(x - 1, y) && isWall[x - 1][y - dy] && !isWall[x - 1][y];

            if (leftForcedJP || rightForcedJP) {
                return new Node(x, y);
            }
        }

        // Explore deeper if current is not JP
        return jump(x + dx, y + dy, x, y);
    }

    private List<Node> getAdjList(int x, int y, Node[][] parent) {
        // For start we need all adjacent nodes.
        if (parent[x][y] == null) {
            return super.getAdjList(x, y);
        }

        List<Node> al = new List<>();

        // Normalized directions in x and y axis
        int dx = getNormalizedDirection(parent[x][y].getX(), x);
        int dy = getNormalizedDirection(parent[x][y].getY(), y);

        if (dx != 0 && dy != 0) {
            /* Diagonal direction
            Adj list should be:
            dx = 1, dy = 1
            | |1|2|
            | |/|3|
            | | | |
            OR
            dx = -1, dy = -1
            | | | |
            |3|/| |
            |2|1| |
            OR
            dx = -1, dy = 1
            |2|1| |
            |3|\| |
            | | | |
            OR
            dx = 1, dy = -1
            | | | |
            | |\|3|
            | |1|2|
            1 & 3 need to both have edge for 2 to have edge
             */
            // 1: x + dx, y
            if (hasEdge(x, y, x + dx, y)) {
                al.add(new Node(x + dx, y));
            }
            // 2: x +dx, y + dy
            if (hasEdge(x, y, x + dx, y + dy)) {
                al.add(new Node(x + dx, y + dy));
            }
            // 3: x, y + dy
            if (hasEdge(x, y, x, y + dy)) {
                al.add(new Node(x, y + dy));
            }

        } else if (dx != 0) {
            /* Horizontal direction
            Adj list should be:
            dx = 1, dy = 0
            | |5 |1|
            | |->|2|
            | |4 |3|
            OR
            dx = -1, dy = 0
            |1|5 | |
            |2|<-| |
            |3|4 | |
             */
            // 1: x + dx, y + 1
            if (hasEdge(x, y, x + dx, y + 1)) {
                al.add(new Node(x + dx, y + 1));
            }
            // 2: x + dx, y
            if (hasEdge(x, y, x + dx, y)) {
                al.add(new Node(x + dx, y));
            }
            // 3: x + dx, y - 1
            if (hasEdge(x, y, x + dx, y - 1)) {
                al.add(new Node(x + dx, y - 1));
            }
            // 4: x, y - 1
            if (hasEdge(x, y, x, y - 1)) {
                al.add(new Node(x, y - 1));
            }
            // 5: x, y + 1
            if (hasEdge(x, y, x, y + 1)) {
                al.add(new Node(x, y + 1));
            }
        } else if (dy != 0) {
            /* Vertical direction
            |1|2|3|
            |4|^|5|
            | | | |
             */
            // 1: x - 1, y + dy
            if (hasEdge(x, y, x - 1, y + dy)) {
                al.add(new Node(x - 1, y + dy));
            }
            // 2: x, y + dy
            if (hasEdge(x, y, x, y + dy)) {
                al.add(new Node(x, y + dy));
            }
            // 3: x + 1, y + dy
            if (hasEdge(x, y, x + 1, y + dy)) {
                al.add(new Node(x + 1, y + dy));
            }
            // 4: x - 1, y
            if (hasEdge(x, y, x - 1, y)) {
                al.add(new Node(x - 1, y));
            }
            // 5: x + 1, y
            if (hasEdge(x, y, x + 1, y)) {
                al.add(new Node(x + 1, y));
            }
        }

        return al;
    }

    private List<Node> reconstructPath(Node[][] parent) {
        List<Node> path = new List<>();
        Node current = end;
        Node next = parent[end.getX()][end.getY()];
        while (next != null) {
            // Add current to path
            path.add(current);
            // Get direction to next node
            int dx = getNormalizedDirection(current.getX(), next.getX());
            int dy = getNormalizedDirection(current.getY(), next.getY());
            // Add all nodes between current and next to path
            int x = current.getX();
            int y = current.getY();
            while (x != next.getX() || y != next.getY()) {
                x += dx;
                y += dy;
                path.add(new Node(x, y));
            }
            // current = next, next = parent of current
            current = next;
            next = parent[current.getX()][current.getY()];
        }
        // Add start to path since it will not be added in loop
        path.add(start);
        return path;
    }
}
