package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Vector;

public class MyListTest {
    int N = 1000;

    @Test
    void AddInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < N; ++i) {
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
        for (int i = 0; i < N; ++i) {
            myList.add(i);
        }
        Assertions.assertEquals(5, myList.get(5));
        myList.display();
    }

    @Test
    void IndexAddInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < N; ++i) {
            myList.add(i);
        }
        myList.insert(5, 7);
        Assertions.assertEquals(7, myList.get(5));
        myList.display();
    }

    @Test
    void removeInt() {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < N; ++i) {
            myList.add(i);
        }
        myList.remove(5);
        for (int i = 0; i < 5; ++i) {
            Assertions.assertEquals(i, myList.get(i));
        }
        for (int i = 6; i < N - 1; ++i) {
            Assertions.assertEquals(i + 1, myList.get(i));
        }
        Assertions.assertEquals(N - 1, myList.getSize());
        myList.display();
    }

    @Test
    void ForEachInt() {
        MyList<Integer> myList = new MyList<>(5);
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < N; ++i) {
            myList.add(i);
        }
        MyList.Callback<Integer> myCallback = v -> {
            v = v * 2;
            vector.add(v);
            return v;
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
        for (int i = 0; i < N; ++i) {
            int randomNum = new Random().nextInt(myList.getSize() + 1);
            myList.insert(randomNum, i);
        }
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
        for (int i = 0; i < N; ++i) {
            myList.add(i);
        }
        myList.serializeToFile("IntegerList.json");
        MyList<Integer> desMyList = MyList.deserializeFromFile("IntegerList.json", Integer.class);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(myList.get(i), desMyList.get(i));
        }
    }

    @Test
    void AddPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i, myList.get(i).getX());
            Assertions.assertEquals(i, myList.get(i).getY());
        }
        myList.display();
    }

    @Test
    void GetPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        Assertions.assertEquals(5, myList.get(5).getX());
        Assertions.assertEquals(5, myList.get(5).getY());
        myList.display();
    }

    @Test
    void IndexAddPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.insert(5, new Point2D(7.0, 8.0));
        Assertions.assertEquals(7, myList.get(5).getX());
        Assertions.assertEquals(8, myList.get(5).getY());
        myList.display();
    }

    @Test
    void removePoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.remove(5);
        for (int i = 0; i < 5; ++i) {
            Assertions.assertEquals(i, myList.get(i).getX());
            Assertions.assertEquals(i, myList.get(i).getY());
        }
        for (int i = 6; i < N - 1; ++i) {
            Assertions.assertEquals(i + 1, myList.get(i).getX());
            Assertions.assertEquals(i + 1, myList.get(i).getY());
        }
        myList.display();
    }

    @Test
    void ForEachPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        Vector<Point2D> vector = new Vector<>();
        for (int i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        MyList.Callback<Point2D> myCallback = v -> {
            v.setX(v.getX() * 2);
            v.setY(v.getY() * 2);
            vector.add(v);
            return v;
        };
        myList.forEach(myCallback);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i * 2, vector.get(i).getX());
            Assertions.assertEquals(i * 2, vector.get(i).getY());
            System.out.print(vector.get(i) + " ");
        }
    }

    @Test
    void SortPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (int i = 0; i < N; ++i) {
            int randomNum = new Random().nextInt(myList.getSize() + 1);
            myList.insert(randomNum, new Point2D(i, i));
        }
        myList.display();
        myList.mergeSort();
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i, myList.get(i).getX());
            Assertions.assertEquals(i, myList.get(i).getY());
        }
        myList.display();
    }

    @Test
    void SerialPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (int i = 0; i < N; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.serializeToFile("Point2DList.json");
        MyList<Point2D> desMyList = MyList.deserializeFromFile("Point2DList.json", Point2D.class);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(myList.get(i).getX(), desMyList.get(i).getX());
            Assertions.assertEquals(myList.get(i).getY(), desMyList.get(i).getY());
        }
    }
}