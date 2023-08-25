package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize = 16;
    private double maxLoad = 0.75;
    private int numOfKey;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(initialSize);
        numOfKey = 0;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = createTable(initialSize);
        numOfKey = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        buckets = createTable(initialSize);
        numOfKey = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    @Override
    public void clear() {
        buckets = createTable(initialSize);
        numOfKey = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int hashCodeKey = getHashCode(key);
        Collection<Node> bucket = buckets[hashCodeKey];

        if (bucket == null) {
            return null;
        }

        for (Node k : bucket) {
            if (k.key.equals(key)) {
                return k.value;
            }
        }
        return null;
    }

    private int getHashCode(K key) {
        int hashCodeKey = key.hashCode();
        hashCodeKey = Math.floorMod(hashCodeKey, buckets.length);
        return hashCodeKey;
    }

    @Override
    public int size() {
        return numOfKey;
    }

    @Override
    public void put(K key, V value) {
        int hashCodeKey = getHashCode(key);
        Collection<Node> bucket = buckets[hashCodeKey];
        Node newNode = new Node(key, value);

        if (bucket == null) {
            buckets[hashCodeKey] = createBucket();
            bucket = buckets[hashCodeKey];
            bucket.add(newNode);
            numOfKey += 1;
            resize();
            return;
        }

        for (Node k : bucket) {
            if (k.key.equals(key)) {
                k.value = value;
                return;
            }
        }

        bucket.add(newNode);
        numOfKey += 1;
        resize();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        if (numOfKey == 0) {
            return set;
        }

        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node k : bucket) {
                set.add(k.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int hashCodeKey = getHashCode(key);
        for (Node k : buckets[hashCodeKey]) {
            if (k.key.equals(key)) {
                V ret = k.value;
                buckets[hashCodeKey].remove(k);
                return ret;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        if (get(key) != value) {
            return null;
        }
        V ret = remove(key);
        return ret;
    }

    /** Returns an Iterator that iterates over the stored keys. */
    @Override
    public Iterator<K> iterator() {
        return new GetMyHashMapIterator();
    }

    private class GetMyHashMapIterator implements Iterator<K> {
        Set<K> keySet;
        Iterator<K> setIterator;

        GetMyHashMapIterator() {
            keySet = keySet();
            setIterator = keySet.iterator();
        }

        @Override
        public boolean hasNext() {
            return setIterator.hasNext();
        }

        @Override
        public K next() {
            return setIterator.next();
        }
    }

    /** Multiply the size of MYHASHMAP when the load factor exceeds the MAXLOAD. */
    private void resize() {
        double loadFactor = ((double)numOfKey) / buckets.length;
        if (loadFactor >= maxLoad) {
            MyHashMap<K, V> newBuckets = new MyHashMap<>(buckets.length * 2);
            for (Collection<Node> bucket : buckets) {
                if (bucket == null) {
                    continue;
                }
                for (Node k : bucket) {
                    newBuckets.put(k.key, k.value);
                }
            }
            buckets = newBuckets.buckets;
        }
    }

}
