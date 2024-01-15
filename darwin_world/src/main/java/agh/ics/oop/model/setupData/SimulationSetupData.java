package agh.ics.oop.model.setupData;

import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.util.MyRange;

public record SimulationSetupData(int width,
                                  int height,
                                  int maxDays,
                                  int startingPlantsCount,
                                  int energyAfterConsumingPlant,
                                  int plantsPerDay,
                                  int initialNumOfAnimals,
                                  int initialAnimalEnergy,
                                  int reproduceEnergyMin,
                                  int energySpendToReproduce,
                                  MyRange mutationCountRange,
                                  int genotypeLength,
                                  GenotypeType genotypeType,
                                  MapType mapType,
                                  int delay) {
}
