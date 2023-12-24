package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.EquatorMap;
import agh.ics.oop.model.maps.MapType;
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

//    TODO dużo argumentów więc pewnie jakiś refactor
    public Simulation(int width, int height, int initialNumOfAnimals, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant, int genotypeLength, GenotypeType genotypeType, MapType mapType) {
        this.animals = new ArrayList<>();

        this.worldMap = mapType.createPlanetMap(width, height, startingPlantsCount, everyDayPlantsCount,energyAfterConsumingPlant);

        this.stats = new SimulationStats(initialNumOfAnimals, startingPlantsCount);

//        List<Vector2d> tab1 = new ArrayList<>(List.of(new Vector2d(1, 2), new Vector2d(3, 3)));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, initialNumOfAnimals);
        for(Vector2d animalPosition : randomPositionGenerator) {
            Animal newAnimal = new Animal(animalPosition, genotypeType.createGenotype(genotypeLength));
            animals.add(newAnimal);
            try {
                worldMap.place(newAnimal);
            } catch (PositionAlreadyOccupiedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(worldMap);
        }


    }


    @Override
    public void run() {
        int day = 0;
        while (day<10){ // TODO number only for test
            for (Animal animal: animals) {
                worldMap.move(animal);
                System.out.println(worldMap); // TODO prowizoryczne wyświetlanie mapy do poprawy to string z animal i wyświetlanie roślin, zwierzaki się nie teleportują, tylko znikają (mogłem użyć złej funkcji place)
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            }
            day++;

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
