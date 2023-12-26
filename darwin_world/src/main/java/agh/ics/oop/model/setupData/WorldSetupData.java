package agh.ics.oop.model.setupData;

public record WorldSetupData(
        int width,
        int height,
        int getPlantsPerDay,
        int energyAfterConsumingPlant,
        int startingPlantsCount) {
    public WorldSetupData(SimulationSetupData data) {
        this(data.width(), data.height(), data.plantsPerDay(), data.energyAfterConsumingPlant(), data.startingPlantsCount());
    }
}
