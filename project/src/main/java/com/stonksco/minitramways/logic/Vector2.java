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
        this.x=(double)x;
        this.y=(double)y;
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

        return "( "+xFormatted.toString()+" , "+yFormatted.toString()+" )";
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
}
