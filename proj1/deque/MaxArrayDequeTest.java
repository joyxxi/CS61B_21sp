package deque;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {
    Comparator<Integer> integerGetMaxComparator = new GetMaxComparator<>();
    Comparator<Integer> alwaysLessC = new AlwaysLessComparator<>();
    Comparator<String> stringComparator = new GetMaxComparator<>();


    @Test
    public void maxTest() {
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(integerGetMaxComparator);
        for (int i = 0; i < 10; i++) {
            mad1.addFirst(i);
        }

        int max = mad1.max();
        int max2 = mad1.max(alwaysLessC);
        //Test1: if max returns the true value
        assertEquals("The max should be the actual max number", 9, max);
        assertEquals("The max should be the last number in the deque", 0, max2);
        //Test2: if the deque is empty, should return null
        MaxArrayDeque<String> mad2 = new MaxArrayDeque<>(stringComparator);
        assertNull("The deque is empty, should return null", mad2.max());

    }

    private static class GetMaxComparator<T> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }

    private static class AlwaysLessComparator<T> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return -10;
        }
    }
}
