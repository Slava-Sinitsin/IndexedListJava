package org.example;

public class Main {
    public static void main(String[] args) {
        MyList<Integer> myList = new MyList<>(5);
        for (int i = 0; i < 10; i++) {
            myList.add(i);
        }
        myList.forEach(v -> System.out.print(v * 2 + " "));
    }
}