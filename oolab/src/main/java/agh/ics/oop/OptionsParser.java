package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.lang.reflect.Array;
import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] directions = new MoveDirection[args.length];
        int idx = 0;

        for (String arg: args) {
            MoveDirection direction = switch (arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l"-> MoveDirection.LEFT;
                default -> null;
            };
            if (direction != null){
                directions[idx] = direction;
                idx++;
            }
        }

        return Arrays.copyOfRange(directions, 0, idx);
    }
}
