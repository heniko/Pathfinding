package heniko.pathfinding.domain;

/**
 * Simple generic list.
 *
 * @author Niko Hernesniemi
 */
public class List<E> {

    private Object[] array;
    private int maxSize;
    private int size;

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
        this(8);
    }

    /**
     * Gets current number of elements in list.
     *
     * @return Number of elements.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if list has no items.
     *
     * @return True if list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds element to the end of the list. Automatically increases max size of
     * the list if needed.
     *
     * @param element New element.
     */
    public void add(E element) {
        if (size == maxSize) {
            grow();
        }
        array[size] = element;
        size++;
    }

    /**
     * Gets element at given index.
     *
     * @param index Index of the element.
     * @return Element in given index.
     * @throws IndexOutOfBoundsException Index was out of bounds.
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
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
        if (index1 < 0 || index2 < 0 || index1 >= size || index2 >= size) {
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
        if (index < 0 || index >= size) {
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
    public E pop() throws IndexOutOfBoundsException {
        if (size < 1) {
            throw new IndexOutOfBoundsException();
        }
        size--;
        return (E) array[size];
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
}
