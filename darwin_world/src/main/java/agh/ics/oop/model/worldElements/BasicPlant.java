package agh.ics.oop.model.worldElements;

public class BasicPlant extends AbstractPlant implements Plant {

    public BasicPlant(int energyAfterConsuming) {
        super(energyAfterConsuming);
    }

    @Override
    public int getNutritious() {
        return energyAfterConsuming;
    }

    @Override
    public boolean isPoisonous() {
        return false;
    }
}
