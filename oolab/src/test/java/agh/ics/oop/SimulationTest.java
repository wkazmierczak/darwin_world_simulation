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
        String[] input1 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        String[] input2 = {"f", "b", "r", "l", "t", "f", "r", "l", "f", "f", "f", "f", "f", "r", "f", "f",
                "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        String[] input3 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "l", "f", "r", "f", "f", "b", "b"};

        List<MoveDirection> directions1 = OptionsParser.parse(input1);
        List<MoveDirection> directions2 = OptionsParser.parse(input2);
        List<MoveDirection> directions3 = OptionsParser.parse(input3);

        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(1, 2));
        List<Vector2d> positions3 = List.of(new Vector2d(2,2), new Vector2d(3,4),
                new Vector2d(2,0), new Vector2d(4,4), new Vector2d(0, 0));

        Simulation simulation1 = new Simulation(directions1, positions1);
        Simulation simulation2 = new Simulation(directions2, positions2);
        Simulation simulation3 = new Simulation(directions3, positions3);

        MapDirection expectedOrient1_f = MapDirection.SOUTH;
        MapDirection expectedOrient2_f = MapDirection.NORTH;

        Vector2d expectedPosition1_f = new Vector2d(3, 0);
        Vector2d expectedPosition2_f = new Vector2d(2, 4);

        MapDirection expectedOrient1_s = MapDirection.WEST;
        MapDirection expectedOrient2_s = MapDirection.EAST;
        MapDirection expectedOrient3_s = MapDirection.WEST;

        Vector2d expectedPosition1_s = new Vector2d(0, 2);
        Vector2d expectedPosition2_s = new Vector2d(4, 4 );
        Vector2d expectedPosition3_s = new Vector2d(0, 0);

        MapDirection expectedOrient1_t = MapDirection.NORTH;
        MapDirection expectedOrient2_t = MapDirection.SOUTH;
        MapDirection expectedOrient3_t = MapDirection.SOUTH;
        MapDirection expectedOrient4_t = MapDirection.WEST;
        MapDirection expectedOrient5_t = MapDirection.WEST;

        Vector2d expectedPosition1_t = new Vector2d(2, 3);
        Vector2d expectedPosition2_t = new Vector2d(3, 3);
        Vector2d expectedPosition3_t = new Vector2d(2, 0);
        Vector2d expectedPosition4_t = new Vector2d(2, 4);
        Vector2d expectedPosition5_t = new Vector2d(1, 1);


        //WHEN
        simulation1.run();
        simulation2.run();
        simulation3.run();


        //THEN
        Assertions.assertEquals(expectedOrient1_f, simulation1.getAnimals().get(0).getOrientation());
        Assertions.assertEquals(expectedOrient2_f, simulation1.getAnimals().get(1).getOrientation());
        Assertions.assertEquals(expectedPosition1_f, simulation1.getAnimals().get(0).getPosition());
        Assertions.assertEquals(expectedPosition2_f, simulation1.getAnimals().get(1).getPosition());

        Assertions.assertEquals(expectedOrient1_s, simulation2.getAnimals().get(0).getOrientation());
        Assertions.assertEquals(expectedOrient2_s, simulation2.getAnimals().get(1).getOrientation());
        Assertions.assertEquals(expectedOrient3_s, simulation2.getAnimals().get(2).getOrientation());
        Assertions.assertEquals(expectedPosition1_s, simulation2.getAnimals().get(0).getPosition());
        Assertions.assertEquals(expectedPosition2_s, simulation2.getAnimals().get(1).getPosition());
        Assertions.assertEquals(expectedPosition3_s, simulation2.getAnimals().get(2).getPosition());

        Assertions.assertEquals(expectedOrient1_t, simulation3.getAnimals().get(0).getOrientation());
        Assertions.assertEquals(expectedOrient2_t, simulation3.getAnimals().get(1).getOrientation());
        Assertions.assertEquals(expectedOrient3_t, simulation3.getAnimals().get(2).getOrientation());
        Assertions.assertEquals(expectedOrient4_t, simulation3.getAnimals().get(3).getOrientation());
        Assertions.assertEquals(expectedOrient5_t, simulation3.getAnimals().get(4).getOrientation());
        Assertions.assertEquals(expectedPosition1_t, simulation3.getAnimals().get(0).getPosition());
        Assertions.assertEquals(expectedPosition2_t, simulation3.getAnimals().get(1).getPosition());
        Assertions.assertEquals(expectedPosition3_t, simulation3.getAnimals().get(2).getPosition());
        Assertions.assertEquals(expectedPosition4_t, simulation3.getAnimals().get(3).getPosition());
        Assertions.assertEquals(expectedPosition5_t, simulation3.getAnimals().get(4).getPosition());
    }
}
