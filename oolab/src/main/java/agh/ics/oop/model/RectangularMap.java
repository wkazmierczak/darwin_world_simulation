package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Map;

public class RectangularMap extends AbstractWorldMap{
    private final int width;
    private final int height;

    private final Boundary boundaries;


//    private final Map<Vector2d, WorldElement> animals = new HashMap<>();

    public RectangularMap(int width, int height){
        this.height = height;
        this.width = width;
        this.boundaries = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));

    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(boundaries.bottomLeft())
                && position.precedes(boundaries.upperRight()) && super.canMoveTo(position);
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


    @Override
    public Boundary getCurrentBounds() {
        return boundaries;
    }
}