package agh.ics.oop.model.Boundary;

import agh.ics.oop.model.Vector2d;

public record Boundary(Vector2d bottomLeft,
                       Vector2d upperRight) {
    public int getWidth() {
        return upperRight().getX() - bottomLeft().getX();
    }

    public int getHeight() {
        return upperRight().getY() - bottomLeft().getY();
    }
}
