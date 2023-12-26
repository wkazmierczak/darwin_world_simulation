package agh.ics.oop;

import agh.ics.oop.model.PositionAlreadyOccupiedException;
import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.stats.SimulationStats;
import agh.ics.oop.model.worldElements.Animal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable {
    private final PlanetMap<Animal, Vector2d> worldMap; //TODO WorldElement ?
    private final List<Animal> animals;
    private SimulationStats stats;

    private final List<SimulationChangeListener> listeners = new LinkedList<>();

    //    TODO dużo argumentów więc pewnie jakiś refactor
    public Simulation(int width, int height, int initialNumOfAnimals, int startingPlantsCount, int everyDayPlantsCount, int energyAfterConsumingPlant, int genotypeLength, GenotypeType genotypeType, MapType mapType) {
        this.animals = new ArrayList<>();

        this.worldMap = mapType.createPlanetMap(width, height, startingPlantsCount, everyDayPlantsCount, energyAfterConsumingPlant);

        this.stats = new SimulationStats(initialNumOfAnimals, startingPlantsCount);

//        List<Vector2d> tab1 = new ArrayList<>(List.of(new Vector2d(1, 2), new Vector2d(3, 3)));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, initialNumOfAnimals);
        for (Vector2d animalPosition : randomPositionGenerator) {
            Animal newAnimal = new Animal(animalPosition, genotypeType.createGenotype(genotypeLength));
            animals.add(newAnimal);
            try {
                worldMap.place(newAnimal);
            } catch (
                    PositionAlreadyOccupiedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(worldMap);
        }


    }

    public void addSimulationChangeListener(SimulationChangeListener listener) {
        listeners.add(listener);
    }

    public void removeSimulationChangeListener(SimulationChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifySimulationChanged(Simulation simulation) {
        listeners.forEach(listener -> listener.simulationChanged(simulation));
    }

    @Override
    public void run() {
        int day = 0;
        while (day < 10) { // TODO number only for test
            for (Animal animal : animals) {
                worldMap.move(animal);
                System.out.println(worldMap); // TODO prowizoryczne wyświetlanie mapy do poprawy to string z animal i wyświetlanie roślin, zwierzaki się nie teleportują, tylko znikają (mogłem użyć złej funkcji place)
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            }
            notifySimulationChanged(this);
            day++;

//            TODO usuwanie ciał
//            TODO nextDay() z Animal
//            TODO wzrost nowych roślin
        }

    }

    public List<Animal> getAnimals() {
        return animals;
    }


    public SimulationStats getStats() {
        return stats;
    }


    public int getDayOfSimulation() {
        return stats.getDayOfSimulation();
    }

    public PlanetMap<Animal, Vector2d> getWorldMap() {
        return worldMap;
    }

}
