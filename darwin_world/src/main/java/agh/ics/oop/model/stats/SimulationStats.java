package agh.ics.oop.model.stats;

import agh.ics.oop.model.genotype.Genotype;

public class SimulationStats {
    private int dayOfSimulation = 0;
    private int totalAgeOfDead = 0;
    private int totalAnimals = 0;
    private int totalFreeSlots = 0;
    private int deadCount = 0;

    public int getDayOfSimulation() {
        return dayOfSimulation;
    }
}
