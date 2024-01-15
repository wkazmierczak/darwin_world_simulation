package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.setupData.WorldSetupData;
import agh.ics.oop.model.worldElements.plants.BasicPlant;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class EquatorMap extends AbstractPlanetMap {

    private final Boundary equatorBounds;

    private final float equatorSurface = 0.2F;
    private final float equatorRatioToGrowNew = 0.8F;

    public EquatorMap(WorldSetupData setupData) {
        super(setupData);
        this.equatorBounds = getEquatorBounds();
        growPlants(getStartingPlantsCount());
    }

    public EquatorMap(int width, int height) {
        super(width, height);
        this.equatorBounds = getEquatorBounds();
        growPlants(getStartingPlantsCount());
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
        int onEquatorCount = Math.min(equatorBounds.getArea(), (int) new Random().doubles(0, 1).limit(count).filter(i -> i < equatorRatioToGrowNew).count());
        List<Vector2d> toBePlacedOnEquator = plantsPositionGenerator.getNFreePositionsWithinBounds(onEquatorCount, equatorBounds);
        List<Vector2d> toBePlacedNotOnEquator = plantsPositionGenerator.getFreePositionsNotInBounds(count - toBePlacedOnEquator.size(), equatorBounds);
        Stream.concat(toBePlacedOnEquator.stream(), toBePlacedNotOnEquator.stream()).forEach(p -> plants.put(p, new BasicPlant(getSetupData().energyAfterConsumingPlant())));
    }


    @Override
    public Boundary getSpecialAreaBounds() {
        return equatorBounds;
    }
}
