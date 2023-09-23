package org.example;

public class Point2D implements Comparable<Point2D> {
    private double x;
    private double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point2D other) {
        double distance1 = Math.sqrt(x + y);
        double distance2 = Math.sqrt(other.x + other.y);
        return Double.compare(distance1, distance2);
    }
}