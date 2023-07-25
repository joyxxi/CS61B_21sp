package deque;

/* Invariants:
1. Size is always (nextLast - nextFirst - 1)
2. The previous item of position 0 is always length-1.
3. The next item of position length-1 is always 0.
 */
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T []) new Object[16];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Adds an item of type of T to the front of the deque. */
    public void addFist(T item) {
        //Check if the array is full
        if (size == items.length) {
            resize();
        }

        items[nextFirst] = item;
        nextFirst = forwardNext(nextFirst);
        size += 1;

    }

    /** Adds an item of type of T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            resize();
        }

        items[nextLast] = item;
        nextLast = backwardNext(nextLast);
        size += 1;
    }

    /** Returns true if deque is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return the size of the deque. */
    public int size() {
        return size;
    }

    /** For arrays of length 16 or more, adjust the length of the array when usage factor is lower than 25%. */
    public void usageControl() {
    // usage = size / length
    }

    /** Resize the array. */
    public void resize() {
        //Make sure the nextFirst and nextLast do not change
    }


    /** Prints the items in the deque from the first to the last. */
    public void printDeque() {
        //The deque starts from nextFirst+1
        int start = backwardNext(nextFirst);
        int end = forwardNext(nextLast);
        int i = start;
        while (i != end) {
            System.out.print(items[i] + " ");
            i = backwardNext(i);
        }
        System.out.println(items[i]);
        }


    /** Return the next index+1 position. */
    private int backwardNext(int i) {
        if (i + 1 > items.length - 1) {
            return 0;
        }
        return i + 1;

    }

    /** Return the next reasonable index-1 position. */
    private int forwardNext(int i) {
        if (i - 1 < 0) {
            return items.length - 1;
        }
        return i - 1;
    }

    /** Removes and returns the item at the front of the deque. */
    public T removeFirst() {
        int indexOfItem = backwardNext(nextFirst);
        nextFirst = indexOfItem;
        T result = items[indexOfItem];
        items[indexOfItem] = null;
        size -= 1;
        return result;
    }

    /** Removes and returns the item at the back of the deque. */
    public T removeLast() {
        int indexOfItem = forwardNext(nextLast);
        nextLast = indexOfItem;
        T result = items[indexOfItem];
        items[indexOfItem] = null;
        size -= 1;
        return result;
    }

    /** Gets the item at the given index. If no such item, returns null. */
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index > size) {
            return null;
        }
        int start = backwardNext(nextFirst);
        while (index > 0) {
            start = backwardNext(start);
            index--;
        }
        return items[start];
    }

}
