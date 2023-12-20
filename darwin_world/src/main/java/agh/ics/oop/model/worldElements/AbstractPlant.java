package agh.ics.oop.model.worldElements;

abstract class AbstractPlant {
    protected final int energyAfterConsuming;

    protected AbstractPlant(int energyAfterConsuming) {
        this.energyAfterConsuming = energyAfterConsuming;
    }
}
