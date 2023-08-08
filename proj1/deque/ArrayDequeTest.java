package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest{

    @Test
    /* Add a few items, checking isEmpty() and size() are correct.
     * Finally, print the deque.
     */
    public void addIsEmptySizePrintTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();

        assertTrue("The newly built deque should be empty.", ad1.isEmpty());

        ad1.addFirst("Front");
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
        //Test1:
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.addLast(3);

        int rm1 = ad1.removeFirst();
        assertEquals(2, ad1.size());
        assertEquals("Should return the item removed before",1, rm1);

        int rm2 = ad1.removeLast();
        assertEquals(1, ad1.size());
        assertEquals("Should return the item removed before", 3, rm2);

        ad1.removeFirst();
        assertTrue("The deque should be empty now", ad1.isEmpty());

        //Test2: Remove from an empty deque, should return null and the size should still be 0.
        assertNull("Remove from an empty deque should return null", ad1.removeFirst());
        assertEquals("The size of the deque should still be 0", 0, ad1.size());
        assertNull("Remove from an empty deque should return null", ad1.removeLast());
        assertEquals("The size of the deque should still be 0", 0, ad1.size());
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

    @Test
    /* Test resize() and usageControl(). The usage factor of the array should always higher than 25. */
    public void resizeTest() {
        //Add 1000 items to the deque, and them remove 9999 items.
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            ad1.addLast(i);
        }
        for (int i = 0; i < 9999; i++) {
            ad1.removeLast();
        }
    }

    @Test
    public void equalTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
        }
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad2.addLast(i);
        }
        ArrayDeque<Integer> ad3 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad3.addFirst(i);
        }
        ArrayDeque<Integer> ad4 = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            ad4.addLast(i);
        }
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }

        //Test1: Compare two equal deque.
        assertTrue("Two deques should be equal and returns True", ad1.equals(ad2));
        //Test2: Compare two unequal deque.
        assertFalse("Two deques should be unequal and returns False", ad1.equals(ad3));
        //Test3: Test the null case.
        Object o = null;
        assertFalse("Should return false if the argument is null", ad1.equals(o));
        //Test: Compare deque with different size.
        assertFalse("Should return false", ad1.equals(ad4));
        //Test: Compare deque with different types.
        assertFalse("Should return false", ad1.equals(lld1));
    }




}
