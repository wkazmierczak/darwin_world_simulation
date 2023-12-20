package agh.ics.oop.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class MapDirectionTest {

    @Test
    public void nextTest(){
        MapDirection curr1 = MapDirection.NORTH;
        MapDirection sol1 = MapDirection.EAST;
        MapDirection curr2 = MapDirection.EAST;
        MapDirection sol2 = MapDirection.SOUTH;
        MapDirection curr3 = MapDirection.SOUTH;
        MapDirection sol3 = MapDirection.WEST;
        MapDirection curr4 = MapDirection.WEST;
        MapDirection sol4 = MapDirection.NORTH;

        MapDirection res1 = curr1.next();
        MapDirection res2 = curr2.next();
        MapDirection res3 = curr3.next();
        MapDirection res4= curr4.next();

        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);
        Assertions.assertEquals(sol3, res3);
        Assertions.assertEquals(sol4, res4);

    }

    @Test
    public void prevTest(){
        MapDirection curr1 = MapDirection.NORTH;
        MapDirection sol1 = MapDirection.WEST;
        MapDirection curr2 = MapDirection.EAST;
        MapDirection sol2 = MapDirection.NORTH;
        MapDirection curr3 = MapDirection.SOUTH;
        MapDirection sol3 = MapDirection.EAST;
        MapDirection curr4 = MapDirection.WEST;
        MapDirection sol4 = MapDirection.SOUTH;

        MapDirection res1 = curr1.previous();
        MapDirection res2 = curr2.previous();
        MapDirection res3 = curr3.previous();
        MapDirection res4= curr4.previous();

        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);
        Assertions.assertEquals(sol3, res3);
        Assertions.assertEquals(sol4, res4);

    }
}
