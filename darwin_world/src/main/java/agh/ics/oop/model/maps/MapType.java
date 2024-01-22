package agh.ics.oop.model.maps;

import agh.ics.oop.model.setupData.WorldSetupData;

public enum MapType {
    EQUATOR_MAP,
    POISONOUS_MAP;

    public PlanetMap createPlanetMap(WorldSetupData data) {
        return switch (this) {
            case EQUATOR_MAP ->
                    new EquatorMap(data);
            case POISONOUS_MAP ->
                    new PoisonousMap(data);
        };
    }
}
