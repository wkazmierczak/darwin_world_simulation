package agh.ics.oop.model.worldElements;


import agh.ics.oop.model.*;

import agh.ics.oop.model.maps.Teleporter;
import agh.ics.oop.model.genotype.BasicGenotype;
import agh.ics.oop.model.genotype.Genotype;
import agh.ics.oop.model.stats.AnimalStats;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    private int energyLevel;

    private final Genotype genotype;

    private AnimalStats Stats;

    private Animal parent1;

    private Animal parent2;


    private final static Vector2d LEFT_BOTTOM = new Vector2d(0, 0);
    private final static Vector2d RIGHT_TOP = new Vector2d(4, 4);

    public Animal(Vector2d position, Genotype genotype, Animal parent1, Animal parent2) {
        this.position = position;
        this.orientation = MapDirection.NORTH;
        this.energyLevel = 0;
        this.genotype = genotype;
        this.Stats = new AnimalStats(2, 2); //TODO for each sim different values
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public Animal(Vector2d position, Genotype genotype) {
        this(position, genotype, null, null);
    }


    public Animal(Vector2d position) {
        this(position, new BasicGenotype(5));
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

        if (teleport.plantAt(nextPosition).isPoisonous()) {
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
        int energySpendToReproduce = this.getStats().getEnergySpendToReproduce();

        if (this.energyLevel <= energySpendToReproduce || other.energyLevel <= energySpendToReproduce) {
            return null;
        }

        return new Animal(this.getPosition(), genotype.createNewFrom(this, other), this, other);
    }


    public void nextDay(Teleporter teleport) {
        energyLevel--;
//        MoveDirection direction = genotype.next();
        move(teleport);
        Plant plant = teleport.plantAt(position);
        if (plant != null) {
            eat(plant);
        }
//        if (energyLevel <= 0){
//            this.getStats().getDayOfDeath() = //TODO get day of simulation;
//        }
    }

    public boolean isDead() {
        return this.getStats().getDayOfDeath() != null;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public static Vector2d getLeftBottom() {
        return LEFT_BOTTOM;
    }

    public static Vector2d getRightTop() {
        return RIGHT_TOP;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public AnimalStats getStats() {
        return Stats;
    }

    public Animal getParent1() {
        return parent1;
    }

    public Animal getParent2() {
        return parent2;
    }
}
