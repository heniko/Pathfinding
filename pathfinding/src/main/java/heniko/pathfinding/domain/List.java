package heniko.pathfinding.domain;

/**
 * Simple generic list implementation. Can work as list, stack or queue.
 *
 * @author Niko Hernesniemi
 */
public class List<E> {

    private Object[] array;
    private int maxSize;
    private int startInd;
    private int endInd;

    /**
     * Constructor for List.
     *
     * @param initialSize Initial max size of the list. This should be more than
     * 0.
     * @throws IllegalArgumentException Initial size of the list was too low.
     */
    public List(int initialSize) throws IllegalArgumentException {
        if (initialSize < 1) {
            throw new IllegalArgumentException();
        }
        this.maxSize = initialSize;
        this.array = new Object[maxSize];
    }

    /**
     * Creates new list with default initial max size.
     */
    public List() {
        // Default size of 8 was chosen because that is the max needed length of 
        // adjacency list.
        this(8);
    }

    /**
     * Gets current number of elements in list.
     *
     * @return Number of elements.
     */
    public int size() {
        return endInd - startInd;
    }

    /**
     * Checks if list has no items.
     *
     * @return True if list is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adds element to the end of the list. Automatically increases max size of
     * the list if needed.
     *
     * @param element New element.
     */
    public void add(E element) {
        if (endInd == maxSize) {
            grow();
        }
        array[endInd] = element;
        endInd++;
    }

    /**
     * Gets element at given index.
     *
     * @param index Index of the element.
     * @return Element in given index.
     * @throws IndexOutOfBoundsException Index was out of bounds.
     */
    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        index += startInd;
        if (!isInsideList(index)) {
            throw new IndexOutOfBoundsException();
        }
        return (E) array[index];
    }

    /**
     * Swaps elements on given indices.
     *
     * @param index1 Index of first element.
     * @param index2 Index of second element.
     * @throws IndexOutOfBoundsException Index of index1 or index2 was out of
     * bounds.
     */
    public void swap(int index1, int index2) throws IndexOutOfBoundsException {
        index1 += startInd;
        index2 += startInd;
        if (!isInsideList(index1) || !isInsideList(index2)) {
            throw new IndexOutOfBoundsException();
        }
        Object o = array[index1];
        array[index1] = array[index2];
        array[index2] = o;
    }

    /**
     * Replaces element on list with a new one.
     *
     * @param index Index element will be placed to.
     * @param element New element.
     * @throws IndexOutOfBoundsException Given index is outside of the list.
     */
    public void put(int index, E element) throws IndexOutOfBoundsException {
        index += startInd;
        if (!isInsideList(index)) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = element;
    }

    /**
     * Adds element to the top of the stack.
     *
     * @param element New element
     */
    public void push(E element) {
        add(element);
    }

    /**
     * Gets and removes last element of the list.
     *
     * @return Last element of the list.
     * @throws IndexOutOfBoundsException List has no elements.
     */
    @SuppressWarnings("unchecked")
    public E pop() throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        endInd--;
        return (E) array[endInd];
    }

    /**
     * Adds new element to the end of the queue.
     *
     * @param element New element.
     */
    public void enqueue(E element) {
        add(element);
    }

    /**
     * Gets and removes the first element in the queue.
     *
     * @return First element in queue.
     * @throws IndexOutOfBoundsException If list is empty.
     */
    @SuppressWarnings("unchecked")
    public E dequeue() throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        startInd++;
        return (E) array[startInd - 1];
    }

    private void grow() {
        // Doubles the size of the array
        int newMaxSize = maxSize * 2;
        Object[] newArray = new Object[newMaxSize];

        for (int i = 0; i < maxSize; i++) {
            newArray[i] = array[i];
        }

        maxSize = newMaxSize;
        array = newArray;
    }

    private boolean isInsideList(int index) {
        return !(index < startInd || index >= endInd);
    }
}
