import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<KeyValuePair<K, V>> {

    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size = 0;

    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node x, K key, V value) {
        if (x == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = insert(x.left, key, value);
        else if (cmp > 0) x.right = insert(x.right, key, value);
        else x.value = value;
        return x;
    }

    public V get(K key) {
        Node x = get(root, key);
        if (x == null) return null;
        return x.value;
    }

    private Node get(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) {
                size--;
                return x.left;
            }
            if (x.left == null) {
                size--;
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
            size--;
        }
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        size--;
        return x;
    }


    private void inOrder(Node x, Queue<KeyValuePair<K, V>> queue) {
        if (x == null) return;
        inOrder(x.left, queue);
        queue.add(new KeyValuePair<>(x.key, x.value));
        inOrder(x.right, queue);
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<KeyValuePair<K, V>> {
        private Queue<KeyValuePair<K, V>> queue;

        public BSTIterator() {
            queue = new LinkedList<>();
            inOrder(root, queue);
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public KeyValuePair<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue.remove();
        }
    }

    public int size() {
        return size;
    }
}