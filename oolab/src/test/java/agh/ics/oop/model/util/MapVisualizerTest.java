package agh.ics.oop.model.util;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapVisualizerTest {

    @Test
    public void drawEmptyMapTest() {
        RectangularMap map = new RectangularMap(3, 2);

        String exp_map =
                " y\\x  0 1 2 3" + System.lineSeparator() +
                "  3: ---------" + System.lineSeparator() +
                "  2: | | | | |" + System.lineSeparator() +
                "  1: | | | | |" + System.lineSeparator() +
                "  0: | | | | |" + System.lineSeparator() +
                " -1: ---------" + System.lineSeparator();

        Assertions.assertEquals(exp_map, map.toString());
    }


    @Test
    public void drawMapWithAnimalsTest() {
        RectangularMap map = new RectangularMap(3, 2);
        Animal animal1 = new Animal(new Vector2d(2,2));
        Animal animal2 = new Animal(new Vector2d(3, 1));
        try {
            map.place(animal1);
        } catch (PositionAlreadyOccupiedException ignored){};
        try {
            map.place(animal2);
        } catch (PositionAlreadyOccupiedException ignored){};

        map.move(animal1, MoveDirection.LEFT);
        map.move(animal1, MoveDirection.LEFT);
        map.move(animal2, MoveDirection.RIGHT);

        String exp_map =
                " y\\x  0 1 2 3" + System.lineSeparator() +
                "  3: ---------" + System.lineSeparator() +
                "  2: | | |v| |" + System.lineSeparator() +
                "  1: | | | |>|" + System.lineSeparator() +
                "  0: | | | | |" + System.lineSeparator() +
                " -1: ---------" + System.lineSeparator();

        Assertions.assertEquals(exp_map, map.toString());
    }
}