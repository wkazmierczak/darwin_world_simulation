package agh.ics.oop.model.listeners;

import agh.ics.oop.model.worldElements.Animal;

public class AnimalTracker implements AnimalChangeListener{
    @Override
    public synchronized void animalInfoChanged(Animal animal) {
//        TODO do adaptacji z GUI

        System.out.println("Genotype: " + animal.getGenotype().getGenotypeList());
        System.out.println("Active genome: " + animal.getGenotype().next());
        System.out.println("Energy level: " + animal.getEnergyLevel());
        System.out.println("Number of eaten plants: " + animal.getStats().getPlantsEaten());
        System.out.println("Number of children: " + animal.getStats().getChildren().size());
        System.out.println("Number of descendants: " + animal.getStats().getDescendantsCount());
        if (animal.isDead()){
            System.out.println("Day of death: " + animal.getStats().getDayOfDeath());
        }
        else {
            System.out.println("Age (in days): " + animal.getStats().getAge());
        }
        System.out.println("-----------------------");
    }
}
