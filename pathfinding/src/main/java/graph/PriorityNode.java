package graph;

public class PriorityNode extends Node implements Comparable<PriorityNode> {

    private final double priority;

    public PriorityNode(int x, int y, double priority) {
        super(x, y);
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PriorityNode node) {
        if (priority <= node.getPriority()) {
            return -1;
        }
        return 1;
    }
}
