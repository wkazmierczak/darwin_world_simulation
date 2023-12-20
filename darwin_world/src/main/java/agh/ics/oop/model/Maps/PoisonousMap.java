package agh.ics.oop.model.Maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.Plants.BasicPlant;
import agh.ics.oop.model.worldElements.Plants.Plant;
import agh.ics.oop.model.worldElements.Plants.PoisonousPlant;

import java.util.List;
import java.util.Random;

public class PoisonousMap extends AbstractPlanetMap {
    private final Boundary poisonsAreaBounds;

    private float poisonousAreaSurface = 0.2F;
    private final float poisonousAreaRatioToGrowNew = 0.5F;

    protected PoisonousMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant) {
        super(width, height, startingPlantsCount, everyDayPlantsCount, energyAfterConsumingPlant);
        poisonsAreaBounds = getPoisonsAreaBounds();
    }

    protected PoisonousMap(int width, int height) {
        super(width, height);

        poisonsAreaBounds = getPoisonsAreaBounds();
    }

    private Boundary getPoisonsAreaBounds() {
        int xl = boundary.bottomLeft().getX();
        int xr = boundary.upperRight().getY();
        int yd = boundary.bottomLeft().getY();
        int yu = boundary.upperRight().getY();
        int poisonousAreaSide = (int) Math.sqrt((xr - xl) * (yu - yd) * poisonousAreaSurface);
        List<Vector2d> positions = getAllPositionsShuffled();
        for (var position : positions) {
            Vector2d bottomLeft = position;
            Vector2d upperRight = position.add(new Vector2d(poisonousAreaSide, poisonousAreaSide));
            if (bottomLeft.inBounds(boundary) && upperRight.inBounds(boundary)) {
                return new Boundary(bottomLeft, upperRight);
            }
        }
        throw new RuntimeException("Cannot find place for poisoned area");
    }

    @Override
    public void growPlants(int count) {
        List<Vector2d> positions = getAllPositionsShuffled();
        int placed = 0;
        for (var position : positions) {
            if (placed == count) return;
            Plant newPlant = new BasicPlant(energyAfterConsumingPlant);
            if (position.inBounds(poisonsAreaBounds)) {
                if (new Random().nextDouble(0, 1) < poisonousAreaRatioToGrowNew) {
                    newPlant = new PoisonousPlant(energyAfterConsumingPlant);
                }
            }
            int i = 0;
            Vector2d newPosition = null;
            while (i < positions.size()) {
                Vector2d current = positions.get(i);
                if (plantAt(current) == null) {
                    newPosition = current;
                    positions.remove(i);
                    placed++;
                    break;
                }
                i++;
            }
            if (newPosition == null) return;
            plants.put(newPosition, newPlant);
        }

    }
}
