package com.example.sqlassignment;

public class MyCircle extends MyOval {
    private MyPoint center;
    private double radius;
    private MyColor color;
    MyCircle(MyPoint center, double radius, MyColor color) {

        super(center, radius*2, radius*2, color);
    }

    public double getRadius () {
        return getRadiusX();
    }

  @Override
    public double perimeter () {
        return Math.PI * getWidth();
  }
  @Override
    public String toString() {
        return "This is MyCircle Object with center: " + getCenter() +
                " radius: " + (int) getRadiusX() + " ,perimeter: "
                + (int)perimeter() + " ,area: " + (int)area();
  }

}
