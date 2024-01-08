package agh.ics.oop.model.maps;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.PositionDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlanetMapTest {

    @Test
    void move() {
    }

    @Test
    void addListener() {
    }

    @Test
    void removeListener() {
    }

    @Test
    void mapChanged() {
    }

    @Test
    void place() {
    }

    @Test
    void testPlace() {
    }

    @Test
    void animalsAt() {
    }

    @Test
    void nextDay() {
    }

    @Test
    void growPlants() {
    }

    @Test
    void testGrowPlants() {
    }

    @Test
    void plantAt() {
    }

    @Test
    void moveIntoDirection() {
        PlanetMap map = new EquatorMap(5, 5);

//        jump from right to left
        Vector2d basePosition1 = new Vector2d(5, 2);
        Vector2d step1 = new Vector2d(1, 0);
        MapDirection baseDirection1 = MapDirection.NORTH;
        PositionDetails result1 = map.moveIntoDirection(basePosition1, baseDirection1, step1);
        assertEquals(new Vector2d(0, 2), result1.position());
        assertEquals(MapDirection.NORTH, result1.orientation());

        //jump from left to right
        Vector2d basePosition2 = new Vector2d(0, 2);
        Vector2d step2 = new Vector2d(-1, 0);
        MapDirection baseDirection2 = MapDirection.EAST;
        PositionDetails result2 = map.moveIntoDirection(basePosition2, baseDirection2, step2);
        assertEquals(new Vector2d(5, 2), result2.position());
        assertEquals(MapDirection.EAST, result2.orientation());

        //from top to bottom
        Vector2d basePosition3 = new Vector2d(2, 5);
        Vector2d step3 = new Vector2d(0, 1);
        MapDirection baseDirection3 = MapDirection.NORTH;
        PositionDetails result3 = map.moveIntoDirection(basePosition3, baseDirection3, step3);
        assertEquals(new Vector2d(2, 5), result3.position());
        assertEquals(MapDirection.SOUTH, result3.orientation());

        //from bottom to top
        Vector2d basePosition4 = new Vector2d(2, 0);
        Vector2d step4 = new Vector2d(0, -1);
        MapDirection baseDirection4 = MapDirection.SOUTH;
        PositionDetails result4 = map.moveIntoDirection(basePosition4, baseDirection4, step4);
        assertEquals(new Vector2d(2, 0), result4.position());
        assertEquals(MapDirection.NORTH, result4.orientation());

        //diagonal move
        Vector2d basePosition5 = new Vector2d(0, 0);
        Vector2d step5 = new Vector2d(-1, -1);
        MapDirection baseDirection5 = MapDirection.SOUTHEAST;
        PositionDetails result5 = map.moveIntoDirection(basePosition5, baseDirection5, step5);
        assertEquals(new Vector2d(5, 0), result5.position());
        assertEquals(MapDirection.NORTHWEST, result5.orientation());
    }

    @Test
    void removeDead() {
    }

    @Test
    void removeAnimal() {
    }

    @Test
    void handleWhoEats() {
    }

    @Test
    void letAnimalsEat() {
    }

    @Test
    void handleWhoReproduces() {
    }

    @Test
    void letAnimalsReproduce() {
    }

    @Test
    void removePlant() {
    }

    @Test
    void getPlantsCount() {
    }

    @Test
    void getSetupData() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getCurrentBounds() {
    }

    @Test
    void getId() {
    }

    @Test
    void getFreePositionsCount() {
    }

    @Test
    void getStartingPlantsCount() {
    }

    @Test
    void getPlantsPerDay() {
    }
}