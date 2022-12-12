package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class MyOval extends MyShape{

    private double height, width;
    private MyPoint center;
    private MyColor color;

    MyOval (MyPoint center,double width, double height, MyColor color) {
        super(new MyPoint(), null);
       setCenter(center);
       setWidth(width);
       setHeight(height);
       setColor(color);
    }

    //Getters
    public double getCenterX () {
        return center.getX();
    }
    public double getCenterY () { return center.getY(); }
    public MyPoint getCenter () { return center; }
    public double getWidth () { return width; }
    public double getHeight () { return height; }
    public double getRadiusX () { return width/2; }
    public double getRadiusY () { return height/2; }
    public MyColor getColor () {
        return color;
    }

    //Setters
    public void setCenter (MyPoint p) {
        center = p;
    }
    public void setHeight (double h) {
        height = h;
    }
    public void setWidth (double w) {
        width = w;
    }
    public void setColor (MyColor color) {
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }

    //Methods
    public double getA() { //returns radius of major axis
        return Math.max(width/2,height/2);
    }
    public double getB() { //returns radius of minor axis
        return Math.min(width/2,height/2);
    }
    @Override
    public double area () {
        return Math.PI * (width/2) * (height/2);
    }
    @Override
    public double perimeter () {
        return Math.PI * (3 * (width/2 + height/2) - Math.sqrt((3*width/2 + (height/2))*(width/2 + 3 * (height/2))));
    }


    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(color.getJavaFXColor());
        GC.strokeOval(getCenterX() - (width/2),getCenterY() - (height/2),width,height);
        GC.fillOval(getCenterX() - (width/2),getCenterY() - (height/2),width,height);
    }

    @Override
    public String toString () {
        return "This is MyOval Object with X-axis length: " + (int)width + " ,Y-axis length: " + (int)height + ",perimeter: " + (int)perimeter() + " ,area: " + (int)area();
    }

    @Override
    public MyRectangle getMyBoundingRectangle() {
        MyPoint topLeft = new MyPoint(center.getX() - width/2, center.getY() - height/2);
        MyColor new_color = MyColor.AQUA;
        new_color.setA(0);
        return new MyRectangle(topLeft,width + 2,height + 2, new_color);
    }

    @Override
    public boolean pointInMyShape(MyPoint p) {
        double xDist = Math.pow((p.getX() - getCenterX()),2);
        double yDist = Math.pow((p.getY()-getCenterY()),2);
        double result = xDist/Math.pow(getRadiusX(),2) + yDist/Math.pow(getRadiusY(),2);
        return result <= 1;
    }


}

