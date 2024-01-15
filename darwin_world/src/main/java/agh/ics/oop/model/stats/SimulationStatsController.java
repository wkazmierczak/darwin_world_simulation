package agh.ics.oop.model.stats;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.worldElements.Animal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimulationStatsController {
    private final Simulation simulation;
    private int dayOfSimulation = 0;

    private final List<Integer> lifeSpansOfDeadAnimals = new LinkedList<>();


    public SimulationStatsController(Simulation simulation) {
        this.simulation = simulation;
    }

    public void nextDay() {
        dayOfSimulation++;
    }



    public int getNumOfAnimals() {
        return simulation.getAnimals().size();
    }

    public int getNumOfPlants() {
        return simulation.getWorldMap().getPlantsCount();

    }

    public int getFreePositionsCount(PlanetMap map) {
        return map.getFreePositionsCount();
    }

    public List<Integer> getMostPopularGenotype() {
        Map<List<Integer>, Long> genotypeCounts = simulation.getAnimals().stream()
                .map(animal -> animal.getGenotype().getGenotypeList())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        return genotypeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public float getAvgLifespanForDeadAnimals() {
        return (float) lifeSpansOfDeadAnimals.stream().reduce(0, Integer::sum) / lifeSpansOfDeadAnimals.size();
    }

    public void newDeath(Animal dead) {
        lifeSpansOfDeadAnimals.add(dead.getStats().getAge());
    }

    public double getAvgEnergyLevel() {
        return simulation.getAnimals().stream()
                .mapToInt(Animal::getEnergyLevel)
                .average()
                .orElse(0.0);
    }

    public double getAvgNumOfChildrenForAliveAnimals(List<Animal> aliveAnimals) {
        return aliveAnimals.stream()
                .mapToInt(animal -> animal.getStats().getChildrenCount())
                .average()
                .orElse(0.0);
    }

    public int getDayOfSimulation() {
        return dayOfSimulation;
    }

}
