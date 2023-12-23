package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.stats.AnimalStats;
import agh.ics.oop.model.stats.SimulationStats;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.genotype.BasicGenotype;
import agh.ics.oop.model.worldElements.WorldElement;
import agh.ics.oop.model.worldElements.plants.BasicPlant;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation implements Runnable {
    private final PlanetMap<Animal, Vector2d> worldMap; //TODO WorldElement ?
    private final List<Animal> animals;
    private int dayOfSimulation;
    private SimulationStats stats;

    public Simulation(int initialNumOfAnimals, PlanetMap<Animal, Vector2d> worldMap) {
        this.animals = new ArrayList<>();

        this.stats = new SimulationStats(initialNumOfAnimals, worldMap.getStartingPlantsCount());

        this.worldMap = worldMap;
        List<Vector2d> tab1 = new ArrayList<>(List.of(new Vector2d(1, 2), new Vector2d(3, 3)));

        for (Vector2d pos : tab1) {
//        for (Vector2d pos : generateRandomVector2ds(initialNumOfAnimals)) {
                Animal animal = new Animal(pos, new BasicGenotype(5));
                try {
                    worldMap.place(animal);
                    this.animals.add(animal);
                } catch (
                        PositionAlreadyOccupiedException ignored) {
                }
        }
    }

    public List<Vector2d> generateRandomVector2ds(int n) {
        Random random = new Random();

        return Stream.generate(() ->
                        new Vector2d(random.nextInt(worldMap.getCurrentBounds().getWidth()), random.nextInt(random.nextInt(worldMap.getCurrentBounds().getHeight()))))
                .distinct()
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        int numOfAnimals = animals.size();
        int i = 0;
        while (i<10){ // TODO number only for test
            worldMap.move(animals.get(i % numOfAnimals));
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            i++;

//            TODO usuwanie ciał
//            TODO nextDay() z Animal
//            TODO wzrost nowych roślin
        }

    }

    public List<Animal> getAnimals() {
        return animals;
    }


    public int getDayOfSimulation() {
        return dayOfSimulation;
    }

    public PlanetMap<Animal, Vector2d> getWorldMap() {
        return worldMap;
    }

    public SimulationStats getStats() {
        return stats;
    }
}
