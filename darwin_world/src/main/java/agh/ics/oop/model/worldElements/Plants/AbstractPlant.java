package agh.ics.oop.model.worldElements.Plants;

abstract class AbstractPlant {
    protected final int energyAfterConsuming;

    protected AbstractPlant(int energyAfterConsuming) {
        this.energyAfterConsuming = energyAfterConsuming;
    }
}
