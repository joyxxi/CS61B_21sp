package deque;

import jh61b.junit.In;
import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    /* Tests getting specified-index item. */
    public void getTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        //Test1: general case. Should be 4.
        int getResult1 = lld1.get(4);
        assertEquals("Should have the same value", 4, getResult1);

        //Test2: the index exceeds the range. Should be null.
        assertNull("Should return null", lld1.get(200));

        //Test3: the deque is empty. Should be null.
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        assertNull("Should return null", lld2.get(5));
    }

    @Test
    /* Tests recursive version of getting specified-index item. */
    public void getRecursiveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        //Test1: general case. Should be 4.
        int getResult1 = lld1.getRecursive(4);
        assertEquals("Should have the same value", 4, getResult1);

        //Test2: the index exceeds the range. Should be null.
        assertNull("Should return null", lld1.getRecursive(200));

        //Test3: the deque is empty. Should be null.
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        assertNull("Should return null", lld2.getRecursive(5));
    }

    @Test
    /* Test if the deque is iterable. Test the concise for loop. */
    public void iteratorTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addLast("Birds");
        lld1.addLast("Dogs");
        lld1.addLast("Flowers");

        StringBuilder returnSB = new StringBuilder();
        for (String item: lld1) {
            returnSB.append(item);
            returnSB.append(" ");
        }
        String expectString = "Birds Dogs Flowers ";
        String actualString = returnSB.toString();
        assertEquals("Two strings should be the same", expectString, actualString);
    }

    @Test
    /* Separately compares two equal and unequal deque, and tests if equals() works. */
    public void equalsTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld2.addLast(i);
        }
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld3.addFirst(i);
        }
        LinkedListDeque<Integer> lld4 = new LinkedListDeque<>();
        for (int i = 0; i < 5; i++) {
            lld4.addLast(i);
        }

        //Test1: Compare two equal deque.
        assertTrue("Two deques should be equal and returns True", lld1.equals(lld2));
        //Test2: Compare two unequal deque.
        assertFalse("Two deques should be unequal and returns False", lld1.equals(lld3));
        //Test3: Test the null case.
        Object o = null;
        assertFalse("Should return false if the argument is null", lld1.equals(o));
        //Test: Compare deque with different size.
        assertFalse("Should return false", lld1.equals(lld4));

    }
}
