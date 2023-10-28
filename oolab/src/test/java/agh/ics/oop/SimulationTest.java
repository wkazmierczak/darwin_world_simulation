package agh.ics.oop;


import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimulationTest {

    @Test
    public void simulationTest(){
        //GIVEN
        String[] input = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(input);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(directions, positions);

        MapDirection expectedOrient1 = MapDirection.SOUTH;
        MapDirection expectedOrient2 = MapDirection.NORTH;

        Vector2d expectedPosition1 = new Vector2d(3, 0);
        Vector2d expectedPosition2 = new Vector2d(2, 4);


        //WHEN
        simulation.run();


        //THEN
        Assertions.assertEquals(expectedOrient1, simulation.getAnimals().get(0).getOrientation());
        Assertions.assertEquals(expectedOrient2, simulation.getAnimals().get(1).getOrientation());
        Assertions.assertEquals(expectedPosition1, simulation.getAnimals().get(0).getPosition());
        Assertions.assertEquals(expectedPosition2, simulation.getAnimals().get(1).getPosition());

    }
}
