package agh.ics.oop.model.listeners;


import agh.ics.oop.model.maps.PlanetMap;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updateCnt = 0;

    @Override
    public synchronized void mapChanged(PlanetMap worldMap, String message) { // zmiana na animal
        updateCnt += 1;
        System.out.println("Update " + updateCnt + ": " + message);
        System.out.println("Map UUID: " + worldMap.getId());
        System.out.println(worldMap);
    }
}
