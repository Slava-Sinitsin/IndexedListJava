package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Vector;

public class MyListIntTest {
    @Test
    void AddInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i, myList.get(i));
        }
        myList.display();
    }

    @Test
    void GetInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        Assertions.assertEquals(5, myList.get(5));
        myList.display();
    }

    @Test
    void IndexAddInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        myList.insert(5, 7);
        Assertions.assertEquals(7, myList.get(5));
        myList.display();
    }

    @Test
    void removeInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        myList.remove(5);
        Assertions.assertEquals(9, myList.getSize());
        Assertions.assertNotEquals(4, myList.get(5));
        myList.display();
    }

    @Test
    void ForEachInt() {
        MyList<Integer> myList = new MyList<>(5);
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        MyList.Callback<Integer> myCallback = v -> {
            v = v * 2;
            vector.add(v);
        };
        myList.forEach(myCallback);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i * 2, vector.get(i));
            System.out.print(vector.get(i) + " ");
        }
    }

    @Test
    void SortInt() {
        MyList<Integer> myList = new MyList<>(5);
        myList.add(1);
        myList.add(7);
        myList.add(8);
        myList.add(5);
        myList.add(2);
        myList.add(0);
        myList.add(6);
        myList.add(3);
        myList.add(4);
        myList.add(9);
        myList.display();
        myList.mergeSort();
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i, myList.get(i));
        }
        myList.display();
    }

    @Test
    void SerialInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(i);
        }
        myList.serializeToFile("intList.json");
        MyList<Integer> desMyList = MyList.deserializeFromFile("intList.json", Integer.class);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(myList.get(i), desMyList.get(i));
        }
    }
}