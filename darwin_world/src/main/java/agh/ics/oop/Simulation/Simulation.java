package agh.ics.oop.Simulation;

import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.*;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.setupData.WorldSetupData;
import agh.ics.oop.model.stats.SimulationStatsController;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.setupData.AnimalSetupData;
import agh.ics.oop.presenter.SimulationPresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable {
    private final PlanetMap worldMap;
    private final List<Animal> animals;
    private final SimulationStatsController statsController;
    private final SimulationSetupData simulationSetupData;
    private final List<SimulationChangeListener> listeners = new LinkedList<>();
    private final int id;
    static private int idCounter = 0;

    public Simulation(SimulationSetupData setupData) {
        this.simulationSetupData = setupData;
        this.id = idCounter++;
        int width = setupData.width();
        int height = setupData.height();
        int initialNumOfAnimals = setupData.initialNumOfAnimals();
        MapType mapType = setupData.mapType();
        this.animals = new ArrayList<>();
        this.worldMap = mapType.createPlanetMap(new WorldSetupData(setupData));

        this.statsController = new SimulationStatsController(this);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, initialNumOfAnimals);
        AnimalSetupData animalSetupData = new AnimalSetupData(setupData);
        for (Vector2d animalPosition : randomPositionGenerator) {
            Animal newAnimal = new Animal(animalPosition, animalSetupData);
            worldMap.place(newAnimal);
            animals.add(newAnimal);
        }
    }

    @Override
    public void run() {
        int flag = 1;
        while (statsController.getDayOfSimulation() < simulationSetupData.maxDays()) { // TODO number only for test
            nextDay();
            removeDead();
            moveAnimals();
            worldMap.letAnimalsEat();
            List<Animal> newborns = worldMap.letAnimalsReproduce();
            animals.addAll(newborns);
            worldMap.growPlants();

            notifySimulationChanged(this);
            System.out.println("Day of simulation: " + statsController.getDayOfSimulation() + " %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            try { //tymczasowe
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }

    }

    private void nextDay() {
        statsController.nextDay();
        worldMap.nextDay(animals);
    }

    private void removeDead() {
        List<Animal> removedDead = worldMap.removeDead(animals, getDayOfSimulation());
        removedDead.forEach(animal -> {
            animal.notifyAnimalTracker();
            getStatsController().newDeath(animal);
            animals.remove(animal);
        });
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            worldMap.move(animal);
            animal.notifyAnimalTracker();
        }
    }

    private void letAnimalEat() {
        worldMap.letAnimalsEat();
    }

    private void letAnimalsReproduce() {
//        worldMap.
    }


    public SimulationStatsController getStatsController() {
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


    public int getId() {
        return id;
    }
}
