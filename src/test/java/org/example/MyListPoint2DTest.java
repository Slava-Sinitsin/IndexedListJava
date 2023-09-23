package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Vector;

public class MyListPoint2DTest {
    @Test
    void AddPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < 10; ++i) {
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
        for (double i = 0; i < 10; ++i) {
            myList.add(new Point2D(i, i));
        }
        Assertions.assertEquals(5, myList.get(5).getX());
        Assertions.assertEquals(5, myList.get(5).getY());
        myList.display();
    }

    @Test
    void IndexAddPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < 10; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.insert(5, new Point2D(7.0, 8.0));
        Assertions.assertEquals(7, myList.get(5).getX());
        Assertions.assertEquals(8, myList.get(5).getY());
        myList.display();
    }

    @Test
    void removeInt2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (double i = 0; i < 10; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.remove(5);
        Assertions.assertEquals(9, myList.getSize());
        Assertions.assertNotEquals(4, myList.get(5).getX());
        Assertions.assertNotEquals(4, myList.get(5).getY());
        myList.display();
    }

    @Test
    void ForEachPoint2D() {
        MyList<Point2D> myList = new MyList<>(5);
        Vector<Point2D> vector = new Vector<>();
        for (int i = 0; i < 10; ++i) {
            myList.add(new Point2D(i, i));
        }
        MyList.Callback<Point2D> myCallback = v -> {
            v.setX(v.getX() * 2);
            v.setY(v.getY() * 2);
            vector.add(v);
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
        myList.add(new Point2D(6.0, 6.0));
        myList.add(new Point2D(7.0, 7.0));
        myList.add(new Point2D(8.0, 8.0));
        myList.add(new Point2D(9.0, 9.0));
        myList.add(new Point2D(3.0, 3.0));
        myList.add(new Point2D(1.0, 1.0));
        myList.add(new Point2D(0.0, 0.0));
        myList.add(new Point2D(2.0, 2.0));
        myList.add(new Point2D(4.0, 4.0));
        myList.add(new Point2D(5.0, 5.0));
        myList.display();
        myList.mergeSort();
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(i, myList.get(i).getX());
            Assertions.assertEquals(i, myList.get(i).getY());
        }
        myList.display();
    }

    @Test
    void SerialInt2D() {
        MyList<Point2D> myList = new MyList<>(5);
        for (int i = 0; i < 10; ++i) {
            myList.add(new Point2D(i, i));
        }
        myList.serializeToFile("pointList.json");
        MyList<Point2D> desMyList = MyList.deserializeFromFile("pointList.json", Point2D.class);
        for (int i = 0; i < myList.getSize(); ++i) {
            Assertions.assertEquals(myList.get(i).getX(), desMyList.get(i).getX());
            Assertions.assertEquals(myList.get(i).getY(), desMyList.get(i).getY());
        }
    }
}