package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class MyRectangle extends MyShape {
    private MyPoint topLeft;
    private double width , height;
    private MyColor color;

    MyRectangle (MyPoint p, double w, double h, MyColor color) {
        super(new MyPoint(), null);
        setTopLeft(p);
        setWidth(w);
        setHeight(h);
        this.color = Optional.ofNullable(color).orElse(MyColor.CADETBLUE);
    }


    //Getters
    public MyPoint getTopLeft () {
        return topLeft;
    }
    public double getWidth () {
        return width;
    }
    public double getHeight () {
        return height;
    }
    public MyColor getColor () {
        return color;
    }

    //Setters
    public void setTopLeft(MyPoint p) {
        topLeft = p;
    }
    public void setWidth (double w) {
        width = w;
    }
    public void setHeight (double h) {
        height = h;
    }
    public void setColor (MyColor color) {
        this.color = color;
    }

    @Override
    public double area () {
        return width * height;
    }
    @Override
    public double perimeter () {
        return 2 * (width + height);
    }



    @Override
    public void draw(GraphicsContext GC) {

        GC.strokeRect(topLeft.getX(),topLeft.getY(),width,height);
        GC.setFill(color.getJavaFXColor());
        GC.fillRect(topLeft.getX(),topLeft.getY(),width,height);
    }

   @Override
    public String toString () {
        return "This is MyRectangle Object with Top Left " + topLeft + " ,width: " + (int)width + " ,height: " + (int)height + " ,perimeter: " + (int)perimeter() + ",and area: " + (int)area();
    }

    @Override
    public MyRectangle getMyBoundingRectangle() {
        MyColor new_color = MyColor.AQUA;
        new_color.setA(0);
        return new MyRectangle(topLeft,width,height,new_color);
    }

    @Override
    public boolean pointInMyShape(MyPoint p) {
        int pX = (int) p.getX();
        int pY = (int) p.getY();
        int xR = (int) topLeft.getX();
        int yR = (int) topLeft.getY();
        return (xR <= pX && pX <= xR + width) && (yR <= pY && pY <= yR + height);
    }

}
