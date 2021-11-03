package com.stonksco.minitramways.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
}
