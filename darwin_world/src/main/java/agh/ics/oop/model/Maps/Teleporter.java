package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.Plants.Plant;

public interface Teleporter {
    Vector2d moveIntoDirection(Vector2d base, Vector2d step);

    Plant plantAt(Vector2d position);

}

