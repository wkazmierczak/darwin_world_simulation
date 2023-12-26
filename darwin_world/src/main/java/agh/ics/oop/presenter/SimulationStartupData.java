package agh.ics.oop.presenter;

import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.MapType;

public record SimulationStartupData(int width,
                                    int height,
                                    int initialNumOfAnimals,
                                    int startingPlantsCount,
                                    int everyDayPlantsCount,
                                    int energyAfterConsumingPlant,
                                    int genotypeLength,
                                    GenotypeType genotypeType,
                                    MapType mapType) {
}
