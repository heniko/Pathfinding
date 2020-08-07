package graph;

/**
 * Node with additional integer for storing GUI changes needed for visualising
 * pathfinding algorithm.
 *
 * @author Niko Hernesniemi
 */
public class ColouredNode extends Node {

    private final int color;

    /**
     * Constructor for ColouredNode.
     *
     * @param x x-coordinate position
     * @param y y-coordinate position
     * @param color 5 = discovered, 6 = handled.
     */
    public ColouredNode(int x, int y, int color) {
        super(x, y);
        this.color = color;
    }

    /**
     * Gets colour value of node.
     *
     * @return colour value
     */
    public int getColor() {
        return color;
    }

}
