package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{

    private int updateCnt = 0;
    @Override
    public void mapChanged(WorldMap<WorldElement, Vector2d> worldMap, String message) {
        updateCnt+=1;
        System.out.println("Update " + updateCnt + ": " + message);
        System.out.println(worldMap.toString());
    }
}
