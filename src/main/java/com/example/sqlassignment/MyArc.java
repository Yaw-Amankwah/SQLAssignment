package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

import java.util.Optional;


public class MyArc extends MyShape {

    private MyOval oval;
    private double startAngle;
    private MyPoint p1,p2;
    private double extent;
    private double endAngle;
    private MyColor color;
    private ArcType arcType;
    private double rStartAngle;
    private double rExtent;
    private double rEndAngle;


    MyArc(MyOval oval, double startAngle, double extent, ArcType arcType) { //FIND POINTS FROM ANGLES
        super(new MyPoint(), null);
        this.oval = oval;
        this.startAngle = startAngle;
        this.rStartAngle = Math.toRadians(startAngle);
        this.extent = extent;
        this.rExtent = Math.toRadians(extent);
        this.endAngle = startAngle + extent;
        this.rEndAngle = Math.toRadians(endAngle);
        this.color = oval.getColor();
        this.arcType = Optional.ofNullable(arcType).orElse(ArcType.ROUND);

    }

    MyArc (MyOval oval, MyPoint p1, MyPoint p2, ArcType arcType) { //FIND ANGLES FROM POINTS
        super(new MyPoint(), null);
        this.oval = oval;

        this.color = oval.getColor();
        this.arcType = Optional.ofNullable(arcType).orElse(ArcType.ROUND);
    }

    MyArc (MyArc A) { //Copy Constructor copies ALL attributes of MyArc A.
        super (new MyPoint(),null);
        this.oval = A.getOval();
        this.startAngle = A.getStartAngle();
        this.extent = A.getExtent();
        this.rStartAngle = Math.toRadians(A.getStartAngle());
        this.rExtent = Math.toRadians(A.getExtent());
        this.endAngle = A.getStartAngle()+A.getExtent();
        this.rEndAngle = Math.toRadians(A.getStartAngle()+A.getExtent());
        this.p1 = A.getP1();
        this.p2 = A.getP2();
        this.color = A.getColor();
        this.arcType = A.getArcType();
    }

    //SETTERS
    public void setColor(MyColor color) {
        this.color = color;
    }
    public void setOval(MyOval oval) {
        this.oval = oval;
    }
    public void setStartAngle (double startAngle) {
        this.startAngle = startAngle;
    }
    public void setExtent (double extent) {
        this.extent = extent;
    }
    public void setP1 (MyPoint p1) { this.p1 = p1;}
    public void setP2 (MyPoint p2) {
        this.p2 = p2;
    }
    public void setArcType (ArcType arcType) { this.arcType = arcType; }

    //GETTERS
    public MyOval getOval() { return oval;}
    public double getStartAngle () { return startAngle; }
    public double getRStartAngle () { return rStartAngle; }

    public double getExtent () { return extent; }
    public double getRExtent () { return rExtent; }
    public MyPoint getP1() {  return p1;  }
    public MyPoint getP2() { return p2; }
    public ArcType getArcType() {return arcType;}


    //METHODS
    public double arcLength() {
        return rExtent * Math.sqrt(0.5 * (double) (oval.getA()*oval.getA() + oval.getB()*oval.getB()));
    }
    public double area_theta(double theta) { //theta = startAngle + extent
        double a = oval.getA();
        double b = oval.getB();
        double atan_num = (b-a)*Math.sin(2*theta);
        double atan_den = b + a + (b-a) * Math.cos(2*theta);
        double ans = (0.5 * a * b) * (theta - Math.atan(atan_num/atan_den));
        return ans;
    }

    @Override
    public double area() {

        if (arcType == ArcType.OPEN) { //Area = Arclength * 1 pixel
            return arcLength();
        }
        else if (arcType == ArcType.ROUND) {
            return area_theta(startAngle + extent) - area_theta(startAngle);
        }
        else {
            return 0.5 * oval.getA() * oval.getB() * (extent - Math.sin(extent));
        }
    }

    @Override
    public double perimeter() { //Assume thickness of arc is 1 pixel, and perimeter goes around arc
        double per = 0;
        switch (arcType) {
            case OPEN:
               per = arcLength() * 2 + 2; //Assume thickness of arc is 1 pixel, and perimeter goes around arc
               break;
            case CHORD:
                per = arcLength() + p1.distanceFromOtherPoint(p2); // Arclength + distance between endpoints
            case ROUND: //Arclength + distance from center to either endpoint
                per = arcLength() + oval.getCenter().distanceFromOtherPoint(p1) + oval.getCenter().distanceFromOtherPoint(p2);
        }
        return per;
    }

    @Override
    public void draw(GraphicsContext GC) {
        double c_X = oval.getCenterX();
        double c_Y = oval.getCenterY();
        double w = oval.getWidth();
        double h = oval.getHeight();
        GC.setFill(color.getJavaFXColor());

        if (arcType == ArcType.OPEN) {
            GC.setStroke(color.getJavaFXColor());
            GC.strokeArc(c_X - w/2,c_Y - h/2,w,h,startAngle,extent, arcType);
            return;}

        GC.strokeArc(c_X - w/2,c_Y - h/2,w,h,startAngle,extent, arcType);
        GC.fillArc(c_X - w/2,c_Y - h/2,w,h,startAngle,extent, arcType);

    }
    @Override
    public String toString() {

        return "this is an arc with center: " + oval.getCenter() +
                " starting angle: " + (int) startAngle +
                " ending angle: " + (int) (startAngle + extent) +
                " endpoints: " + p1 + " " + p2 + " arc type: " + arcType +
                " area: " + (int) area() + " perimeter: " + (int) perimeter() + " arc length: " + (int)arcLength();
    }

    @Override
    public MyRectangle getMyBoundingRectangle() {

        double topLeftX = 0, topLeftY = 0,width = 0, height = 0;
        double st_angle = this.startAngle %360;
        double ext = this.extent;
        double sum = st_angle + ext;
        double centerX = oval.getCenterX();
        double centerY = oval.getCenterY();
        double o_width = oval.getWidth();
        double o_height = oval.getHeight();

        if (st_angle < 90) {
            if (sum <= 90) {
                topLeftX = centerX;
                topLeftY = centerY - o_height/2;
                width = o_width/2;
                height = o_height/2;
            }
            else if (sum <=180) {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width;
                height = o_height/2;
            }
            else {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width;
                height = o_height;
            }
            }
        else if (st_angle < 180) {
            if (sum <=180) {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width/2;
                height = o_height/2;
            }
            else if (sum <=270) {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width/2;
                height = o_height;
            }
            else {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width;
                height = o_height;
            }
        } else if (st_angle < 270) {
            if (sum <= 270) {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY;
                width = o_width/2;
                height = o_height/2;
            }
            else if (sum <=360) {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY;
                width = o_width;
                height = o_height/2;
            }
            else {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width;
                height = o_height;
            }
        }
        else {
            if (sum <= 360) {
                topLeftX = centerX;
                topLeftY = centerY;
                width = o_width/2;
                height = o_height/2;
            }
            else if (sum %360 <= 90) {
                topLeftX = centerX;
                topLeftY = centerY - o_height/2;
                width = o_width/2;
                height = o_height;
            }
            else {
                topLeftX = centerX - o_width/2;
                topLeftY = centerY - o_height/2;
                width = o_width;
                height = o_height;
            }
        }
        MyPoint topLeft = new MyPoint(topLeftX,topLeftY);
        MyColor new_color = MyColor.AQUA;
        new_color.setA(0);

       return new MyRectangle(topLeft,width,height,new_color);
    }

    @Override
    public boolean pointInMyShape(MyPoint p) {
        MyPoint center = oval.getCenter();
        double p_angle = center.angleInDegrees(p);
        return ((startAngle + extent) >= p_angle) && (p_angle >= startAngle) && oval.pointInMyShape(p);

    }

}
