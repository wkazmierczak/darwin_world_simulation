package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.PositionAlreadyOccupiedException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> directions = new LinkedList<>();

        for (String arg: args) {
            MoveDirection direction = switch (arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l"-> MoveDirection.LEFT;
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            };

            directions.add(direction);

        }

        return directions;
    }
}
