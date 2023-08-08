package deque;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

/* Use circular sentinel to implement the deque.
* Invariants:
* 1. sentinel.next always points to the first item; sentinel.prev always points to the last item.
* 2. The last item.next always points to the sentinel; the first item.prev always points to the sentinel.
* */
public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    /** Declare the basic unit NODE,
    which are made up of three parts:
    pointed to the previous Node, item, and pointed to the next Node. */
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;

        }
    }

    private Node sentinel;
    private int size;

    /** Constructor of the deque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        //sentinel = new Node(null, sentinel, sentinel); Error: Cannot assign field "prev" because "firstNode" is null
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Add an item to the front of the deque. */
    @Override
    public void addFirst(T item) {
        //Identify the first node. Invariants: sentinel.next always points to the first item.
/*        Node firstNode = sentinel.next;
        firstNode.prev = new Node(item, firstNode);
        sentinel.next = firstNode.prev;
        firstNode.prev.prev = sentinel;
        size += 1;*/
        Node firstNode = sentinel.next;
        firstNode.prev = new Node(item, sentinel, firstNode); //Cannot assign field "prev" because "firstNode" is null (Have to define sentinel.next and prev)
        sentinel.next = firstNode.prev;
        size += 1;
    }

    /** Add an item to the back of the deque. */
    @Override
    public void addLast(T item) {
/*        Node lastNode = sentinel.prev;
        lastNode.next = new Node(item, sentinel);
        sentinel.prev = lastNode.next;
        lastNode.next.prev = lastNode;
        size += 1;*/
        Node lastNode = sentinel.prev;
        lastNode.next = new Node(item, lastNode, sentinel);
        sentinel.prev = lastNode.next;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
//    @Override
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Print the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        // Create a pointer which starts from the first item
        Node p = sentinel.next;
        // Base case: there is no next item in the deque
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        // Once all the items have been printed, print out a new line.
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node firstNode = sentinel.next;
        T item = firstNode.item;
        firstNode.next.prev = sentinel;
        sentinel.next = firstNode.next;
        size -= 1;
        firstNode = null;
        return item;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node lastNode = sentinel.prev;
        T item = lastNode.item;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size -= 1;
        lastNode = null;
        return item;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * The deque cannot be altered.
     */
    @Override
    public T get(int index) {
        //Create a pointer to track the location
        Node p = sentinel.next;

        while (p.item != null) {
            if (index == 0) {
                return p.item;
            }
            p = p.next;
            index -= 1;
        }

        return null;
    }

    /** Recursive version of GET. */
    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, Node p) {
        if (p.item == null) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index-1, p.next);
    }


    /** Returns whether the parameter O is equal to the Deque. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof LinkedListDeque other) {
            if (other.size != this.size) {
                return false;
            }
            for (int i = 0; i < size-1; i++) {
                T thisItem = this.get(i);
                Object otherItem = (T) other.get(i);
                if(!thisItem.equals(otherItem)) {
                    return false;
                }
            }
        }
        return true;
    }

/*    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder();
        for (T x : this) {
            returnSB.append(x);
            returnSB.append(" ");
        }
        return returnSB.toString();
    }*/


    /** Returns an iterator. */
    @Override
    public Iterator<T> iterator() {
        return new lldIterator();
    }

    private class lldIterator implements Iterator<T> {
        //Create a pointer always points to the next item
        Node pos = sentinel.next;

        @Override
        public boolean hasNext() {
            return pos.item != null;
        }

        @Override
        public T next() {
            T returnItem = pos.item;
            pos = pos.next;
            return returnItem;
        }
    }


}
