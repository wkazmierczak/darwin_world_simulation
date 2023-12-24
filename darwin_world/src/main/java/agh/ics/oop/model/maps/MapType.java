package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.Animal;

public enum MapType {
    EQUATOR_MAP,
    POISONOUS_MAP;

    public PlanetMap<Animal, Vector2d> createPlanetMap(int width, int height, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant) { // TODO typ Animal do weryfikacji
        return switch (this) {
            case EQUATOR_MAP -> new EquatorMap(width, height, startingPlantsCount, everyDayPlantsCount,energyAfterConsumingPlant);
            case POISONOUS_MAP -> new PoisonousMap(width, height, startingPlantsCount, everyDayPlantsCount,energyAfterConsumingPlant);
        };
    }
}
