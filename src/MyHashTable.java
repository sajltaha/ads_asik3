import java.util.LinkedList; // Using LinkedList for separate chaining

public class MyHashTable<K, V> {

    private class HashNode<K, V> {
        private K key;
        private V value;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{" + key + "=" + value + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            HashNode<?, ?> hashNode = (HashNode<?, ?>) obj;
            return key.equals(hashNode.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

    private LinkedList<HashNode<K, V>>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        this(11);
    }

    public MyHashTable(int M) {
        if (M <= 0) throw new IllegalArgumentException("Initial capacity must be positive");
        this.M = M;
        chainArray = (LinkedList<HashNode<K, V>>[]) new LinkedList[M];
        for (int i = 0; i < M; i++) {
            chainArray[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int index = hash(key);
        LinkedList<HashNode<K, V>> bucket = chainArray[index];
        for (HashNode<K, V> node : bucket) {
            if (node.getKey().equals(key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(new HashNode<>(key, value));
        size++;
    }

    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int index = hash(key);
        LinkedList<HashNode<K, V>> bucket = chainArray[index];
        for (HashNode<K, V> node : bucket) {
            if (node.getKey().equals(key)) return node.getValue();
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int index = hash(key);
        LinkedList<HashNode<K, V>> bucket = chainArray[index];
        HashNode<K, V> nodeToRemove = null;
        for (HashNode<K, V> node : bucket) {
            if (node.getKey().equals(key)) {
                nodeToRemove = node;
                break;
            }
        }
        if (nodeToRemove != null) {
            V removedValue = nodeToRemove.getValue();
            bucket.remove(nodeToRemove);
            size--;
            return removedValue;
        }
        return null;
    }

    public boolean contains(V value) {
        if (value == null) {
            for (int i = 0; i < M; i++) {
                for (HashNode<K, V> node : chainArray[i]) {
                    if (node.getValue() == null) return true;
                }
            }
        } else {
            for (int i = 0; i < M; i++) {
                for (HashNode<K, V> node : chainArray[i]) {
                    if (value.equals(node.getValue())) return true;
                }
            }
        }
        return false;
    }

    public K getKey(V value) {
        if (value == null) {
            for (int i = 0; i < M; i++) {
                for (HashNode<K, V> node : chainArray[i]) {
                    if (node.getValue() == null) return node.getKey();
                }
            }
        } else {
            for (int i = 0; i < M; i++) {
                for (HashNode<K, V> node : chainArray[i]) {
                    if (value.equals(node.getValue())) return node.getKey();
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return M;
    }

    public int getBucketSize(int index) {
        if (index < 0 || index >= M) throw new IndexOutOfBoundsException("Bucket index out of range");
        return chainArray[index].size();
    }
}
