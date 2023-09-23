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
            newNode.prev = current;
        }
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

    @SuppressWarnings({"ReassignedVariable", "DuplicatedCode"})
    public void insert(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            size++;
            updateVector(index, "add");
        } else {
            Node<T> current;
            if (vector.size() > 1) {
                current = vector.get(index / (vector.size() * n));
                for (int i = vector.size() * n; i < index - 1; i++) {
                    current = current.next;
                }
            } else {
                current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
            }
            Node<T> newNode = new Node<>(data);
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
            size++;
            updateVector(index, "add");
        }
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
        }
        size--;
        updateVector(index, "rem");
    }

    private void updateVector(int index, String op) {
        if (Objects.equals(op, "add")) {
            for (int i = index / n; i < vector.size(); ++i) {
                vector.set(i, vector.get(i).prev);
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


    public void mergeSort() {
        head = mergeSort(head);
        updateVector(0, "sort");
    }

    private Node<T> mergeSort(Node<T> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<T> mid = findMiddle(head);
        Node<T> left = head;
        Node<T> right = mid.next;
        mid.next = null;

        left = mergeSort(left);
        right = mergeSort(right);

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
                left.prev = current;
                left = left.next;
            } else {
                current.next = right;
                right.prev = current;
                right = right.next;
            }
            current = current.next;
        }

        if (left != null) {
            current.next = left;
            left.prev = current;
        } else {
            current.next = right;
            right.prev = current;
        }

        return merged.next;
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
