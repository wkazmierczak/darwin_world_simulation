package agh.ics.oop.model.worldElements;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

public record PositionDetails(Vector2d position,
                              MapDirection orientation) {
}
