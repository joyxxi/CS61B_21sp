package deque;

import java.util.Iterator;

/* Invariants:
1. Size is always (nextLast - nextFirst - 1)
2. The previous item of position 0 is always length-1.
3. The next item of position length-1 is always 0.
 */
public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Adds an item of type of T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        //Check if the array is full
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirst] = item;
        nextFirst = forwardNext(nextFirst);
        size += 1;

    }

    /** Adds an item of type of T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = item;
        nextLast = backwardNext(nextLast);
        size += 1;
    }

    /** Returns true if deque is empty. */
//    @Override
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Return the size of the deque. */
    @Override
    public int size() {
        return size;
    }

    /** For arrays of length 16 or more, adjust the length of the array when usage factor is lower than 25%.
     * usageFactor = size / length */
    private void usageControl() {
        if (items.length >= 16) {
            float usageFactor = ((float) size / items.length);
            if (usageFactor < 0.25) {
                //Change the usage factor to 50%.
                resize(size * 2);
            }
        }
    }

    /** Resize the array. Adjust the length of the array to desired CAPACITY. */
    private void resize(int capacity) {
        //Make sure the nextFirst and nextLast do not change;
        T[] newArray = (T[]) new Object[capacity];
        int firstPos = backwardNext(nextFirst);
        System.arraycopy(items, firstPos, newArray, 0, size-firstPos);
        System.arraycopy(items, 0, newArray, size-firstPos, firstPos);
        items = newArray;
        nextFirst = items.length-1;
        nextLast = size;
    }


    /** Prints the items in the deque from the first to the last. */
    @Override
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
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int indexOfItem = backwardNext(nextFirst);
        nextFirst = indexOfItem;
        T result = items[indexOfItem];
        items[indexOfItem] = null;
        size -= 1;
        usageControl();
        return result;
    }

    /** Removes and returns the item at the back of the deque. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int indexOfItem = forwardNext(nextLast);
        nextLast = indexOfItem;
        T result = items[indexOfItem];
        items[indexOfItem] = null;
        size -= 1;
        usageControl();
        return result;
    }

    /** Gets the item at the given index. If no such item, returns null. */
    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index >= size) {
            return null;
        }
        int start = backwardNext(nextFirst);
        int pos;
        if (index < items.length-start) {
            pos = start + index;
        } else {
            pos = index - (items.length - start);
        }
        return items[pos];
//        while (index > 0) {
//            start = backwardNext(start);
//            index--;
//        }
//        return items[start];
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private int pos = backwardNext(nextFirst);

        @Override
        public boolean hasNext() {
            return items[pos] == null;
        }

        @Override
        public T next() {
            T returnItem = items[pos];
            pos = backwardNext(pos);
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof ArrayDeque other) {
            if (size != other.size) {
                return false;
            }
            int firsPos = backwardNext(nextFirst);
            for (int i = 0; i < size - 1; i++) {
                T thisItem = get(i);
                Object otherItem = other.get(i);
                if (!thisItem.equals(otherItem)) {
                    return false;
                }
            }
        }
        return true;
    }

}
