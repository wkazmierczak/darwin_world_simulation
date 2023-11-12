package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void placeTest() {
        GrassField map = new GrassField(2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        Animal animal2 = new Animal(new Vector2d(1, 3));
        Animal animal3 = new Animal(new Vector2d(1, 3));


        boolean res1 = map.place(animal1);
        boolean res2 = map.place(animal2);
        boolean res3 = map.place(animal3);


        Assertions.assertEquals(2, map.getTufts().size());
        Assertions.assertEquals(animal1, map.getAnimals().get(new Vector2d(1, 1)));
        Assertions.assertEquals(animal2, map.getAnimals().get(new Vector2d(1, 3)));
        assertNull(map.getAnimals().get(new Vector2d(2, 2)));
        Assertions.assertTrue(res1);
        Assertions.assertTrue(res2);
        Assertions.assertFalse(res3);
    }

    @Test
    void move() {
        GrassField map = new GrassField(2);
        Animal animal1 = new Animal(new Vector2d(2, 3));
        map.place(animal1);

        map.move(animal1, MoveDirection.FORWARD);

        Assertions.assertEquals(animal1, map.getAnimals().get(new Vector2d(2, 4)));
        Assertions.assertNull(map.getAnimals().get(new Vector2d(2, 3)));
    }

    @Test
    void isOccupied() {
        GrassField map = new GrassField( 2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        Assertions.assertTrue(map.isOccupied(new Vector2d(1, 1)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(2, 1)));
    }

    @Test
    void objectAt() {
        GrassField map = new GrassField(2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        for (Map.Entry<Vector2d, WorldElement> ent2 : map.getTufts().entrySet()) {
            Vector2d key = ent2.getKey();
            WorldElement value = ent2.getValue();

            if (key.equals(new Vector2d(1, 1))){
                Assertions.assertEquals(animal1, map.objectAt(key));
            }
            else{
                Assertions.assertEquals(value, map.objectAt(key));
            }
        }

        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1, 1)));
    }

    @Test
    void canMoveTo() {
        GrassField map = new GrassField(2);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        map.place(animal1);

        Assertions.assertFalse(map.canMoveTo(new Vector2d(1, 1)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(2, 1)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(-11, 1)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(3, 1)));
    }

    @Test
    void testToString() {
        GrassField map = new GrassField(0);

        String exp_map = "";

        Assertions.assertEquals(exp_map, map.toString());
    }
}