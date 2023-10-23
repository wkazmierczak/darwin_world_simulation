package agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] tab1 = {"fk", "l",  "s",  "f",  "d",  "p"};
        String[] tab2 = {"b", "l",  "r",  "f",  "d",  "p"};
        String[] tab3 = {"fblr", "l",  "s",  "b",  "q",  "r"};
        String[] tab4 = {};
        MoveDirection[] sol1 = {MoveDirection.LEFT, MoveDirection.FORWARD};
        MoveDirection[] sol2 = {MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.FORWARD};
        MoveDirection[] sol3 = {MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        MoveDirection[] sol4 = {};


        MoveDirection[] res1 = OptionsParser.parse(tab1);
        MoveDirection[] res2 = OptionsParser.parse(tab2);
        MoveDirection[] res3 = OptionsParser.parse(tab3);
        MoveDirection[] res4 = OptionsParser.parse(tab4);

        Assertions.assertArrayEquals(sol1, res1);
        Assertions.assertArrayEquals(sol2, res2);
        Assertions.assertArrayEquals(sol3, res3);
        Assertions.assertArrayEquals(sol4, res4);

    }
}
