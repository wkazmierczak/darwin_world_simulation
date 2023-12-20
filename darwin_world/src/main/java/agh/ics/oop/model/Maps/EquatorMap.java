package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.BasicPlant;
import agh.ics.oop.model.worldElements.Plant;

import java.util.*;
import java.util.stream.Collectors;

public class EquatorMap extends AbstractPlanetMap {

    private final Boundary equatorBounds;

    private final float equatorSurface = 0.2F;
    private final float equatorRatioToGrowNew = 0.8F;

    protected EquatorMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant) {
        super(width, height, startingPlantsCount, everyDayPlantsCount, energyAfterConsumingPlant);
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
        int range = (int) Math.floor(Math.abs(yu - yd) * equatorSurface / 2);
        return new Boundary(new Vector2d(xl, yMid - range), new Vector2d(xr, yMid + range));

    }

    @Override
    public void growPlants(int count) {
//        RandomPositionGenerator generator = new RandomPositionGenerator(getWidth(), getHeight());
        List<Vector2d> positions = getAllPositionsShuffled();

        List<Boolean> onEquator = new Random().doubles(0, 1).mapToObj(i -> i < equatorRatioToGrowNew).limit(count).collect(Collectors.toCollection(ArrayList<Boolean>::new));
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
