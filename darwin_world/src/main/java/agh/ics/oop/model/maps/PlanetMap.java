package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.PositionAlreadyOccupiedException;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface PlanetMap extends Teleporter {

    /**
     * Place a animal on the map.
     *
     * @param object The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    void place(Animal object) throws PositionAlreadyOccupiedException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal object);

    Collection<Animal> animalsAt(Vector2d position);
    Plant plantAt(Vector2d position);

    void removeDead(List<Animal> animals);

    void growPlants();

    void letAnimalsEat();


//    Collection<T> getElements();

    Boundary getCurrentBounds();

    String getId();

    int updateNumOfFreePositions();

    int getStartingPlantsCount();
}
