package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2d>{
    private final int width;
    private final int height;

    private final Vector2d lowerLeft;

    private final Vector2d upperRight;
    private final Map<Vector2d, Animal> animals = new HashMap<>();

    public RectangularMap(int width, int height){
        this.height = height;
        this.width = width;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean place(Animal animal){
        Vector2d currPos = animal.getPosition();

        if (canMoveTo(currPos)){
            animals.put(currPos, animal);
            return true;
        }
        return false;

    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d prevPos = animal.getPosition();
        animal.move(direction, this);
        Vector2d currPos = animal.getPosition();
        if (!currPos.equals(prevPos)){
            animals.put(currPos, animal);
            animals.remove(prevPos);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft)
                && position.precedes(upperRight) && !isOccupied(position);
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

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }
}
