import java.util.Random;

class MyTestingClass {
    private int id;
    private String data;

    public MyTestingClass(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() { return id; }
    public String getData() { return data; }

    @Override
    public int hashCode() {
        int hash = 17;
        final int prime = 31;
        hash = prime * hash + id;
        if (data != null) {
            for (int i = 0; i < data.length(); i++) {
                hash = prime * hash + data.charAt(i);
            }
        }
        return hash & 0x7fffffff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyTestingClass that = (MyTestingClass) obj;
        return id == that.id && (data == null ? that.data == null : data.equals(that.data));
    }

    @Override
    public String toString() {
        return "MyTestingClass{" + "id=" + id + ", data='" + data + '\'' + '}';
    }
}

class Student {
    private String studentId;
    private String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId='" + studentId + '\'' + ", name='" + name + '\'' + '}';
    }
}

public class HashTableTest {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>(11);
        Random random = new Random();
        final int NUM_ELEMENTS = 10000;

        System.out.println("Adding " + NUM_ELEMENTS + " elements...");
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            int randomId = random.nextInt(100000);
            String randomData = "Data" + random.nextInt(5000);
            MyTestingClass key = new MyTestingClass(randomId, randomData);
            Student value = new Student("S" + randomId, "StudentName" + i);
            table.put(key, value);
        }

        System.out.println("Finished adding elements. Table size: " + table.size());

        System.out.println("\nBucket Distribution:");
        int totalElementsChecked = 0;
        for (int i = 0; i < table.getCapacity(); i++) {
            int bucketSize = table.getBucketSize(i);
            System.out.println("Bucket " + i + ": " + bucketSize + " elements");
            totalElementsChecked += bucketSize;
        }

        System.out.println("Total elements across buckets: " + totalElementsChecked);
        System.out.println("Expected elements: " + table.size());
    }
}
