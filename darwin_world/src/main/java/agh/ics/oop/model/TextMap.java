package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TextMap implements WorldMap<String, Integer> {

    private final List<String> stringList = new ArrayList<>();
    private final List<MapDirection> orientList = new ArrayList<>();

    @Override
    public void place(String text){
        stringList.add(text);
        orientList.add(MapDirection.EAST);
    }

    @Override
    public void move(String text, MoveDirection direction) {
        int currIdx = stringList.indexOf(text);
        MapDirection currOrient = orientList.get(currIdx);
        String flag = "";

        switch (direction) {
            case RIGHT -> orientList.set(currIdx, currOrient.next());
            case LEFT -> orientList.set(currIdx, currOrient.previous());
            case FORWARD -> {
                if (currOrient == MapDirection.EAST && canMoveTo(currIdx + 1)) {
                    flag = "east move";


                } else if (currOrient == MapDirection.WEST && canMoveTo(currIdx - 1)) {
                    flag = "west move";

                }
            }
            case BACKWARD -> {
                if (currOrient == MapDirection.EAST && canMoveTo(currIdx - 1)) {
                    flag = "west move";

                } else if (currOrient == MapDirection.WEST && canMoveTo(currIdx + 1)) {
                    flag = "east move";
                }
            }
        }

        if (flag == "east move"){
            String neighbor = stringList.get(currIdx + 1);
            MapDirection neighOrient = orientList.get(currIdx + 1);

            stringList.set(currIdx, neighbor);
            stringList.set(currIdx + 1, text);
            orientList.set(currIdx, neighOrient);
            orientList.set(currIdx + 1, currOrient);
        }

        else if (flag == "west move"){
            String neighbor = stringList.get(currIdx - 1);
            MapDirection neighOrient = orientList.get(currIdx - 1);

            stringList.set(currIdx, neighbor);
            stringList.set(currIdx - 1, text);
            orientList.set(currIdx, neighOrient);
            orientList.set(currIdx - 1, currOrient);
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        return 0<=position && position<stringList.size();
    }

    @Override
    public String objectAt(Integer position) {
        return stringList.get(position);
    }

    @Override
    public Collection<String> getElements() {
        return stringList;
    }

    @Override
    public Boundary getCurrentBounds() {
        return null;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position >= 0 && position < stringList.size();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[ ");
        for (String elem: stringList){
            res.append("\"").append(elem).append("\"").append(", ");
        }
        res = new StringBuilder(res.substring(0, res.length() - 2) + " ]");
        return res.toString();
    }

    public List<MapDirection> getOrientList() {
        return orientList;
    }

    public List<String> getStringList() {
        return stringList;
    }
}
