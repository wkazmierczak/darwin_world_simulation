package agh.ics.oop.model.setupData;

import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.util.MyRange;

public record AnimalSetupData(
        int energyAfterConsumingPlant,
        int initialAnimalEnergy,
        int reproduceEnergyMin,
        int energySpendToReproduce,
        MyRange mutationsRange,
        int genotypeLength,
        GenotypeType genotypeType
) {
    public AnimalSetupData(SimulationSetupData data) {
        this(
                data.energyAfterConsumingPlant(),
                data.initialAnimalEnergy(),
                data.reproduceEnergyMin(),
                data.energySpendToReproduce(),
                data.mutationCountRange(),
                data.genotypeLength(),
                data.genotypeType()
        );
    }
}
