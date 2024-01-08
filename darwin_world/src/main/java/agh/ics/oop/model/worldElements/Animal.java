package agh.ics.oop.model.worldElements;


import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.genotype.Genotype;
import agh.ics.oop.model.listeners.AnimalChangeListener;
import agh.ics.oop.model.listeners.AnimalTracker;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.Teleporter;
import agh.ics.oop.model.setupData.AnimalSetupData;
import agh.ics.oop.model.stats.AnimalStats;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energyLevel;
    private final Genotype genotype;
    private AnimalStats stats;
    private AnimalSetupData setupData;
    List<Animal> parents = new ArrayList<>();
    private final List<AnimalChangeListener> listeners = new LinkedList<>();


    //animal born on initialization
    public Animal(Vector2d position, AnimalSetupData setupData) {
        this.setupData = setupData;
        this.position = position;
        this.orientation = MapDirection.getRandom();
        this.energyLevel = setupData.initialAnimalEnergy();
        this.genotype = setupData.genotypeType().createGenotype(setupData.genotypeLength());
        this.stats = new AnimalStats();
        this.parents = new ArrayList<>();
    }

    public Animal(int initialEnergy, Genotype genotypeFromParents, Animal parent1, Animal parent2) {
        this.position = parent1.getPosition();
        this.orientation = MapDirection.getRandom();
        this.energyLevel = initialEnergy;
        this.genotype = genotypeFromParents;
        this.stats = new AnimalStats(); //TODO for each sim different values
        this.parents = List.of(parent1, parent2);
//        this.parent1 = parent1;
//        this.parent2 = parent2;
    }

    @Override
    public String toString() {
        return getOrientation().toString();
    }

    public boolean isAt(Vector2d position) {
        return getPosition().equals(position);
    }

    public void move(Teleporter teleport) {
        int rotate = genotype.next();
        Vector2d prevPos = getPosition();
        MapDirection prevOrient = getOrientation();

        MapDirection nextOrientation = orientation.rotateNTimes(rotate);

        PositionDetails moveDetails = teleport.moveIntoDirection(prevPos, prevOrient, nextOrientation.toUnitVector());
        Vector2d nextPosition = moveDetails.position();
        nextOrientation = moveDetails.orientation();

        if (teleport.plantAt(nextPosition) != null && teleport.plantAt(nextPosition).isPoisonous()) {
            if (new Random().nextInt(100) < 20) {
                nextOrientation = prevOrient.rotateNTimes(new Random().nextInt(1, 8));
                moveDetails = teleport.moveIntoDirection(prevPos, prevOrient, nextOrientation.toUnitVector());
                nextPosition = moveDetails.position();
                nextOrientation = moveDetails.orientation();
            }
        }

        this.position = nextPosition;
        this.orientation = nextOrientation;
    }

    public void eat(Plant plant) {
        energyLevel += plant.getNutritious();
    }

    public Animal reproduce(Animal other) {
        int energySpendToReproduce = setupData.energySpendToReproduce();

        if (this.energyLevel <= energySpendToReproduce || other.energyLevel <= energySpendToReproduce) {
            return null;
        }
        this.energyLevel -= energySpendToReproduce;
        other.energyLevel -= energySpendToReproduce;

        return new Animal(energySpendToReproduce * 2, genotype.createNewFrom(this, other, setupData.mutationsRange()), this, other);
    }


//    public void nextDay(Teleporter teleport) {
//        energyLevel--;
////        MoveDirection direction = genotype.next();
//        move(teleport);
//        Plant plant = teleport.plantAt(position);
//        if (plant != null) {
////            eat(plant);
//        } // TODO dodanie logiki związanej z jedzeniem
////        TODO rozmanażanie się zwierzaków
//
////        if (energyLevel <= 0){
////            this.getStats().getDayOfDeath() = //TODO get day of simulation;
////        }
//    }

    public void nextDay() {
        energyLevel--;
        stats.incrementAge();
    }

    public boolean isDead() {
        return energyLevel <= 0;
//        return this.getStats().getDayOfDeath() != null;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public AnimalSetupData getSetupData() {
        return setupData;
    }

    public List<Animal> getParents() {
        return parents;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public AnimalStats getStats() {
        return stats;
    }

    //    public Animal getParent1() {
//        return parent1;
//    }
//
//    public Animal getParent2() {
//        return parent2;
//    }
    public void addAnimalTracker(AnimalChangeListener listener) {
        listeners.add(listener);
    }


    public void removeAnimalTracker(AnimalChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyAnimalTracker() {
        listeners.forEach(listener -> listener.animalInfoChanged(this));
    }

}
