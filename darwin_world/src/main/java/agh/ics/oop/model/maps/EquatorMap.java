package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.plants.BasicPlant;

import java.util.*;
import java.util.stream.Stream;

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
        PlantsPositionGenerator plantsPositionGenerator = new PlantsPositionGenerator(plants, boundary);
        int onEquatorCount = (int) new Random().doubles(0, 1).limit(count).filter(i -> i < equatorRatioToGrowNew).count();
        List<Vector2d> positions = Stream.concat(plantsPositionGenerator.getNFreePositionsWithinBounds(onEquatorCount, equatorBounds).stream(), plantsPositionGenerator.getFreePositionsNotInBounds(count - onEquatorCount, equatorBounds).stream()).toList();
        positions.forEach(p -> plants.put(p, new BasicPlant(energyAfterConsumingPlant)));
    }

}
