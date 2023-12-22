package agh.ics.oop.model.listeners;


import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.MapChangeListener;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.worldElements.WorldElement;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updateCnt = 0;
    @Override
    public synchronized void mapChanged(PlanetMap<WorldElement, Vector2d> worldMap, String message) {
        updateCnt+=1;
        System.out.println("Update " + updateCnt + ": " + message);
        System.out.println("Map UUID: " + worldMap.getId());
        System.out.println(worldMap);
    }
}
