package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Vector2d;

public interface Teleporter {
    Vector2d moveIntoDirection(Vector2d base, Vector2d step);
}
