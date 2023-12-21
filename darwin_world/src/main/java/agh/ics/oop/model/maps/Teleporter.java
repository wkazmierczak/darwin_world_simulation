package agh.ics.oop.model.maps;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.PositionDetails;
import agh.ics.oop.model.worldElements.plants.Plant;

public interface Teleporter {
    PositionDetails moveIntoDirection(Vector2d basePosition, MapDirection baseOrientation, Vector2d step);

    Plant plantAt(Vector2d position);

}

