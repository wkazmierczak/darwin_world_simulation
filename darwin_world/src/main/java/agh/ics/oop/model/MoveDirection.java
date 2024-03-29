package agh.ics.oop.model;

import java.util.Arrays;
import java.util.Optional;

public enum MoveDirection {
    FORWARD(0),
    FORWARD_RIGHT(1),
    RIGHT(2),
    BACKWARD_RIGHT(3),
    BACKWARD(4),
    BACKWARD_LEFT(5),
    LEFT(6),
    FORWARD_LEFT(7);

    private final int index;

    MoveDirection(int index) {
        this.index = index;
    }

    public static Optional<MoveDirection> valueOf(int index) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.index == index)
                .findFirst();
//    private final MoveDirection[] VALUES = MoveDirection.values();

//    public MoveDirection getFromIndex(int idx) throws IllegalArgumentException {
//        if (idx < 0 || idx >= VALUES.length)
//            throw new IllegalArgumentException();
//        return VALUES[idx];
//    }

    }

    public int getIndex() {
        return index;
    }
}
