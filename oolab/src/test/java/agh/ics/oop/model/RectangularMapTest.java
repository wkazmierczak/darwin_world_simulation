package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    public void placeTest() {
        RectangularMap map = new RectangularMap(2, 2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        Animal animal2 = new Animal(new Vector2d(1, 3));

        boolean res1 = map.place(animal1);
        boolean res2 = map.place(animal2);


        Assertions.assertTrue(res1);
        Assertions.assertFalse(res2);
    }

    @Test
    public void move() {
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(new Vector2d(2, 3));
        map.place(animal1);

        map.move(animal1, MoveDirection.FORWARD);

        Assertions.assertEquals(animal1, map.getAnimals().get(new Vector2d(2, 4)));
        Assertions.assertNull(map.getAnimals().get(new Vector2d(2, 3)));
    }

    @Test
    public void isOccupied() {
        RectangularMap map = new RectangularMap(2, 2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        Assertions.assertTrue(map.isOccupied(new Vector2d(1, 1)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(2, 1)));
    }

    @Test
    public void objectAt() {
        RectangularMap map = new RectangularMap(2, 2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1, 1)));
        assertNull(map.objectAt(new Vector2d(2, 1)));
    }

    @Test
    public void canMoveTo() {
        RectangularMap map = new RectangularMap(2, 2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        Assertions.assertFalse(map.canMoveTo(new Vector2d(1, 1)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(2, 1)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(-11, 1)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(3, 1)));
    }

    @Test
    public void testToString() {
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
}