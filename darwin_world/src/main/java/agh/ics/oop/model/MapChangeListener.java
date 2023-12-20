package agh.ics.oop.model;

import agh.ics.oop.model.Maps.PlanetMap;
import agh.ics.oop.model.worldElements.WorldElement;

public interface MapChangeListener {
    void mapChanged(PlanetMap<WorldElement, Vector2d> worldMap, String message);

}
