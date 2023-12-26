package agh.ics.oop.Simulation;

import agh.ics.oop.model.PositionAlreadyOccupiedException;
import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.setupData.WorldSetupData;
import agh.ics.oop.model.stats.SimulationStats;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.setupData.AnimalSetupData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable {
    private final PlanetMap worldMap; //TODO WorldElement ?
    private final List<Animal> animals;
    private final SimulationStats statsController;
    private final SimulationSetupData simulationSetupData;
    private final List<SimulationChangeListener> listeners = new LinkedList<>();

    //    TODO dużo argumentów więc pewnie jakiś refactor
    public Simulation(SimulationSetupData setupData) {
        this.simulationSetupData = setupData;
        int width = setupData.width();
        int height = setupData.height();
        int initialNumOfAnimals = setupData.initialNumOfAnimals();
        MapType mapType = setupData.mapType();

        this.animals = new ArrayList<>();

        this.worldMap = mapType.createPlanetMap(new WorldSetupData(setupData));

        this.statsController = new SimulationStats(setupData.initialNumOfAnimals(), setupData.startingPlantsCount());

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, initialNumOfAnimals);
        AnimalSetupData animalSetupData = new AnimalSetupData(setupData);
        for (Vector2d animalPosition : randomPositionGenerator) {
            Animal newAnimal = new Animal(animalPosition, animalSetupData);
            try {
                worldMap.place(newAnimal);
                animals.add(newAnimal);
            } catch (
                    PositionAlreadyOccupiedException e) {
                throw new RuntimeException(e);
            }
        }


    }


    @Override
    public void run() {
        while (statsController.getDayOfSimulation() < 10) { // TODO number only for test
            nextDay();
            worldMap.removeDead(animals);
            moveAnimals();
            worldMap.letAnimalsEat();
//            worldMap.letAnimalsReproduce();
            worldMap.growPlants();

            notifySimulationChanged(this);

        }

    }

    private void nextDay() {
        statsController.nextDay();
//            TODO nextDay() z Animal
        //
    }

    private void removeDead() {
        worldMap.removeDead(animals);
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            worldMap.move(animal);
//                System.out.println(worldMap); // TODO prowizoryczne wyświetlanie mapy do poprawy to string z animal i wyświetlanie roślin, zwierzaki się nie teleportują, tylko znikają (mogłem użyć złej funkcji place)
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private void letAnimalEat() {
        worldMap.letAnimalsEat();
    }

    private void letAnimalsReproduce() {
//        worldMap.
    }


    public SimulationStats getStatsController() {
        return statsController;
    }


    public int getDayOfSimulation() {
        return statsController.getDayOfSimulation();
    }

    public PlanetMap getWorldMap() {
        return worldMap;
    }

    public List<Animal> getAnimals() {
        return animals;
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

}
