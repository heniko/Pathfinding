package graph;

/**
 * Node with added comparable priority.
 *
 * @author Niko Hernesniemi
 */
public class PriorityNode extends Node implements Comparable<PriorityNode> {

    private final double priority;

    /**
     * Constructor for PriorityNode
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
     * Gets priority of PriorityNode
     *
     * @return
     */
    public double getPriority() {
        return priority;
    }

    /**
     * Compares this node to other node
     *
     * @param node node compared to
     * @return -1 if this node has higher priority than the node given as
     * parameter and 1 otherwise
     */
    @Override
    public int compareTo(PriorityNode node) {
        if (priority <= node.getPriority()) {
            return -1;
        }
        return 1;
    }
}
