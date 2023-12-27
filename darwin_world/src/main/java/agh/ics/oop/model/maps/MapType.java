package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.setupData.WorldSetupData;
import agh.ics.oop.model.worldElements.Animal;

public enum MapType {
    EQUATOR_MAP,
    POISONOUS_MAP;

    public PlanetMap createPlanetMap(WorldSetupData data) { // TODO typ Animal do weryfikacji
        return switch (this) {
            case EQUATOR_MAP ->
                    new EquatorMap(data);
            case POISONOUS_MAP ->
                    new PoisonousMap(data);
        };
    }
}
