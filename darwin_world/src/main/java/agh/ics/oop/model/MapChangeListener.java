package agh.ics.oop.model;

import agh.ics.oop.model.Maps.WorldMap;
import agh.ics.oop.model.worldElements.WorldElement;

public interface MapChangeListener {
    void mapChanged(WorldMap<WorldElement, Vector2d> worldMap, String message);

}
