package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    /* Add a few items, checking isEmpty() and size() are correct.
     * Finally, print the deque.
     */
    public void addIsEmptySizePrintTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();

        assertTrue("The newly built deque should be empty.", ad1.isEmpty());

        ad1.addFist("Front");
        assertEquals(1, ad1.size());
        assertFalse("ld1 should now contain 1 item.", ad1.isEmpty());

        ad1.addLast("Middle");
        assertEquals(2, ad1.size());

        ad1.addLast("Back");
        assertEquals(3, ad1.size());

        System.out.println("Print out the deque");
        ad1.printDeque();
    }

    @Test
    /* Adds a few items, and then removes them. Make sure the deque is empty afterwards. */
    public void removeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();

        ad1.addFist(2);
        ad1.addFist(1);
        ad1.addLast(3);

        int rm1 = ad1.removeFirst();
        assertEquals(2, ad1.size());
        assertEquals("Should return the item removed before",1, rm1);

        int rm2 = ad1.removeLast();
        assertEquals(1, ad1.size());
        assertEquals("Should return the item removed before", 3, rm2);

        ad1.removeFirst();
        assertTrue("The deque should be empty now", ad1.isEmpty());
    }

    @Test
    /* Get specified-index item in the deque. */
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        //Test1: the deque is empty, should return null
        assertTrue("The deque should be empty now", ad1.isEmpty());
        assertNull("Should return null when item does not exist", ad1.get(5));

        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
        }
        int getResult1 = ad1.get(9);
        //Test2: general case to get the correct result, without resizing
        assertEquals("Should have the same value", 9, getResult1);
        //Test3: when index exceeds the size, should return null
        assertNull("Should return null when index exceeds the size of the deque", ad1.get(10000));

        //Test4: general case to get the correct result, with resize() and usageControl()
    }



}
