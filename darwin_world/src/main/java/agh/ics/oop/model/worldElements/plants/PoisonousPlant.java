package agh.ics.oop.model.worldElements.plants;

public class PoisonousPlant extends AbstractPlant implements Plant {
    public PoisonousPlant(int energyAfterConsuming) {
        super(energyAfterConsuming);
    }

    @Override
    public int getNutritious() {
        return -energyAfterConsuming;
    }

    @Override
    public boolean isPoisonous() {
        return true;
    }
}
