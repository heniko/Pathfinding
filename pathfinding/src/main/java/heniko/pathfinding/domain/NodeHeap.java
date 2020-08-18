package heniko.pathfinding.domain;

import heniko.pathfinding.util.PriorityNode;

/**
 * Priority queue implementation for PriorityNodes.
 *
 * @author Niko Hernesniemi
 */
public class NodeHeap {

    private List<PriorityNode> heap;

    /**
     * Constructor for NodeHeap.
     */
    public NodeHeap() {
        this.heap = new List<>(32);
    }

    /**
     * Gets the number of nodes in heap.
     *
     * @return Number of nodes.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Checks if heap is empty.
     *
     * @return True if heap is empty.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the highest priority node but doesn't remove it from the heap.
     *
     * @return Highest priority node.
     */
    public PriorityNode peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    /**
     * Returns the highest priority node and removes it from the heap.
     *
     * @return Highest priority node.
     */
    public PriorityNode poll() {
        if (isEmpty()) {
            return null;
        }
        // Swap first and last, pop last, shiftDown first, return popped
        heap.swap(0, size() - 1);
        PriorityNode res = heap.pop();
        shiftDown(0);
        return res;
    }

    /**
     * Adds new node to heap.
     *
     * @param node Added node.
     */
    public void add(PriorityNode node) {
        heap.add(node);
        shiftUp(size() - 1);
    }

    private void shiftUp(int i) {
        double priority = heap.get(i).getPriority();
        int currentPos = i;
        int parentPos = parent(i);
        // While has parent AND priority < parent priority
        // If currentPos = 0 then parent(currentPos) = -1
        while (parentPos > -1 && priority < heap.get(parentPos).getPriority()) {
            // Swap parent and current
            heap.swap(currentPos, parentPos);
            currentPos = parentPos;
            parentPos = parent(parentPos);
        }
    }

    private void shiftDown(int i) {
        // Check that i is not outside the heap
        if (i >= size()) {
            return;
        }
        boolean changeHappened = true;
        // Loops until we stop finding new lower priority value nodes
        while (changeHappened) {
            int dominant = getDominantNode(i);
            // change happened if i is not the dominant node
            changeHappened = i != dominant;
            // Swap i and dominant (if i == dominant no change will be made)
            heap.swap(i, dominant);
            // new i pos is the position of dominant node
            i = dominant;
        }
    }

    private int getDominantNode(int i) {
        // From i, left(i) and right(i) return node with lowest priority value
        // Assumes that i is valid index
        int dominant = i;
        dominant = getDominant(i, left(i));
        dominant = getDominant(dominant, right(i));
        return dominant;
    }

    private int getDominant(int ind1, int ind2) {
        if (ind2 >= size()) {
            return ind1;
        }
        // If ind1 has lower priority value return it
        if (heap.get(ind1).getPriority() < heap.get(ind2).getPriority()) {
            return ind1;
        }
        // Else return ind2
        return ind2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }
}
