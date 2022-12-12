package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class MyPoint {
    private double x,y;
    private MyColor color;

    //Constructors
    MyPoint() {
        this.x = 0;
        this.y = 0;
        this.color = MyColor.CADETBLUE;
    }
    MyPoint (double x, double y) {
        this.x = x;
        this.y = y;
        this.color = MyColor.CADETBLUE;
    }

    MyPoint(double x, double y, MyColor color) {
        this.x = x;
        this.y = y;
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }

    MyPoint (MyPoint p, MyColor color) {
        this.x = p.getX();
        this.y = p.getY();
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }

    //Setters
    public void setColor (MyColor color) {this.color = color;}
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint (MyPoint p) {
        this.x = p.getX();
        this.y = p.getY();
    }


    //Getters
    public double getX () {
        return x;
    }
    public double getY () {
        return y;
    }
    public MyPoint getPoint () {
        return new MyPoint(x,y,null);
    }
    public MyColor getColor() {
        return this.color;
    }

    //Methods
    public void shiftX (double dx) { //Move the x-cord by dx amount
        setX(x + dx);
    }
    public void shiftY (double dy) { //Move the y-cord by dy amount
        setY(y + dy);
    }
    public void shiftPoint (double dx, double dy) {
        setPoint(x + dx, y + dy);
    }
    public double distanceFromOrigin () { //Distance of point to Top Left Corner
        return Math.sqrt(x*x + y*y);
    }
    public double distanceFromOtherPoint (MyPoint p) {
        double dx = x - p.getX();
        double dy = y - p.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    public double angleInDegrees (MyPoint p) {
        double dx = x - p.getX();
        double dy = y - p.getY();
        return Math.toDegrees(Math.atan2(dy,dx));
    }

    @Override
    public String toString() {
        return "Point p(" + (int)x + ", " + (int)y +")";
    }
    public void draw (GraphicsContext gc) {
        gc.setFill(color.getJavaFXColor());
        gc.fillRect(x,y,1,1);
    }
}
