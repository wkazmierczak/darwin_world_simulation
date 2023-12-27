package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.MapChangeListener;
import agh.ics.oop.model.setupData.WorldSetupData;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.AnimalComparator;
import agh.ics.oop.model.worldElements.PositionDetails;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class AbstractPlanetMap implements PlanetMap, Teleporter {

    protected final Map<Vector2d, SortedSet<Animal>> animalsPos = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final Boundary boundary;
    protected final WorldSetupData setupData;
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    protected String id; //TODO zapytac co to


    protected AbstractPlanetMap(WorldSetupData setupData) {
        this.setupData = setupData;
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(getSetupData().width(), getSetupData().height()));
    }

    //JUST FOR TESTS
    protected AbstractPlanetMap(int width, int height) {
        this(new WorldSetupData(width, height, 10, 3, 2));
    }

    public void addListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    public void mapChanged(String message) {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this, message); // ! zly typ dla mapchanged
        }
    }

    public void place(Vector2d pos, Animal animal) {
        if (animalsPos.containsKey(pos))
            animalsPos.get(pos).add(animal);
        else {
            SortedSet<Animal> animalsOnPos = new TreeSet<>(new AnimalComparator());
            animalsOnPos.add(animal);
            animalsPos.put(pos, animalsOnPos);
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
        return animalsPos.get(position);
    }

    @Override
    public void move(Animal elem) {
        Animal animal = (Animal) elem;
        String animalToString = animal.toString();

        Vector2d prevPos = animal.getPosition();
        animal.move(this);
        Vector2d currPos = animal.getPosition();
        if (!currPos.equals(prevPos)) {
            place(currPos, animal);
            removeAnimal(prevPos, animal);
            mapChanged(animal + " moved from " + prevPos + " to " + currPos);
        } else {
            mapChanged(animal.getPosition() + " rotated from " + animalToString + " to " + animal);
        }
    }

    @Override
    public void nextDay(List<Animal> animals) {
        animals.forEach(Animal::nextDay);
    }


    public void growPlants() {
        growPlants(getPlantsPerDay());
    }

    abstract void growPlants(int count);

    @Override
    public Plant plantAt(Vector2d pos) {
        return plants.get(pos);
    }

    @Override
    public PositionDetails moveIntoDirection(Vector2d basePosition, MapDirection baseDirection, Vector2d step) {
        //TODO naprawić bieguny
        var position = basePosition.add(step);
        var orientation = baseDirection;
        position = position.closeInXTeleport(boundary);
        if (!position.inBounds(boundary)) {
            position = position.closeInY(boundary);
            orientation = orientation.opposite();
        }
        return new PositionDetails(position, orientation);
    }

    @Override
    public List<Animal> removeDead(List<Animal> animals, int day) {
        List<Animal> removed = animals.stream().filter(Animal::isDead).toList();
        removed.forEach(a -> a.getStats().setDayOfDeath(day));
        removed.forEach(this::removeAnimal);
        return removed;
    }

    private void removeAnimal(Vector2d pos, Animal animal) {
        animalsPos.get(pos).remove(animal);
        if (animalsPos.get(pos).isEmpty()) {
            animalsPos.remove(pos);
        }
    }

    protected void removeAnimal(Animal animal) {
        Vector2d pos = animal.getPosition();
        animalsPos.get(pos).remove(animal);
    }

    protected void handleWhoEats(Vector2d pos, Plant plant) {
        animalsPos.get(pos).first().eat(plant);
        removePlant(pos);
    }

    @Override
    public void letAnimalsEat() {
        List<Vector2d> positionWithPlantsAndAnimals = plants.keySet().stream().filter(pos -> animalsPos.containsKey(pos) && !animalsPos.get(pos).isEmpty()).toList();
        positionWithPlantsAndAnimals.forEach(pos -> handleWhoEats(pos, plants.get(pos)));
    }

    protected void removePlant(Vector2d pos) {
        plants.remove(pos);
    }

    public int getPlantsCount() {
        return plants.size();
    }

    public WorldSetupData getSetupData() {
        return setupData;
    }


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

    @Override
    public int getFreePositionsCount() {
        return (int) (boundary.getWidth() * boundary.getHeight() - Stream.concat(plants.keySet().stream(), animalsPos.keySet().stream())
                .distinct()
                .count());
    }

    public int getStartingPlantsCount() {
        return setupData.startingPlantsCount();
    }

    public int getPlantsPerDay() {
        return setupData.getPlantsPerDay();
    }
}
