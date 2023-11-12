package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap implements WorldMap<WorldElement, Vector2d>{

    private final int numOfTufts;

    private final Map<Vector2d, WorldElement> tufts = new HashMap<>();

    private Vector2d lowerLeft = new Vector2d(0, 0);
    private Vector2d upperRight = new Vector2d(0, 0);


    public GrassField(Integer numOfTufts){
        this.numOfTufts = numOfTufts;
        placeTufts(numOfTufts);
    }

    private void placeTufts(int numOfTufts){
        int maxWidth = (int)Math.sqrt(numOfTufts * 10)+1;
        int maxHeight = (int)Math.sqrt(numOfTufts * 10)+1;

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, numOfTufts);
        for(Vector2d grassPosition : randomPositionGenerator) {
            tufts.put(grassPosition, new Grass(grassPosition));
        }

//        naive version

//        Random rand = new Random();
//
//        int size = (int)Math.sqrt(numOfTufts * 10)+1;
//        int i = 0;
//        while (i < numOfTufts) {
//            int x = rand.nextInt(size);
//            int y = rand.nextInt(size);
//            Vector2d v = new Vector2d(x, y);
//            if (!tufts.containsKey(v)){
//                i+=1;
//                tufts.put(v, new Grass(v));
//                if (v.follows(upperRight)){
//                    upperRight = v;
//                }
//            }
//        }

    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if (object != null) {
            return object;
        }
        else{
            return tufts.get(position);
        }
    }


    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);

        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Map.Entry<Vector2d, WorldElement> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();

            upperRight = key.upperRight(upperRight);
            lowerLeft = key.lowerLeft(lowerLeft);

        }

        for (Map.Entry<Vector2d, WorldElement> ent2 : tufts.entrySet()) {
            Vector2d key = ent2.getKey();

            upperRight = key.upperRight(upperRight);
            lowerLeft = key.lowerLeft(lowerLeft);
        }

        return visualizer.draw(lowerLeft, upperRight);

    }

    @Override
    public Collection<WorldElement> getElements(){
        Collection<WorldElement> res = super.getElements();
        res.addAll(tufts.values());
        return res;
    }

    public Map<Vector2d, WorldElement> getTufts() {
        return tufts;
    }

    public Integer getNumOfTufts() {
        return numOfTufts;

    }

    public Map<Vector2d, WorldElement> getAnimals() {
        return animals;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }
}
