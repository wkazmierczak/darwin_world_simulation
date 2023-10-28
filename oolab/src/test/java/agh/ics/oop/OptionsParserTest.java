package agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] tab1 = {"fk", "l",  "s",  "f",  "d",  "p"};
        String[] tab2 = {"b", "l",  "r",  "f",  "d",  "p"};
        String[] tab3 = {"fblr", "l",  "s",  "b",  "q",  "r"};
        String[] tab4 = {};
        List<MoveDirection> sol1 = new ArrayList<>(
                List.of(MoveDirection.LEFT,
                        MoveDirection.FORWARD));
        List<MoveDirection> sol2 = new ArrayList<>(
                List.of(MoveDirection.BACKWARD,
                        MoveDirection.LEFT,
                        MoveDirection.RIGHT,
                        MoveDirection.FORWARD));
        List<MoveDirection> sol3 = new ArrayList<>(
                List.of(MoveDirection.LEFT,
                        MoveDirection.BACKWARD,
                        MoveDirection.RIGHT));
        List<MoveDirection> sol4 = new ArrayList<>();


//        MoveDirection[] res1 = OptionsParser.parse(tab1);
//        MoveDirection[] res2 = OptionsParser.parse(tab2);
//        MoveDirection[] res3 = OptionsParser.parse(tab3);
//        MoveDirection[] res4 = OptionsParser.parse(tab4);

        List<MoveDirection> res1 = OptionsParser.parse(tab1);
        List<MoveDirection> res2 = OptionsParser.parse(tab2);
        List<MoveDirection> res3 = OptionsParser.parse(tab3);
        List<MoveDirection> res4 = OptionsParser.parse(tab4);


        Assertions.assertEquals(sol1, res1);
        Assertions.assertEquals(sol2, res2);
        Assertions.assertEquals(sol3, res3);
        Assertions.assertEquals(sol4, res4);

    }
}
