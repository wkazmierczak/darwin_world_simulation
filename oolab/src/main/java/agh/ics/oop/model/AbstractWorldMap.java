package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2d>{

    protected final Map<Vector2d, WorldElement> animals = new HashMap<>();


    @Override
    public boolean place(WorldElement animal) {

        Vector2d currPos = animal.getPosition();

        if (canMoveTo(currPos)){
            animals.put(currPos, animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(WorldElement elem, MoveDirection direction) {
        Animal animal = (Animal) elem;

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
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public Collection<WorldElement> getElements() {
        return animals.values();
    }
}
