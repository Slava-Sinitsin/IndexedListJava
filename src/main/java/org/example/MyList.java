package org.example;

import java.io.*;
import java.util.Vector;

public class MyList<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;
    private final Vector<Node<T>> vector;
    private final int n;

    public int getSize() {
        return size;
    }

    public MyList(int n) {
        this.head = null;
        this.size = 0;
        this.vector = new Vector<>();
        this.n = n;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;

        // Добавляем ссылку на каждый N-ый элемент списка в вектор
        if (size % n == 0) {
            vector.add(newNode);
        }

        updateVector();
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public void insert(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            head = newNode;
            size++;
            updateVector();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(data);
            newNode.next = current.next;
            current.next = newNode;
            size++;
            updateVector();
        }
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
        if (size % n == 0) {
            vector.remove(vector.size() - 1);
        }
        updateVector();
    }

    private void updateVector() {
        vector.clear();
        Node<T> current = head;
        int count = 1;
        while (current != null) {
            if (count % n == 0) {
                vector.add(current);
            }
            current = current.next;
            count++;
        }
    }

    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
        System.out.print("N = " + n + ": ");
        for (Node<T> node : vector) {
            System.out.print("[" + node.data + "] ");
        }
        System.out.println();
    }

    public void mergeSort() {
        head = mergeSort(head);
        updateVector();
    }

    private Node<T> mergeSort(Node<T> head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Находим середину списка
        Node<T> mid = findMiddle(head);

        // Разделяем список на две части
        Node<T> left = head;
        Node<T> right = mid.next;
        mid.next = null;

        // Рекурсивно сортируем обе части
        left = mergeSort(left);
        right = mergeSort(right);

        // Объединяем две отсортированные части
        return merge(left, right);
    }

    private Node<T> findMiddle(Node<T> head) {
        if (head == null) {
            return null;
        }
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    @SuppressWarnings("unchecked")
    private Node<T> merge(Node<T> left, Node<T> right) {
        Node<T> merged = new Node<>(null);
        Node<T> current = merged;

        while (left != null && right != null) {
            if (((Comparable<T>) left.data).compareTo(right.data) <= 0) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }

        if (left != null) {
            current.next = left;
        } else {
            current.next = right;
        }

        return merged.next;
    }


    // Определение интерфейса обратного вызова
    public interface Callback<T> {
        void toDo(T v);
    }

    // Метод forEach, который вызывает метод tоdo для каждого элемента
    public void forEach(Callback<T> callback) {
        Node<T> current = head;
        while (current != null) {
            callback.toDo(current.data);
            current = current.next;
        }
    }

    // Сериализация данных в файл в формате JSON
    @SuppressWarnings("CallToPrintStackTrace")
    public void serializeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"n\":").append(n).append(",");
            json.append("\"data\":[");
            Node<T> current = head;
            while (current != null) {
                if (current.data instanceof Point2D point) {
                    json.append("\"(").append(point.getX()).append(";").append(point.getY()).append(")\",");
                } else {
                    json.append("\"").append(current.data.toString()).append("\",");
                }
                current = current.next;
            }
            if (json.charAt(json.length() - 1) == ',') {
                json.setCharAt(json.length() - 1, ']');
            } else {
                json.append("]");
            }
            json.append("}");
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Десериализация данных из файла в формате JSON для различных типов
    @SuppressWarnings({"WrapperTypeMayBePrimitive", "unchecked", "CallToPrintStackTrace"})
    public static <E> MyList<E> deserializeFromFile(String filename, Class<E> elementType) {
        MyList<E> myList = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            // Парсим JSON
            String jsonString = json.toString();
            int index = jsonString.indexOf("\"n\":") + 4;
            int endIndex = jsonString.indexOf(",", index);
            int n = Integer.parseInt(jsonString.substring(index, endIndex));
            myList = new MyList<>(n);

            int dataStartIndex = jsonString.indexOf("\"data\":[") + 8;
            int dataEndIndex = jsonString.lastIndexOf("]");
            String dataString = jsonString.substring(dataStartIndex, dataEndIndex);
            String[] elements = dataString.split(",");
            for (String element : elements) {
                if (elementType == Integer.class) {
                    Integer value = Integer.parseInt(element.substring(1, element.length() - 1));
                    myList.add((E) value);
                } else if (elementType == Point2D.class) {
                    // Парсим 2D-точку
                    String[] coords = element.substring(2, element.length() - 2).split(";");
                    double x = Double.parseDouble(coords[0].trim());
                    double y = Double.parseDouble(coords[1].trim());
                    Point2D point = new Point2D(x, y);
                    myList.add((E) point);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myList;
    }
}