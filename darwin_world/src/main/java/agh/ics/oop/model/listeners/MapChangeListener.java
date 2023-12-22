package agh.ics.oop.model.listeners;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.maps.PlanetMap;

import agh.ics.oop.model.worldElements.WorldElement;

public interface MapChangeListener {
    void mapChanged(PlanetMap<WorldElement, Vector2d> worldMap, String message);

}
