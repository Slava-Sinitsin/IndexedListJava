package org.example;

import java.io.*;
import java.util.Objects;
import java.util.Vector;

public class MyList<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;
    private Node<T> tail; // Добавляем поле tail
    private int size;
    private final Vector<Node<T>> vector;

    public int getN() {
        return n;
    }

    private final int n;

    public int getSize() {
        return size;
    }

    public MyList(int n) {
        this.head = null;
        this.tail = null; // Инициализируем tail как null
        this.size = 0;
        this.vector = new Vector<>();
        this.n = n;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        // Обновляем tail на новый элемент
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode; // Если список пуст, tail указывает на новый элемент
        size++;
        if (size % n == 0) {
            vector.add(newNode);
        }
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

    @SuppressWarnings("DuplicatedCode")
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        if (index == 0) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            if (size == 1) {
                tail = null; // Если удаляем последний элемент, обновляем tail
            }
        } else {
            Node<T> current;
            if (vector.size() > 1 && index >= n) {
                current = vector.get(index / n - 1);
                for (int i = vector.size() * n - index; i < index - 1; i++) {
                    current = current.next;
                }
            } else {
                current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
            }
            current.next = current.next.next;
            if (current.next != null) {
                current.next.prev = current;
            }
            if (current.next == null) {
                tail = current; // Если удаляем последний элемент, обновляем tail
            }
        }
        size--;
        updateVector(index, "rem");
    }

    public void insert(int index, T data) {
        Node<T> newNode = new Node<>(data);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode; // Если список пуст, tail указывает на новый элемент
            }
        } else {
            Node<T> current;
            if (vector.size() > 1 && index >= n) {
                current = vector.get(index / n - 1);
                for (int i = n * (index / n); i < index; i++) {
                    current = current.next;
                }
            } else {
                current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
            }
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
            if (newNode.next == null) {
                tail = newNode; // Если новый элемент добавлен в конец списка, обновляем tail
            }
        }
        size++;
        updateVector(index, "add");
    }

    private void updateVector(int index, String op) {
        if (Objects.equals(op, "add")) {
            for (int i = index / n; i < vector.size(); i++) {
                vector.set(i, vector.get(i).prev);
            }
            if (size % n == 0) {
                vector.add(tail);
            }
        }
        if (Objects.equals(op, "rem")) {
            for (int i = index / n; i < vector.size(); ++i) {
                if (vector.get(i).next == null) {
                    vector.remove(i);
                    break;
                } else {
                    vector.set(i, vector.get(i).next);
                }
            }
        }
        if (Objects.equals(op, "sort")) {
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
            current = head;
            current.prev = null;
            while (current.next != null) {
                current.next.prev = current;
                current = current.next;
            }
            tail = current;
        }
    }

    public void display() {
        Node<T> current = head;
        System.out.print("null <- ");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            } else {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println("null");
        System.out.print("N = " + n + ": ");
        for (Node<T> node : vector) {
            System.out.print("[" + node.data + "] ");
        }
        System.out.println();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> current = head;
        builder.append("null <- ");
        while (current != null) {
            builder.append(current.data);
            if (current.next != null) {
                builder.append(" <-> ");
            } else {
                builder.append(" -> ");
            }
            current = current.next;
        }
        builder.append("null");
        return builder.toString();
    }

    public String vectorToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("N = ").append(n).append(": ");
        for (Node<T> node : vector) {
            builder.append("[").append(node.data).append("] ");
        }
        return builder.toString();
    }

    public void mergeSort() {
        head = mergeSort(head);
        tail = findTail(head);
        updateVector(0, "sort");
    }

    private Node<T> mergeSort(Node<T> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<T> middle = findMiddle(head);
        Node<T> secondHalf = middle.next;
        middle.next = null;

        Node<T> left = mergeSort(head);
        Node<T> right = mergeSort(secondHalf);

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

    private Node<T> merge(Node<T> left, Node<T> right) {
        Node<T> result;
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (compare(left.data, right.data) <= 0) {
            result = left;
            result.next = merge(left.next, right);
        } else {
            result = right;
            result.next = merge(left, right.next);
        }
        return result;
    }

    private Node<T> findTail(Node<T> head) {
        if (head == null) {
            return null;
        }

        while (head.next != null) {
            head = head.next;
        }

        return head;
    }


    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable<T>) a).compareTo(b);
        } else {
            throw new IllegalArgumentException("Elements must implement Comparable interface.");
        }
    }

    public interface Callback<T> {
        void toDo(T v);
    }

    public void forEach(Callback<T> callback) {
        Node<T> current = head;
        while (current != null) {
            callback.toDo(current.data);
            current = current.next;
        }
    }

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

    @SuppressWarnings({"WrapperTypeMayBePrimitive", "unchecked", "CallToPrintStackTrace"})
    public static <E> MyList<E> deserializeFromFile(String filename, Class<E> elementType) {
        MyList<E> myList = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

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
