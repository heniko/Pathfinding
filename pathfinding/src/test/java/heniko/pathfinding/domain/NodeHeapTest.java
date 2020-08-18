package heniko.pathfinding.domain;

import heniko.pathfinding.util.PriorityNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for NodeHeap
 *
 * @author Niko Hernesniemi
 */
public class NodeHeapTest {

    NodeHeap heap;

    @Before
    public void setUp() {
        this.heap = new NodeHeap();
    }

    @Test
    public void addTest() {
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
        heap.add(new PriorityNode(0, 0, 0.0));
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test
    public void peekTest() {
        heap.add(new PriorityNode(0, 0, 12));
        heap.add(new PriorityNode(0, 0, 2));
        heap.add(new PriorityNode(0, 0, 8));
        assertEquals(2, heap.peek().getPriority(), 0.0001);
        assertEquals(3, heap.size());
    }

    @Test
    public void pollTest() {
        heap.add(new PriorityNode(0, 0, 12));
        heap.add(new PriorityNode(0, 0, 2));
        heap.add(new PriorityNode(0, 0, 8));
        assertEquals(2, heap.poll().getPriority(), 0.0001);
    }

    @Test
    public void extendedPollTest() {
        heap.add(new PriorityNode(0, 0, 12));
        heap.add(new PriorityNode(0, 0, 2));
        heap.add(new PriorityNode(0, 0, 8));
        assertEquals(2, heap.poll().getPriority(), 0.0001);
        heap.add(new PriorityNode(0, 0, 11));
        heap.add(new PriorityNode(0, 0, 65));
        assertEquals(8, heap.poll().getPriority(), 0.0001);
        assertEquals(11, heap.poll().getPriority(), 0.0001);
        heap.add(new PriorityNode(0, 0, 1));
        assertEquals(1, heap.poll().getPriority(), 0.0001);
        heap.add(new PriorityNode(0, 0, 7));
        assertEquals(7, heap.poll().getPriority(), 0.0001);
    }
}
