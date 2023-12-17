package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2d>{

    protected final Map<Vector2d, WorldElement> animals = new HashMap<>();

    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    protected String id;

    public void addListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    public void mapChanged(String message) {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this, message);
        }
    }


    @Override
    public void place(WorldElement animal) throws PositionAlreadyOccupiedException{

        Vector2d currPos = animal.getPosition();

        if (canMoveTo(currPos)){
            animals.put(currPos, animal);
            mapChanged(animal + " placed at " + currPos);
        }
        else {
            throw new PositionAlreadyOccupiedException(currPos);
        }
    }

    @Override
    public void move(WorldElement elem, MoveDirection direction) {
        Animal animal = (Animal) elem;

        String animalToString = animal.toString();

        Vector2d prevPos = animal.getPosition();
        animal.move(direction, this);
        Vector2d currPos = animal.getPosition();
        if (!currPos.equals(prevPos)){
            animals.put(currPos, animal);
            animals.remove(prevPos);
            mapChanged(animal + " moved from " + prevPos + " to " +currPos);
        }
        else {
            mapChanged(animal.getPosition() + " rotated from " + animalToString + " to " + animal);
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

    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);

        Boundary boundaries = getCurrentBounds();

        return visualizer.draw(boundaries.bottomLeft(), boundaries.upperRight());
    }

}
