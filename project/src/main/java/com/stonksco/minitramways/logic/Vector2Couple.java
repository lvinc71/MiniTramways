package com.stonksco.minitramways.logic;

import java.util.Objects;

/**
 * Couple de vecteurs à deux dimensions
 */
public class Vector2Couple {

    private Vector2 v1;
    private Vector2 v2;

    public Vector2Couple(Vector2 v1, Vector2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public double distance() {
        return Vector2.Distance(v1,v2);
    }

    public Vector2 getV1() {
        return v1.clone();
    }

    public Vector2 getV2() {
        return v2.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2Couple that = (Vector2Couple) o;
        return v1.equals(that.v1) && v2.equals(that.v2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2);
    }

    @Override
    public String toString() {
        return "Vector2Couple{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }
}
