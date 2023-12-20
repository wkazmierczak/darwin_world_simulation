package agh.ics.oop.model.Maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.worldElements.Plant;

import java.util.*;

public abstract class AbstractPlanetMap implements PlanetMap<Animal, Vector2d>, Teleporter {

    protected final Map<Vector2d, Set<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final Boundary boundary;
    protected final int everyDayPlantsCount;
    protected final int energyAfterConsumingPlant;
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    protected String id; //TODO zapytac co to

    protected AbstractPlanetMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant) {
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
        this.everyDayPlantsCount = everyDayPlantsCount;
        this.energyAfterConsumingPlant = energyAfterConsumingPlant;
        growPlants(startingPlantsCount);
    }

    //JUST FOR TESTS
    protected AbstractPlanetMap(int width, int height) {
        this(width, height, 10, 3, 5);
    }

    public void addListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    public void mapChanged(String message) {
        for (MapChangeListener listener : mapChangeListeners) {
//            listener.mapChanged(this, message); // ! zly typ dla mapchanged
        }
    }

    public void place(Vector2d pos, Animal animal) {
        if (animals.containsKey(pos))
            animals.get(pos).add(animal);
        else {
            animals.put(pos, new HashSet<>(List.of(animal)));
        }
        mapChanged(animal + " placed at " + pos);
    }

    @Override
    public void place(Animal animal) {
        Vector2d currPos = animal.getPosition();
        place(currPos, animal);
    }


    @Override
    public Collection<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public void move(Animal elem) {
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
    public Plant plantAt(Vector2d pos) {
        return plants.get(pos);
    }

    @Override
    public Vector2d moveIntoDirection(Vector2d base, Vector2d step) {
        //TODO naprawić bieguny
        var newPos = base.add(step);
        return newPos.closeIn(boundary);
    }

    protected int getWidth() {
        return this.getCurrentBounds().upperRight().getX() - this.getCurrentBounds().bottomLeft().getX();
    }

    protected int getHeight() {
        return this.getCurrentBounds().upperRight().getY() - this.getCurrentBounds().bottomLeft().getY();
    }

    @Override
    public void removeDead(List<Animal> animals) {
        animals.stream().filter(Animal::isDead).forEach(this::removeAnimal);
//        toDelete.stream().map(Animal::getPosition).distinct().forEach(pos -> toDelete.forEach(animal -> removeAnimal(pos, animal)));
    }

    protected void removeAnimal(Vector2d pos, Animal animal) {
        animals.get(pos).remove(animal);
    }

    protected void removeAnimal(Animal animal) {
        Vector2d pos = animal.getPosition();
        animals.get(pos).remove(animal);
    }

    protected void removePlant(Vector2d pos) {
        animals.remove(pos);
    }

//    @Override
//    public Collection<SetWorldElement> getElements() {
//        return animals.values();
//    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this); //does not work
        Boundary boundaries = getCurrentBounds();
        return visualizer.draw(boundaries.bottomLeft(), boundaries.upperRight());
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    @Override
    public String getId() {
        return id;
    }

}
