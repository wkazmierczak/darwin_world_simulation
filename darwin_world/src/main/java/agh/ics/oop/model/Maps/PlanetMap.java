package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.PositionAlreadyOccupiedException;

import java.util.Collection;
import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface PlanetMap<T, P> extends Teleporter {

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
    void move(T object);

    Collection<T> animalsAt(P position);

    void removeDead(List<T> animals);

    void growPlants(int count);


//    Collection<T> getElements();

    Boundary getCurrentBounds();

    String getId();
}
