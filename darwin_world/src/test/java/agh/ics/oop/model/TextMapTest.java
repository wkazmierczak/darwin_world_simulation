package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextMapTest {

    @Test
    public void placeTest() {
        String text1 = "ksdj";
        String text2 = "kot";
        String text3 = "pies";
        TextMap map = new TextMap();
        List<String> res1 = List.of("ksdj", "kot");
        List<String> res2 = List.of("ksdj", "kot", "pies");

        map.place(text1);
        map.place(text2);

        Assertions.assertEquals(res1, map.getStringList());

        map.place(text3);

        Assertions.assertEquals(res2, map.getStringList());


    }

    @Test
    public void moveTest() {
        TextMap map = new TextMap();
        map.place("ala");
        map.place("ma");
        map.place("kota");


        map.move("ma", MoveDirection.FORWARD);
        map.move("ma", MoveDirection.FORWARD);
        map.move("kota", MoveDirection.LEFT);
        map.move("kota", MoveDirection.LEFT);
        map.move("kota", MoveDirection.FORWARD);


        Assertions.assertEquals("[ \"kota\", \"ala\", \"ma\" ]", map.toString());

        map.move("kota", MoveDirection.BACKWARD);
        map.move("kota", MoveDirection.RIGHT);


        Assertions.assertEquals("[ \"ala\", \"kota\", \"ma\" ]", map.toString());
    }

    @Test
    public void isOccupiedTest() {
        TextMap map = new TextMap();
        map.place("kot");
        map.place("pies");
        map.place("mysz");

        Assertions.assertTrue(map.isOccupied(1));
        Assertions.assertFalse(map.isOccupied(4));
        Assertions.assertTrue(map.isOccupied(0));
        Assertions.assertFalse(map.isOccupied(-1));
    }

    @Test
    public void objectAtTest() {
        TextMap map = new TextMap();
        map.place("kot");
        map.place("pies");
        map.place("mysz");

        Assertions.assertEquals("kot", map.objectAt(0));
        Assertions.assertEquals("pies", map.objectAt(1));
        Assertions.assertNotEquals("mysz", map.objectAt(0));

    }

    @Test
    public void canMoveToTest() {
        TextMap map = new TextMap();
        map.place("kot");
        map.place("pies");
        map.place("mysz");

        Assertions.assertTrue(map.isOccupied(2));
        Assertions.assertFalse(map.isOccupied(10));
        Assertions.assertTrue(map.isOccupied(0));
        Assertions.assertFalse(map.isOccupied(-12));
    }
}