package agh.ics.oop.model.maps;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.genotype.BasicGenotype;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.setupData.AnimalSetupData;
import agh.ics.oop.model.util.MyRange;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.PositionDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlanetMapTest {

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
        PlanetMap map = new EquatorMap(5, 5);
        AnimalSetupData setupData = new AnimalSetupData(1, -10, 1, 1, new MyRange(10, 20), 1, GenotypeType.BASIC_GENOTYPE);
        Animal deadAnimal = new Animal(new Vector2d(2, 2), setupData);
        Animal deadAnimal1 = new Animal(new Vector2d(1, 2), setupData);
        Animal deadAnimal2 = new Animal(new Vector2d(2, 3), setupData);
        List<Animal> animals = List.of(deadAnimal, deadAnimal1, deadAnimal2);
        for (Animal animal : animals) {
            map.place(animal);
        }

        List<Animal> removed = map.removeDead(animals, 1);

        assertEquals(3, removed.size());
    }

    @Test
    void handleWhoEats() {
        PlanetMap map = new EquatorMap(5, 5);
        AnimalSetupData setupData = new AnimalSetupData(1, 0, 1, 1, new MyRange(10, 20), 1, GenotypeType.BASIC_GENOTYPE);
        Animal parent1 = new Animal(new Vector2d(2, 2), setupData);
        Animal parent2 = new Animal(new Vector2d(2, 2), setupData);
        Animal contender1 = new Animal(10, new BasicGenotype(1), parent1, parent2);
        Animal contender2 = new Animal(15, new BasicGenotype(1), parent1, parent2);
        Animal contender3 = new Animal(10, new BasicGenotype(1), parent1, parent2);
        Animal contender4 = new Animal(10, new BasicGenotype(1), parent1, parent2);
        contender1.getStats().incrementAge();
        contender4.getStats().addChild(contender1);

        map.place(contender1);
        map.place(contender2);
        map.place(contender3);
        map.place(contender4);
        Collection<Animal> animals = map.animalsAt(new Vector2d(2, 2));
        assertIterableEquals(List.of(contender2, contender1, contender4, contender3), animals);
    }

}