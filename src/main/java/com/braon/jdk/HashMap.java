package com.braon.jdk;

public class HashMap<K, V> {
    private Node<K, V>[] table;
    private int capacity = 4;
    private float loadFactor = 0.75f;
    private int threshold = 3;
    private int size;

    public HashMap() {
        init();
    }

    public HashMap(int capacity, int loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.threshold = capacity * loadFactor;
        init();
    }

    @SuppressWarnings("unchecked")
	private void init() {
        capacity = 1 << 4;
        table = new Node[capacity];
        size = 0;
    }

    public void clear() {
        init();
    }

    public void put(K K, V V) {
        if (K == null || V == null) {
            return;
        }
        // space is narrow
        else if (size + 1 > threshold) {
            resize();
        }

        Node<K, V> newNode = new Node<>();
        newNode.setK(K);
        newNode.setV(V);
        newNode.setHash(K.hashCode() & (capacity - 1));
        newNode.setNext(null);

        if (putNode(newNode))
            size++;
    }

    public V get(K K) {
        // calculate store place
        int hash = K.hashCode() & (capacity - 1);
        Node<K, V> first = table[hash];

        if (first != null) {
            do {
                if (first.getK().equals(K))
                    return first.getV();
            } while ((first = first.next) != null);
            return null;
        } // if
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        // recaculate the array size
        capacity = capacity << 1;
        threshold = (int)(loadFactor * capacity);

        // keep the old one, and renew a new table
        Node<K, V>[] oldTable = table;
        table = new Node[capacity];
        // relay the elements
        for (Node<K, V> first : oldTable) {
            while (first != null) {
                first.setHash(first.getK().hashCode() & (capacity - 1));
                this.putNode(first);

                // we need to remove the link
                Node<K, V> next = first.next;
                first.next = null;
                first = next;
            } // while
        } // for
    }

    int getThreshold() {
        return threshold;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    private boolean putNode(Node<K, V> newNode) {
        Node<K, V> first = table[newNode.getHash()];
        if (first == null) {
            table[newNode.getHash()] = newNode;
        }
        else {
            while (first.next != null) {
                // two absolute equivalent K, we need to update the V
                if (first.getK().equals(newNode.getK())) {
                    first.setV(newNode.getV());
                    return false;
                }
                first = first.next;
            }

            if (first.getK().equals(newNode.getK())) {
                first.setV(newNode.getV());
                return false;
            }
            else
                first.next = newNode;
        }
        return true;
    }

    public void print() {
        System.out.println(toString() + "\r\n");
    }
    
    public void remove(K k) {
        if (k == null)
            return;

    	int hash = k.hashCode() & (capacity - 1);
        Node<K, V> prev = table[hash];
        Node<K, V> cur = table[hash].next;

        if (prev.getK().equals(k)) {
            table[hash] = table[hash].next;
            size--;
            return;
        }
        while (cur != null) {
            if (cur.getK().equals(k)) {
                prev.next = cur.next;
                size--;
                return;
            }
            prev = cur;
            cur = cur.next;
        }
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	for (Node<K, V> node: table) {
            if (node != null) {
                do {
                    sb = sb.append("hashCode: " + node.getHash() + ", K: " + node.getK() + ", V: "
                            + node.getV() + "   ");
                } while ((node = node.next) != null);
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }
}

class Node<K, V> {
    Node<K, V> next;
    private K k;
    private V v;
    private int hash;

    public Node() {}

    public Node(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }
}
