package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;


public abstract class MyShape extends Object implements MyShapeInterface {

    private MyPoint p; //Reference point
    private MyColor color; //Shape color

    //Constructors

    MyShape (MyPoint p, MyColor color) {
        setPoint(p);
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }

    MyShape (double x, double y, MyColor color) {
        setPoint(x,y);
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }

    //Setters
    public void setPoint (MyPoint p) {
        this.p = p;
    }
    public void setPoint (double x, double y) {
        this.p.setX(x);
        this.p.setY(y);
    }

    //Getters
    public MyPoint getPoint() {
        return p;
    }
    public double getX() {
        return p.getX();
    }
    public double getY() {
        return p.getY();
    }
    public MyColor getColor() {
        return color;
    }

    //Methods
    public abstract double area();

    public abstract double perimeter();

    public abstract void draw (GraphicsContext GC);

    @Override
    public String toString() {
        return "This is MyShape Object with reference point " + p + " and color " + color;
    }


}
