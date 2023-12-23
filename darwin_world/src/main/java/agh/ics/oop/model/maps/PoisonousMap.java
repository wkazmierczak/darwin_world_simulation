package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.plants.BasicPlant;
import agh.ics.oop.model.worldElements.plants.Plant;
import agh.ics.oop.model.worldElements.plants.PoisonousPlant;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class PoisonousMap extends AbstractPlanetMap {
    private final Boundary poisonsAreaBounds;

    private float poisonousAreaSurface = 0.2F;
    private final float poisonousAreaRatioToGrowNew = 0.5F;

    public PoisonousMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant) {
        super(width, height, startingPlantsCount, everyDayPlantsCount, energyAfterConsumingPlant);
        poisonsAreaBounds = getPoisonsAreaBounds();
        growPlants(startingPlantsCount);
    }

    public PoisonousMap(int width, int height) {
        super(width, height);
        poisonsAreaBounds = getPoisonsAreaBounds();
        growPlants(startingPlantsCount);
    }

    private Boundary getPoisonsAreaBounds() {
        int xl = boundary.bottomLeft().getX();
        int xr = boundary.upperRight().getY();
        int yd = boundary.bottomLeft().getY();
        int yu = boundary.upperRight().getY();
        int poisonousAreaSide = (int) Math.sqrt((xr - xl) * (yu - yd) * poisonousAreaSurface);
        PlantsPositionGenerator plantsPositionGenerator = new PlantsPositionGenerator(plants, boundary);
        Predicate<Vector2d> isGoodPlaceForPoisonedArea = (Vector2d bottomLeft) -> {
            Vector2d upperRight = bottomLeft.add(new Vector2d(poisonousAreaSide, poisonousAreaSide));
            return bottomLeft.inBounds(boundary) && upperRight.inBounds(boundary);
        };
        return plantsPositionGenerator.getAllPositionsShuffled().filter(isGoodPlaceForPoisonedArea).findFirst().map(bottomLeft -> new Boundary(bottomLeft, bottomLeft.add(new Vector2d(poisonousAreaSide, poisonousAreaSide)))).orElseThrow(() -> new RuntimeException("Cannot find place for poisoned area"));
    }

    @Override
    public void growPlants(int count) {
        PlantsPositionGenerator plantsPositionGenerator = new PlantsPositionGenerator(plants, boundary);

        List<Vector2d> positions = plantsPositionGenerator.getNFreePositions(count);
        for (var position : positions) {
            Plant newPlant = new BasicPlant(energyAfterConsumingPlant);
            if (position.inBounds(poisonsAreaBounds)) {
                if (new Random().nextDouble(0, 1) < poisonousAreaRatioToGrowNew) {
                    newPlant = new PoisonousPlant(energyAfterConsumingPlant);
                }
            }
            plants.put(position, newPlant);
        }

    }

}
