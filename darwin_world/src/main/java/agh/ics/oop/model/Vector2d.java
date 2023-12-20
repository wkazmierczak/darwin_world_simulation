package agh.ics.oop.model;

import agh.ics.oop.model.Boundary.Boundary;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    int returnCycled(int xl, int x0, int xr) {
        return (x0 - xl) % Math.abs(xr - xl) + xl;
    }

    public Vector2d closeIn(Boundary boundary) {
        int xl = boundary.bottomLeft().getX();
        int xr = boundary.upperRight().getY();
        int yd = boundary.bottomLeft().getY();
        int yu = boundary.upperRight().getY();
        return new Vector2d(returnCycled(xl, getX(), xr), returnCycled(yd, getY(), yu));
    }

    public boolean inBounds(Boundary boundary) {
        return boundary.bottomLeft().precedes(this) && boundary.upperRight().follows(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return Objects.equals(that.x, x) && Objects.equals(that.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
