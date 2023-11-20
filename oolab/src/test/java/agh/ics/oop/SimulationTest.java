package agh.ics.oop;


import agh.ics.oop.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class SimulationTest {

    @Test
    public void simulationTest(){
        //GIVEN
        String[] input1 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        String[] input2 = {"f", "b", "r", "l", "t", "f", "r", "l", "f", "f", "f", "f", "f", "r", "f", "f",
                "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        String[] input3 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "l", "f", "r", "f", "f", "b", "b"};

        List<MoveDirection> directions1 = OptionsParser.parse(input1);
        Exception sol2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {OptionsParser.parse(input2);});
        List<MoveDirection> directions3 = OptionsParser.parse(input3);

        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(1, 2));
        List<Vector2d> positions3 = List.of(new Vector2d(2,2), new Vector2d(3,4),
                new Vector2d(2,0), new Vector2d(4,4), new Vector2d(0, 0));
        RectangularMap map1 = new RectangularMap(4, 4);
        RectangularMap map2 = new RectangularMap(5, 3);
        RectangularMap map3 = new RectangularMap(9, 7);

        GrassField map4 = new GrassField(5);
        GrassField map5 = new GrassField(10);
        GrassField map6 = new GrassField(15);

        Simulation simulation1 = new Simulation(directions1, positions1, map1);
        Assertions.assertEquals(sol2.getMessage(), "t is not legal move specification");
        Simulation simulation3 = new Simulation(directions3, positions3, map3);

        Simulation sim4 = new Simulation(directions1, positions1, map4);
        Simulation sim5 = new Simulation(directions1, positions1, map5);
        Simulation sim6 = new Simulation(directions1, positions1, map6);

        MapDirection expectedOrient1_f = MapDirection.SOUTH;
        MapDirection expectedOrient2_f = MapDirection.NORTH;

        Vector2d expectedPosition1_f = new Vector2d(2, 0);
        Vector2d expectedPosition2_f = new Vector2d(3, 4);

        MapDirection expectedOrient1_s = MapDirection.EAST;
//        MapDirection expectedOrient2_s poza mapą
        MapDirection expectedOrient3_s = MapDirection.SOUTH;

        Vector2d expectedPosition1_s = new Vector2d(5, 3);
//        Vector2d expectedPosition2_s = new Vector2d(3,4); poza mapą
        Vector2d expectedPosition3_s = new Vector2d(2, 0);

        MapDirection expectedOrient1_t = MapDirection.NORTH;
        MapDirection expectedOrient2_t = MapDirection.SOUTH;
        MapDirection expectedOrient3_t = MapDirection.SOUTH;
        MapDirection expectedOrient4_t = MapDirection.WEST;
        MapDirection expectedOrient5_t = MapDirection.WEST;

        Vector2d expectedPosition1_t = new Vector2d(2, 5);
        Vector2d expectedPosition2_t = new Vector2d(3, 3);
        Vector2d expectedPosition3_t = new Vector2d(2, 0);
        Vector2d expectedPosition4_t = new Vector2d(2, 4);
        Vector2d expectedPosition5_t = new Vector2d(1, 1);

        String exp_map1 =
                " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | | | |^| |" + System.lineSeparator() +
                "  3: | | | | | |" + System.lineSeparator() +
                "  2: | | | | | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | |v| | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();

        String exp_map2 =
                " y\\x  0 1 2 3 4 5" + System.lineSeparator() +
                "  4: -------------" + System.lineSeparator() +
                "  3: | | | | | |>|" + System.lineSeparator() +
                "  2: | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | |" + System.lineSeparator() +
                "  0: | | |v| | | |" + System.lineSeparator() +
                " -1: -------------" + System.lineSeparator();

        String exp_map3 =
                " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  8: ---------------------" + System.lineSeparator() +
                "  7: | | | | | | | | | | |" + System.lineSeparator() +
                "  6: | | | | | | | | | | |" + System.lineSeparator() +
                "  5: | | |^| | | | | | | |" + System.lineSeparator() +
                "  4: | | |<| | | | | | | |" + System.lineSeparator() +
                "  3: | | | |v| | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | |<| | | | | | | | |" + System.lineSeparator() +
                "  0: | | |v| | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();




        //WHEN
        simulation1.run();
//        simulation2.run();
        simulation3.run();

        sim4.run();
        sim5.run();
        sim6.run();


        //THEN
        Assertions.assertEquals(expectedOrient1_f, simulation1.getAnimals().get(0).getOrientation());
        Assertions.assertEquals(expectedOrient2_f, simulation1.getAnimals().get(1).getOrientation());
        Assertions.assertEquals(expectedPosition1_f, simulation1.getAnimals().get(0).getPosition());
        Assertions.assertEquals(expectedPosition2_f, simulation1.getAnimals().get(1).getPosition());

        for (Map.Entry<Vector2d, WorldElement> entry: map1.getAnimals().entrySet()){
            Vector2d key = entry.getKey();
            WorldElement value = entry.getValue();
            Assertions.assertEquals(value.getPosition(), key);
        }



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

        for (Map.Entry<Vector2d, WorldElement> entry: map3.getAnimals().entrySet()){
            Vector2d key = entry.getKey();
            WorldElement value = entry.getValue();
            Assertions.assertEquals(value.getPosition(), key);
        }

        Assertions.assertEquals(exp_map1, map1.toString());
//        Assertions.assertEquals(exp_map2, map2.toString());
        Assertions.assertEquals(exp_map3, map3.toString());


        for (Map.Entry<Vector2d, WorldElement> entry: map4.getAnimals().entrySet()){
            Vector2d key = entry.getKey();
            WorldElement value = entry.getValue();
            Assertions.assertEquals(value.getPosition(), key);
        }

        for (Map.Entry<Vector2d, WorldElement> entry: map5.getAnimals().entrySet()){
            Vector2d key = entry.getKey();
            WorldElement value = entry.getValue();
            Assertions.assertEquals(value.getPosition(), key);
        }

        for (Map.Entry<Vector2d, WorldElement> entry: map6.getAnimals().entrySet()){
            Vector2d key = entry.getKey();
            WorldElement value = entry.getValue();
            Assertions.assertEquals(value.getPosition(), key);
        }
    }
}
