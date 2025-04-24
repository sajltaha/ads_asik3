import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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

    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node x, K key, V value) {
        if (x == null) return new Node(key, value);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = insert(x.left, key, value);
        else if (cmp > 0) x.right = insert(x.right, key, value);
        else x.value = value;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
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
        Queue<KeyValuePair<K, V>> queue = new LinkedList<>();
        inOrder(root, queue);
        return queue.iterator();
    }
}
