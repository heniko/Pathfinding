package algorithms;

import graph.ColouredNode;
import graph.Node;
import graph.PriorityNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar {

    // SQRT2 is constant cost for diagonal moves
    private final double SQRT2 = Math.sqrt(2);
    private final Node start;
    private final Node end;
    private final int sizeX;
    private final int sizeY;
    private final boolean[][] isWall;
    private LinkedList<ColouredNode> changes;
    private final Heuristic heuristic;

    public AStar(Node start, Node end, int sizeX, int sizeY, boolean[][] isWall, LinkedList<ColouredNode> changes, Heuristic heuristic) {
        this.start = start;
        this.end = end;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.isWall = isWall;
        this.changes = changes;
        this.heuristic = heuristic;
    }

    public ArrayList<Node> solve() {
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
                return reconstructPath(parent);
            }
            ArrayList<Node> adjList = getAdjList(cx, cy);
            for (Node neighbor : adjList) {
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
        return new ArrayList<>();
    }

    private ArrayList<Node> getAdjList(int x, int y) {
        ArrayList<Node> adjList = new ArrayList<>();
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

    private boolean isInsideGraph(int x, int y) {
        return !(x < 0 || y < 0 || x >= sizeX || y >= sizeY);
    }

    private ArrayList<Node> reconstructPath(Node[][] parent) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            current = parent[current.getX()][current.getY()];
        }
        return path;
    }
}
