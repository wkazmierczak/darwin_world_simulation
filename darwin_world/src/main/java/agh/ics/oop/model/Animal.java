package agh.ics.oop.model;

import agh.ics.oop.model.genotype.BasicGenotype;
import agh.ics.oop.model.genotype.Genotype;
import agh.ics.oop.model.worldElements.WorldElement;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    private int energyLevel;

    private final Genotype genotype;

    private final static Vector2d LEFT_BOTTOM = new Vector2d(0, 0);
    private final static Vector2d RIGHT_TOP = new Vector2d(4, 4);

    public Animal(Vector2d position, Genotype genotype) {
        this.position = position;
        this.orientation = MapDirection.NORTH;
        this.energyLevel = 0;
        this.genotype = genotype;
    }

    public Animal(Vector2d position) {
        this(position, new BasicGenotype(5));
    }

    public boolean isDead() {
        return false;
    }

    @Override
    public String toString() {
        return getOrientation().toString();
    }

    public boolean isAt(Vector2d position) {
        return getPosition().equals(position);
    }

    public void move(MoveDirection direction, MoveValidator<Vector2d> validator) {
        Vector2d currPos = getPosition();
        MapDirection currOrient = getOrientation();

        switch (direction) {
            case RIGHT ->
                    this.orientation = currOrient.next();
            case LEFT ->
                    this.orientation = currOrient.previous();
            case FORWARD -> {
                currPos = currPos.add(currOrient.toUnitVector());
                if (validator.canMoveTo(currPos)) {
                    this.position = currPos;
                }
                ;
            }
            case BACKWARD -> {
                currPos = currPos.substract(currOrient.toUnitVector());
                if (validator.canMoveTo(currPos)) {
                    this.position = currPos;
                }
            }
        }
        ;


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
}
