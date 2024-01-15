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
    private boolean frozen;

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
        while (statsController.getDayOfSimulation() < simulationSetupData.maxDays()) {
            nextDay();
            removeDead();
            moveAnimals();
            worldMap.letAnimalsEat();
            letReproduce();
            worldMap.growPlants();

            notifySimulationChanged(this);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                while (frozen) {
                    try {
                        this.wait();
                    } catch (
                            InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
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

    private void letReproduce() {
        List<Animal> newborns = worldMap.letAnimalsReproduce();
        animals.addAll(newborns);
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            worldMap.move(animal);
            animal.notifyAnimalTracker();
        }
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

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
        synchronized (this) {
            this.notify();
        }
    }

    public int getId() {
        return id;
    }
}
