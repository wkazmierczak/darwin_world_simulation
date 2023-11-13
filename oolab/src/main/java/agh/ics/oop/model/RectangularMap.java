package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap{
    private final int width;
    private final int height;

    private final Vector2d lowerLeft;

    private final Vector2d upperRight;

//    private final Map<Vector2d, WorldElement> animals = new HashMap<>();

    public RectangularMap(int width, int height){
        this.height = height;
        this.width = width;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft)
                && position.precedes(upperRight) && super.canMoveTo(position);
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeft, upperRight);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Map<Vector2d, WorldElement> getAnimals() {
        return animals;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }
}
