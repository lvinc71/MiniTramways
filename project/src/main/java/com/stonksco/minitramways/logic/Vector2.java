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

        /*//from w w  w.ja  v  a2s  .  c om
         * Copyright (c) JenSoft API
         * This source file is part of JenSoft API, All rights reserved.
         * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
         */
        //package com.java2s;

        double bx = x2 - x1;
        double by = y2 - y1;
        double dx = x4 - x3;
        double dy = y4 - y3;

        double b_dot_d_perp = bx * dy - by * dx;

        if (b_dot_d_perp != 0) {
            double cx = x3 - x1;
            double cy = y3 - y1;

            double t = (cx * dy - cy * dx) / b_dot_d_perp;

            if (t < 0 || t > 1) {
                return null;
            }

            double u = (cx * by - cy * bx) / b_dot_d_perp;

            if (u < 0 || u > 1) {
                return null;
            } else {
                double x = x1 + t * bx;
                double y = y1 + t * by;
                res = new Vector2(x,y);
            }

        }

    return res;
    }

    public static double Distance(Vector2 v1, Vector2 v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x,2) - Math.pow(v2.y - v1.y,2));
    }

    public Vector2 round() {
        return new Vector2(Math.round(x),Math.round(y));
    }
}
