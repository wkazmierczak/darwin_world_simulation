package agh.ics.oop.model.Maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.worldElements.Plant;
import agh.ics.oop.model.worldElements.WorldElement;

import java.util.*;

public abstract class AbstractPlanetMap implements WorldMap<WorldElement, Vector2d>, Teleporter {

    protected final Map<Vector2d, Set<WorldElement>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final Boundary boundary;
    protected final int everyDayPlantsCount;
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    protected String id;

    protected AbstractPlanetMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount) {
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
        this.everyDayPlantsCount = everyDayPlantsCount;
        growPlants(startingPlantsCount);
    }

    //JUST FOR TESTS
    protected AbstractPlanetMap(int width, int height) {
        this(width, height, 10, 3);
    }

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

    public void place(Vector2d pos, WorldElement animal) {
        if (animals.containsKey(pos))
            animals.get(pos).add(animal);
        else {
            animals.put(pos, new HashSet<>(List.of(animal)));
        }
        mapChanged(animal + " placed at " + pos);
    }

    @Override
    public void place(WorldElement animal) {
        Vector2d currPos = animal.getPosition();
        place(currPos, animal);
    }


    @Override
    public void move(WorldElement elem, MoveDirection direction) {
        Animal animal = (Animal) elem;
        String animalToString = animal.toString();

        Vector2d prevPos = animal.getPosition();
//        animal.move(direction, this);
        Vector2d currPos = animal.getPosition();
        if (!currPos.equals(prevPos)) {
            place(currPos, animal);
            removeAnimal(prevPos, animal);
            mapChanged(animal + " moved from " + prevPos + " to " + currPos);
        } else {
            mapChanged(animal.getPosition() + " rotated from " + animalToString + " to " + animal);
        }
    }

//    @Override
//    public boolean isOccupied(Vector2d position) {
//        return animals.containsKey(position);
//    }

//    @Override
//    public WorldElement objectAt(Vector2d position) {
//        return animals.get(position);
//    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return !isOccupied(position);
//    }

    @Override
    public Vector2d moveIntoDirection(Vector2d base, Vector2d step) {
        var newPos = base.add(step);
        return newPos.closeIn(boundary);
    }

    @Override
    public void removeDead(List<Animal> animals) {
        animals.stream().filter(Animal::isDead).forEach(this::removeAnimal);
//        toDelete.stream().map(Animal::getPosition).distinct().forEach(pos -> toDelete.forEach(animal -> removeAnimal(pos, animal)));
    }

    protected void removeAnimal(Vector2d pos, WorldElement animal) {
        animals.get(pos).remove(animal);
    }

    protected void removeAnimal(WorldElement animal) {
        Vector2d pos = animal.getPosition();
        animals.get(pos).remove(animal);
    }

    protected void removePlant(Vector2d pos) {
        animals.remove(pos);
    }

    abstract void growPlants(int count);

//    @Override
//    public Collection<SetWorldElement> getElements() {
//        return animals.values();
//    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);

        Boundary boundaries = getCurrentBounds();

        return visualizer.draw(boundaries.bottomLeft(), boundaries.upperRight());
    }

}
