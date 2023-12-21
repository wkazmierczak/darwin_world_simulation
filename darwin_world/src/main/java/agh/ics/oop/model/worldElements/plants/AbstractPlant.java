package agh.ics.oop.model.worldElements.plants;

abstract class AbstractPlant {
    protected final int energyAfterConsuming;

    protected AbstractPlant(int energyAfterConsuming) {
        this.energyAfterConsuming = energyAfterConsuming;
    }
}
