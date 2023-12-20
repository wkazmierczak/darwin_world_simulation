package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.PositionAlreadyOccupiedException;
import agh.ics.oop.model.worldElements.Plant;

import java.util.Collection;
import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap<T, P> {

    /**
     * Place a animal on the map.
     *
     * @param object The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    void place(T object) throws PositionAlreadyOccupiedException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(T object, MoveDirection direction);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
//    boolean isOccupied(P position);

    /**
     * Returns a plant at a given position.
     *
     * @param position The position of the plant.
     * @return plant or null if no plants grows on position.
     */
    Plant plantAt(P position);

    void removeDead(List<Animal> animals);

    void growPlants();


    Collection<T> getElements();

    Boundary getCurrentBounds();

    String getId();
}
