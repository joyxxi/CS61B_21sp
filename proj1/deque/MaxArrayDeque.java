package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    /** Returns the maximum element in the deque as governed by the COMPARATOR.
     * If the MaxArrayDeque is empty, simply return null.
     */
    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            T item = get(i);
            if(comparator.compare(max, item) < 0) {
                max = item;
            }
        }
        return max;
    }

    /** Returns the maximum element in the deque as governed by the given comparator c.*/
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            T item = get(i);
            if (c.compare(max, item) < 0) {
                max = item;
            }
        }
        return max;
    }
}
