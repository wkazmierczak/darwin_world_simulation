package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class Vector2dTest {

    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(4, 5);
        Vector2d v4 = null;

        boolean res1 = v1.equals(v1);
        boolean res2 = v2.equals(v3);
        boolean res3 = v3.equals(v4);
        boolean res4= v1.equals(v2);

        Assertions.assertTrue(res1);
        Assertions.assertTrue(res2);
        Assertions.assertFalse(res3);
        Assertions.assertFalse(res4);
    }


    @Test
    public void toStringTest() {
        Vector2d vect1 = new Vector2d(0, 1);
        Vector2d vect2 = new Vector2d(-3, 2);
        String sol1 = "(0,1)";
        String sol2 = "(-3,2)";

        String res1 = vect1.toString();
        String res2 = vect2.toString();

        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);
    }

    @Test
    public void precedesTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(4, 6);

        boolean sol1 = v1.precedes(v2);
        boolean sol2 = v2.precedes(v1);
        boolean sol3 = v2.precedes(v3);
        boolean sol4 = v1.precedes(v1);

        Assertions.assertTrue(sol1);
        Assertions.assertFalse(sol2);
        Assertions.assertTrue(sol3);
        Assertions.assertTrue(sol4);
    }

    @Test
    public void followsTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(4, 6);

        boolean sol1 = v1.follows(v2);
        boolean sol2 = v2.follows(v1);
        boolean sol3 = v2.follows(v3);
        boolean sol4 = v1.follows(v1);

        Assertions.assertFalse(sol1);
        Assertions.assertTrue(sol2);
        Assertions.assertFalse(sol3);
        Assertions.assertTrue(sol4);
    }

    @Test
    public void addTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(4, 6);
        Vector2d sol1 = new Vector2d(7, 8);
        Vector2d sol2 = new Vector2d(7, 9);

        Vector2d res1 = v1.add(v2);
        Vector2d res2 = v1.add(v3);

        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);

    }

    @Test
    public void substractTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(4, 6);
        Vector2d sol1 = new Vector2d(-1, -2);
        Vector2d sol2 = new Vector2d(1, 3);

        Vector2d res1 = v1.substract(v2);
        Vector2d res2 = v3.substract(v1);

        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);

    }

    @Test
    public void upperRightTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 2);
        Vector2d v3 = new Vector2d(1, 6);
        Vector2d v4 = new Vector2d(4, 6);
        Vector2d exp1 = new Vector2d(4, 3);
        Vector2d exp2 = new Vector2d(3, 6);
        Vector2d exp3 = new Vector2d(4, 6);

        Vector2d res1 = v1.upperRight(v2);
        Vector2d res2 = v3.upperRight(v1);
        Vector2d res3 = v4.upperRight(v1);


        Assertions.assertEquals(exp1, res1);
        Assertions.assertEquals(exp2, res2);
        Assertions.assertEquals(exp3, res3);

    }

    @Test
    public void lowerLeftTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, 2);
        Vector2d v3 = new Vector2d(1, 6);
        Vector2d v4 = new Vector2d(4, 6);
        Vector2d exp1 = new Vector2d(3, 2);
        Vector2d exp2 = new Vector2d(1, 3);
        Vector2d exp3 = new Vector2d(3, 3);

        Vector2d res1 = v1.lowerLeft(v2);
        Vector2d res2 = v3.lowerLeft(v1);
        Vector2d res3 = v4.lowerLeft(v1);

        Assertions.assertEquals(exp1, res1);
        Assertions.assertEquals(exp2, res2);
        Assertions.assertEquals(exp3, res3);

    }

    @Test
    public void oppositeTest() {
        Vector2d v1 = new Vector2d(3, 3);
        Vector2d v2 = new Vector2d(4, -2);
        Vector2d v3 = new Vector2d(-1, -6);
        Vector2d v4 = new Vector2d(-4, 6);
        Vector2d expected1 = new Vector2d(-3, -3);
        Vector2d expected2 = new Vector2d(-4, 2);
        Vector2d expected3 = new Vector2d(1, 6);
        Vector2d expected4 = new Vector2d(4, -6);

        Vector2d res1 = v1.opposite();
        Vector2d res2 = v2.opposite();
        Vector2d res3 = v3.opposite();
        Vector2d res4 = v4.opposite();

        Assertions.assertEquals(expected1, res1);
        Assertions.assertEquals(expected2, res2);
        Assertions.assertEquals(expected3, res3);
        Assertions.assertEquals(expected4, res4);

    }
}
