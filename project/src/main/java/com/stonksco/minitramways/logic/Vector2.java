package com.stonksco.minitramways.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Vector2 {

    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x=x;
        this.y=y;
    }

    public Vector2(int x, int y) {
        this.x= x;
        this.y= y;
    }


    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 div(Vector2 v) {
        return new Vector2(x/v.getX(),y/v.getY());
    }

    public Vector2 scale(Vector2 v) {
        return new Vector2(x*v.getX(),y*v.getY());
    }

    public Vector2 scale(double s) {
        return new Vector2(x*s,y*s);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x-v.getX(),y-v.getY());
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(x+v.getX(),y+v.getY());
    }

    public Vector2 normalize() {
        return new Vector2(x/length(),y/length());
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    }

    public double scalar(Vector2 v) {
        return x*v.getX()+y*v.getY();
    }

    public Vector2 clone() {
        return new Vector2(x,y);
    }

    @Override
    public String toString() {
        BigDecimal xFormatted = BigDecimal.valueOf(x);
        BigDecimal yFormatted = BigDecimal.valueOf(y);
        xFormatted.setScale(3, RoundingMode.HALF_UP);
        yFormatted.setScale(3, RoundingMode.HALF_UP);
        xFormatted.stripTrailingZeros();
        yFormatted.stripTrailingZeros();

        return "( "+ xFormatted +" , "+ yFormatted +" )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Double.compare(vector2.getX(), getX()) == 0 && Double.compare(vector2.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    /**
     * Retourne le point d'intersection entre deux segments
     * @param start1 Premier point du premier segment
     * @param end1 Deuxième point du deuxième segment
     * @param start2 Premier point du deuxième segment
     * @param end2 Deuxième point du deuxième segment
     * @return Point d'intersection entre les segments, ou null si les segments ne se croisent pas
     */
    public static Vector2 getIntersectionOf(Vector2 start1, Vector2 end1, Vector2 start2, Vector2 end2) {
        Vector2 res = null;
        double x1 = start1.getX();
        double x2 = end1.getX();
        double x3 = start2.getX();
        double x4 = end2.getX();
        double y1 = start1.getY();
        double y2 = end1.getY();
        double y3 = start2.getY();
        double y4 = end2.getY();

        double a1 = (y2-y1)/(x2-x1);
        double b1 = y1-(a1*x1);
        double a2 = (y4-y3)/(x4-x3);
        double b2 = y3 - a2*x3;

        // Si les deux segments ne sont pas parallèles
        if(a1!=a2) {
            double x5 = -(b1-b2)/(a1-a2);

            if((Math.min(x1,x2) < x5) && (x5 < Math.max(x1,x2)) && ((Math.min(x3,x4) < x5) && (x5 < Math.max(x3,x5)))) {
                double y5 = a1 * x5 + b1;
                res = new Vector2(x5,y5);
            }
        }
        return res;
    }
}
