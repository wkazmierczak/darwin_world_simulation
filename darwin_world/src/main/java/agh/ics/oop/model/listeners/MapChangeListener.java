package agh.ics.oop.model.listeners;

import agh.ics.oop.model.maps.PlanetMap;

public interface MapChangeListener {
    void mapChanged(PlanetMap worldMap, String message);

}
