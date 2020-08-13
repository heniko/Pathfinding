package heniko.pathfinding.domain;

import heniko.pathfinding.util.ColouredNode;
import heniko.pathfinding.util.Node;
import heniko.pathfinding.util.PriorityNode;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * {@inheritDoc} This implementation uses A*.
 *
 * @author Niko Hernesniemi
 */
public final class AStar extends Pathfinder {

    /**
     * Constructor for A* pathfinding algorithm.
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
    public AStar(Node start, Node end, int sizeX, int sizeY, boolean[][] isWall, LinkedList<ColouredNode> changes, Heuristic heuristic) {
        super(start, end, sizeX, sizeY, isWall, changes, heuristic);
    }

    /**
     * {@inheritDoc} A* pathfinding algorithm will be used.
     */
    @Override
    public List<Node> solve() {
        // Keeps track of nodes that have been opened. This was needed because
        // gScore values are initialized to 0.
        boolean[][] opened = new boolean[sizeX][sizeY];
        // Keeps track of nodes that have been already handled.
        boolean[][] closed = new boolean[sizeX][sizeY];
        // Holds discovered nodes
        PriorityQueue<PriorityNode> openList = new PriorityQueue<>();
        // This will keep track of the path
        Node[][] parent = new Node[sizeX][sizeY];
        // Keeps track of the current g of each node
        double[][] gScore = new double[sizeX][sizeY];

        // Start algorithms from start node
        gScore[start.getX()][start.getY()] = 0;
        opened[start.getX()][start.getY()] = true;
        openList.add(new PriorityNode(start.getX(), start.getY(), heuristic.getHValue(start.getX(), start.getY(), end.getX(), end.getY())));

        while (!openList.isEmpty()) {
            PriorityNode current = openList.poll();
            int cx = current.getX();
            int cy = current.getY();

            // Since the algorithm just adds new nodes of the same spot to heap
            // instead of updating the priority we may get the same node more than once.
            // There is no need to handle the same node more than once.
            if (closed[cx][cy]) {
                continue;
            }

            // Add node to changes list as handled
            changes.add(new ColouredNode(cx, cy, 6));

            closed[cx][cy] = true;
            // Check if we have reached the end
            if (cx == end.getX() && cy == end.getY()) {
                pathLength = gScore[end.getX()][end.getY()];
                return reconstructPath(parent);
            }
            List<Node> adjList = getAdjList(cx, cy);
            for (int i = 0; i < adjList.getSize(); i++) {
                Node neighbor = adjList.get(i);
                int nx = neighbor.getX();
                int ny = neighbor.getY();

                if (closed[nx][ny]) {
                    continue;
                }

                // For moving vertically or horizontally the cost is 1. For
                // diagonal moves the cost is sqrt(2).
                double cnCost = (cx - nx == 0 || cy - ny == 0) ? 1 : SQRT2;
                double ng = gScore[cx][cy] + cnCost;

                if (!opened[nx][ny]) {
                    // Add node to changes list as discovered. Node will be added
                    // to the list only when it is discovered for the first time
                    // since discovering it again wouldn't make any changes to GUI.
                    changes.add(new ColouredNode(nx, ny, 5));
                }
                // Changes are made to neighbor if this is the first time we
                // discover it or if the new g is lower than the previously stored one.
                if (!opened[nx][ny] || ng < gScore[nx][ny]) {
                    gScore[nx][ny] = ng;
                    double nh = heuristic.getHValue(nx, ny, end.getX(), end.getY());
                    parent[nx][ny] = new Node(cx, cy);
                    opened[nx][ny] = true;
                    openList.add(new PriorityNode(nx, ny, ng + nh));
                }
            }
        }

        // If we do not find a path empty list will be returned
        return new List<>();
    }

    private List<Node> reconstructPath(Node[][] parent) {
        List<Node> path = new List<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            current = parent[current.getX()][current.getY()];
        }
        return path;
    }
}
