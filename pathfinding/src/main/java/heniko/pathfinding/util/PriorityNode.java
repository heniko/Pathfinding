package heniko.pathfinding.util;

/**
 * Node with added comparable priority.
 *
 * @author Niko Hernesniemi
 */
public class PriorityNode extends Node {

    private final double priority;

    /**
     * Constructor for PriorityNode.
     *
     * @param x x-axis coordinate
     * @param y y-axis coordinate
     * @param priority
     */
    public PriorityNode(int x, int y, double priority) {
        super(x, y);
        this.priority = priority;
    }

    /**
     * Gets priority of PriorityNode.
     *
     * @return priority of the node
     */
    public double getPriority() {
        return priority;
    }
}
