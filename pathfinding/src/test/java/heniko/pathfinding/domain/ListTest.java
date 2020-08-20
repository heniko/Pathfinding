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
        list.enqueue(3);
        list.enqueue(6);
        list.enqueue(9);
        int element = list.dequeue();
        assertEquals(element, 3);
        element = list.dequeue();
        assertEquals(element, 6);
        list.enqueue(7);
        element = list.dequeue();
        assertEquals(element, 9);
        element = list.dequeue();
        assertEquals(element, 7);
    }

    @Test
    public void dequeueAndGetTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.dequeue();
        int element = list.get(0);
        assertEquals(element, 8);
    }

    @Test
    public void dequeueAndPopTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.dequeue();
        int element = list.pop();
        assertEquals(element, 1);
    }

    @Test
    public void dequeueAndPutTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.dequeue();
        list.put(0, 2);
        int element = list.get(0);
        assertEquals(element, 2);
    }

    @Test
    public void dequeueAndSwapTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.dequeue();
        list.swap(0, 1);
        int element = list.get(0);
        assertEquals(element, 1);
    }
    
    @Test
    public void dequeAndGrow() {
        list = new List<>(10);
        list.enqueue(5);
        list.enqueue(8);
        list.enqueue(1);
        list.dequeue();
        for (int i = 0; i < 40; i++) {
            list.enqueue(i);
        }
        int element = list.get(0);
        assertEquals(element, 8);
    }
    
    @Test
    public void reverseTest() {
        list.add(5);
        list.add(8);
        list.add(1);
        list.reverse();
        int element = list.get(0);
        assertEquals(element, 1);
        element = list.get(1);
        assertEquals(element, 8);
        element = list.get(2);
        assertEquals(element, 5);
    }
}
