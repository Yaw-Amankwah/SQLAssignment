package com.example.sqlassignment;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface MyShapeInterface {
    abstract MyRectangle getMyBoundingRectangle();

    abstract boolean pointInMyShape(MyPoint p);

    static boolean SimilarObjects(MyShape S1, MyShape S2) { // Doesn't take into account the color of the shape
        if (S1.getClass().equals(S2.getClass())) {
            if (S1 instanceof MyRectangle) {
                return (((MyRectangle) S1).getTopLeft() == ((MyRectangle) S2).getTopLeft()) &&
                        (((MyRectangle) S1).getWidth() == ((MyRectangle) S2).getWidth()) &&
                        (((MyRectangle) S1).getHeight() == ((MyRectangle) S2).getHeight());
            }
            else if (S1 instanceof MyCircle) {
                return (((MyCircle) S1).getCenter() == ((MyCircle) S2).getCenter()) &&
                        (((MyCircle) S1).getRadius() == ((MyCircle) S2).getRadius());
            }
            else if (S1 instanceof MyOval) {
                return ((MyOval) S1).getCenter() == ((MyOval) S2).getCenter() &&
                        ((MyOval) S1).getHeight() == ((MyOval) S2).getHeight() &&
                        ((MyOval) S1).getWidth() == ((MyOval) S2).getWidth();
            }
            else if (S1 instanceof MyArc) {
                return ((MyArc) S1).getOval() == ((MyArc) S2).getOval() &&
                        ((((MyArc) S1).getP1() == ((MyArc) S2).getP1() &&
                         ((MyArc) S1).getP2() == ((MyArc) S2).getP2()) ||
                        (((MyArc) S1).getP1() == ((MyArc) S2).getP2() &&
                        ((MyArc) S1).getP2() == ((MyArc) S2).getP1()));
            }
        }
        return false;
    }

    static MyRectangle intersectMyRectangles(MyRectangle R1, MyRectangle R2) { //WORKS
        double x1 = R1.getTopLeft().getX();
        double y1 = R1.getTopLeft().getY();
        double w1 = R1.getWidth();
        double h1 = R1.getHeight();

        double x2 = R2.getTopLeft().getX();
        double y2 = R2.getTopLeft().getY();
        double w2 = R2.getWidth();
        double h2 = R2.getHeight();

        if (y1 > y2 + h2 || y2 > y1 + h1) {
            return null;
        } // One rectangle above another
        if (x1 > x2 + w2 || x2 > x1 + w1) {
            return null;
        } // one rectangle to the side of other

        double topLeftX = Math.max(x1, x2);
        double topLeftY = Math.max(y1, y2);
        MyPoint topLeft = new MyPoint(topLeftX, topLeftY);

        double min_width = Math.min(x1 + w1, x2 + w2);
        double min_height = Math.min(y1 + h1, y2 + h2);

        double width = Math.abs(topLeftX - min_width);
        double height = Math.abs(topLeftY - min_height);
        return new MyRectangle(topLeft, width, height, MyColor.YELLOW);
    }

    static List<MyPoint> intersectMyShapes(MyShape S1, MyShape S2) {

        MyRectangle R1 = S1.getMyBoundingRectangle();
        MyRectangle R2 = S2.getMyBoundingRectangle();
        MyRectangle intersect = intersectMyRectangles(R1, R2);

        if (intersect == null) {
            return null;
        }
        int x_it = (int) intersect.getTopLeft().getX();
        int y_it = (int) intersect.getTopLeft().getY();
        int w_it = (int) intersect.getWidth();
        int h_it = (int) intersect.getHeight();
        List<MyPoint> intersection = new ArrayList<>();
        for (int i = x_it; i < x_it + w_it; i++) {
            for (int j = y_it; j < y_it + h_it; j++) {
                MyPoint p = new MyPoint(i, j);
                if (S1.pointInMyShape(p) && S2.pointInMyShape(p)) {
                    intersection.add(p);
                }
            }
        }
        return intersection;
    }

    default Canvas drawIntersectMyShapes(MyShape S) {

        int w = (int) Math.max(this.getMyBoundingRectangle().getTopLeft().getX() + this.getMyBoundingRectangle().getWidth(),
                S.getMyBoundingRectangle().getTopLeft().getX() + S.getMyBoundingRectangle().getWidth());
        int h = (int) Math.max(this.getMyBoundingRectangle().getTopLeft().getY() + this.getMyBoundingRectangle().getHeight(),
                S.getMyBoundingRectangle().getTopLeft().getY() + S.getMyBoundingRectangle().getHeight());
        List<MyPoint> intersect = intersectMyShapes((MyShape) this, S);
        Canvas CV = new Canvas(w + 10,h + 10);
        if (intersect != null) {
            GraphicsContext GC = CV.getGraphicsContext2D();
            GC.setLineWidth(5);
            S.draw(GC);
            ((MyShape) this).draw(GC);

            for (MyPoint p : intersect) {
                new MyPoint();
                p.setColor(MyColor.YELLOW);
                p.draw(GC);
            }

        }
        return CV;
    }
}



