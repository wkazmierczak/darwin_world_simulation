package agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.PositionAlreadyOccupiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] tab1 = {"fk", "l",  "s",  "f",  "d",  "p"};
        String[] tab2 = {"b", "l",  "r",  "f"};
        String[] tab3 = {"fblr", "l",  "s",  "b",  "q",  "r"};
        String[] tab4 = {};
        Exception sol1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {OptionsParser.parse(tab1);});
        List<MoveDirection> sol2 = new ArrayList<>(
                List.of(MoveDirection.BACKWARD,
                        MoveDirection.LEFT,
                        MoveDirection.RIGHT,
                        MoveDirection.FORWARD));
        Exception sol3 = Assertions.assertThrows(IllegalArgumentException.class, () -> {OptionsParser.parse(tab3);});
        List<MoveDirection> sol4 = new ArrayList<>();


//        MoveDirection[] res1 = OptionsParser.parse(tab1);
//        MoveDirection[] res2 = OptionsParser.parse(tab2);
//        MoveDirection[] res3 = OptionsParser.parse(tab3);
//        MoveDirection[] res4 = OptionsParser.parse(tab4);

//        Exception res1 = OptionsParser.parse(tab1);
//        List<MoveDirection> res2 = OptionsParser.parse(tab2);
//        Exception res3 = OptionsParser.parse(tab3);
//        List<MoveDirection> res4 = OptionsParser.parse(tab4);


        Assertions.assertEquals(sol1.getMessage(), "fk is not legal move specification");
        Assertions.assertEquals(sol2, OptionsParser.parse(tab2));
        Assertions.assertEquals(sol3.getMessage(), "fblr is not legal move specification");
        Assertions.assertEquals(sol4, OptionsParser.parse(tab4));

        Assertions.assertDoesNotThrow(()->OptionsParser.parse(tab2));
        Assertions.assertDoesNotThrow(()->OptionsParser.parse(tab4));

    }
}
