package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.BasicPlant;
import agh.ics.oop.model.worldElements.Plant;

import java.util.*;
import java.util.stream.Collectors;

public class EquatorMap extends AbstractPlanetMap {

    private final Boundary equatorBounds;

    protected EquatorMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount) {
        super(width, height, startingPlantsCount, everyDayPlantsCount);
        this.equatorBounds = getEquatorBounds();
    }

    protected EquatorMap(int width, int height) {
        super(width, height);
        this.equatorBounds = getEquatorBounds();
    }

    private Boundary getEquatorBounds() {
        int xl = boundary.bottomLeft().getX();
        int xr = boundary.upperRight().getY();
        int yd = boundary.bottomLeft().getY();
        int yu = boundary.upperRight().getY();
        int yMid = (yu + yd) / 2;
        int range = (int) Math.floor(Math.abs(yu - yd) * 0.1);
        return new Boundary(new Vector2d(xl, yMid - range), new Vector2d(xr, yMid + range));
    }

    @Override
    public void growPlants(int count) {
//        RandomPositionGenerator generator = new RandomPositionGenerator(getWidth(), getHeight());
        List<Vector2d> positions = new ArrayList<>(getWidth() * getHeight());
        for (int i = getCurrentBounds().bottomLeft().getX(); i < getCurrentBounds().upperRight().getX(); i++) {
            for (int j = getCurrentBounds().bottomLeft().getY(); j < getCurrentBounds().upperRight().getY(); j++) {
                positions.add(new Vector2d(i, j));
            }
        }
        Collections.shuffle(positions);

        List<Boolean> onEquator = new Random().doubles(0, 1).mapToObj(i -> i < 0.8).limit(count).collect(Collectors.toCollection(ArrayList<Boolean>::new));
        for (var placeOnEquator : onEquator) {
            Plant newPlant = new BasicPlant(energyAfterConsumingPlant);
            int i = 0;
            Vector2d newPosition = null;
            while (i < positions.size()) {
                Vector2d current = positions.get(i);
                if (plantAt(current) == null) {
                    if (placeOnEquator) {
                        if (current.inBounds(equatorBounds)) {
                            newPosition = current;
                            positions.remove(i);
                            break;
                        }
                    } else {
                        if (!current.inBounds(equatorBounds)) {
                            newPosition = current;
                            positions.remove(i);
                            break;
                        }
                    }
                }
                i++;
            }
            if (newPosition == null) return;
            plants.put(newPosition, newPlant);
        }
    }

}
