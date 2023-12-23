package agh.ics.oop.model;

import agh.ics.oop.model.maps.PlanetMap;

import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.WorldElement;

public interface MapChangeListener {
    void mapChanged(PlanetMap<Animal, Vector2d> worldMap, String message); //zmiana na animal

}
