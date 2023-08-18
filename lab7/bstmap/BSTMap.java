package bstmap;

import jh61b.junit.In;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root;
    private int size;

    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left, right; //left and right subtrees

        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(BSTNode x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to containsKey() is null");
        }
        if (x == null) {
            return false;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return containsKey(x.left, key);
        } else if (cmp > 0) {
            return containsKey(x.right, key);
        } else {
            return true;
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = put(root, key, value);
    }
    private BSTNode put(BSTNode x, K key, V value) {
        if (x == null) {
            size += 1;
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.val = value;
        }
        return x;
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    /* Prints out the BSTMap in order of increasing Key*/
    public void printInOrder() {
        printInOrder(root);
    }
    private void printInOrder(BSTNode x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        System.out.print(x.key);
        System.out.print(" ");
        System.out.println(x.val);
        printInOrder(x.right);
//        V[] vals = (V []) new Object[size];
//        V[] val =(V []) new Object[]{x.val};
//        V[] leftVals = InOrderVals(x.left);
//        V[] rightVals = InOrderVals(x.right);
//        System.arraycopy(leftVals, 0, vals, 0, leftVals.length);
//        System.arraycopy(val, 0, vals, leftVals.length, 1);
//        System.arraycopy(rightVals, 0, vals, leftVals.length+1, rightVals.length);
//        return vals;
    }
}
