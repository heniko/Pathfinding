package graph;

/**
 * Represents point in graph.
 *
 * @author Niko Hernesniemi
 */
public class Node {

    private final int x, y;

    /**
     * Constructor for Node
     *
     * @param x x-axis coordinate
     * @param y y-axis coordinate
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x-axis coordinate
     *
     * @return x-axis coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y-axis coordinate
     *
     * @return y-axis coordinate
     */
    public int getY() {
        return y;
    }
}
