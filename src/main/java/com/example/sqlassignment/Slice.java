package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Slice {
   private MyArc slice;
   private MyPoint center;
   private int radius;
   private double startAngle, extent, endAngle;
   private double rStartAngle, rExtent,rEndAngle;
   private MyColor color;

   Slice(MyPoint center, int radius, double startAngle, double extent, MyColor color) {
      this.center = center;
      this.radius = radius;
      this.startAngle = startAngle;
      this.extent = extent;
      this.color = color;
      this.endAngle =  startAngle + extent;

      this.rStartAngle = Math.toRadians(startAngle);
      this.rExtent = Math.toRadians(extent);
      this.rEndAngle = Math.toRadians(endAngle);

      this.slice = new MyArc((new MyOval(center,radius * 2,radius * 2,color)), startAngle,extent, ArcType.ROUND);
   }

   Slice (Slice s) {
      this.center = s.getCenter();
      this.radius = s.getRadius();
      this.startAngle = s.getStartAngle();
      this.extent = s.getExtent();
      this.color = s.getColor();
      this.endAngle = s.getEndAngle();

      this.rStartAngle = Math.toRadians(startAngle);
      this.rExtent = Math.toRadians(extent);
      this.rEndAngle = Math.toRadians(endAngle);
      this.slice = s.slice;
   }

  //GETTERS
   public MyPoint getCenter() { return center; }
   public int getRadius() { return radius; }

   public double getStartAngle() { return startAngle; }

   public double getExtent() { return extent; }

   public double getEndAngle() { return endAngle; }

   public MyColor getColor() { return color; }

   //METHODS
    @Override
   public String toString() {
       return "Slice with center: " + this.getCenter() +
               " radius: " + this.getRadius() +
                " starting angle: " + (int) startAngle +
                " ending angle: " + (int) (startAngle + extent);
    }
   public void draw (GraphicsContext gc) {
       slice.draw(gc);
   }
}
