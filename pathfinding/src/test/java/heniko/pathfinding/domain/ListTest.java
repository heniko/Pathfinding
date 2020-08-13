package heniko.pathfinding.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for List.
 *
 * @author Niko Hernesniemi
 */
public class ListTest {

    List<Integer> list;

    @Before
    public void setUp() {
        list = new List<>(4);
    }

    @Test
    public void addElementTest() {
        assertEquals(list.size(), 0);
        list.add(5);
        assertEquals(list.size(), 1);
    }

    @Test
    public void getElementsTest() {
        list.add(5);
        int element = list.get(0);
        assertEquals(element, 5);
        list.add(8);
        element = list.get(1);
        assertEquals(element, 8);
    }

    @Test
    public void listMaxSizeGrowsTest() {
        list.add(3);
        list.add(6);
        list.add(9);
        list.add(11);
        list.add(42);
        assertEquals(list.size(), 5);
        int element = list.get(4);
        assertEquals(element, 42);
    }

    @Test
    public void swappingElementsTest() {
        list.add(3);
        list.add(6);
        list.add(9);
        list.swap(0, 2);
        int element1 = list.get(0);
        int element2 = list.get(2);
        assertEquals(element1, 9);
        assertEquals(element2, 3);
    }

    @Test
    public void putTest() {
        list.add(3);
        int element = list.get(0);
        assertEquals(element, 3);
        list.put(0, 9);
        element = list.get(0);
        assertEquals(element, 9);
    }

    @Test
    public void stackTest() {
        list.push(3);
        list.push(6);
        list.push(9);
        int element = list.pop();
        assertEquals(element, 9);
        element = list.pop();
        assertEquals(element, 6);
        element = list.pop();
        assertEquals(element, 3);
        assertEquals(list.size(), 0);
    }

    @Test
    public void isEmptyTest() {
        assertEquals(list.isEmpty(), true);
        list.push(2);
        assertEquals(list.isEmpty(), false);
        list.pop();
        assertEquals(list.isEmpty(), true);
    }

    @Test
    public void queueTest() {
        list.endQueue(3);
        list.endQueue(6);
        list.endQueue(9);
        int element = list.deQueue();
        assertEquals(element, 3);
        element = list.deQueue();
        assertEquals(element, 6);
        list.endQueue(7);
        element = list.deQueue();
        assertEquals(element, 9);
        element = list.deQueue();
        assertEquals(element, 7);
    }

    @Test
    public void deQueueAndGetTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.deQueue();
        int element = list.get(0);
        assertEquals(element, 8);
    }

    @Test
    public void deQueueAndPopTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.deQueue();
        int element = list.pop();
        assertEquals(element, 1);
    }

    @Test
    public void deQueueAndPutTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.deQueue();
        list.put(0, 2);
        int element = list.get(0);
        assertEquals(element, 2);
    }
    
    @Test
    public void deQueueAndSwapTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.deQueue();
        list.swap(0, 1);
        int element = list.get(0);
        assertEquals(element, 1);
    }
}
