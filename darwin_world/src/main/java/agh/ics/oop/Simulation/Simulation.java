package agh.ics.oop.Simulation;

import agh.ics.oop.model.PositionAlreadyOccupiedException;
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
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Simulation implements Runnable {
    private final PlanetMap worldMap; //TODO WorldElement ?
    private final List<Animal> animals;
    private final SimulationStatsController statsController;
    private final SimulationSetupData simulationSetupData;
    private final List<SimulationChangeListener> listeners = new LinkedList<>();
    private Animal animalToTrack = null;

    public Simulation(SimulationSetupData setupData) {
        this.simulationSetupData = setupData;
        int width = setupData.width();
        int height = setupData.height();
        int initialNumOfAnimals = setupData.initialNumOfAnimals();
        MapType mapType = setupData.mapType();

        this.animals = new ArrayList<>();

        this.worldMap = mapType.createPlanetMap(new WorldSetupData(setupData));
        MapChangeListener mapChangeListener = new ConsoleMapDisplay();
        worldMap.addListener(mapChangeListener);


//        this.statsController = new SimulationStatsController(setupData.initialNumOfAnimals(), setupData.startingPlantsCount());
        this.statsController = new SimulationStatsController(this);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, initialNumOfAnimals);
        AnimalSetupData animalSetupData = new AnimalSetupData(setupData);
        for (Vector2d animalPosition : randomPositionGenerator) {
            Animal newAnimal = new Animal(animalPosition, animalSetupData);
//            try {
            worldMap.place(newAnimal);
            animals.add(newAnimal);
//            } catch (
//                    PositionAlreadyOccupiedException e) {
//                throw new RuntimeException(e);
//            }
        }
//        setAnimalToTrack(animals.get(0));
    }

    @Override
    public void run() {
        int flag = 1;
        while (statsController.getDayOfSimulation() < 10) { // TODO number only for test
            nextDay();
            removeDead();
            moveAnimals();
            worldMap.letAnimalsEat();
//            System.out.println(animals);
            List<Animal> newborns = worldMap.letAnimalsReproduce();
            if (flag == 1 && !newborns.isEmpty()) { // TODO to remove only for tests, listener ustawiony na pierwsze narodzone dziecko
                flag = 0;
                setAnimalToTrack(newborns.get(0));
            }
            animals.addAll(newborns);
//            System.out.println(animals);
            worldMap.growPlants();

            notifySimulationChanged(this);
            System.out.println(statsController.getDayOfSimulation() + " %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        }

    }

    private void nextDay() {
        statsController.nextDay();
        worldMap.nextDay(animals);
    }

    private void removeDead() {
        List<Animal> removedDead = worldMap.removeDead(animals, getDayOfSimulation());
        if (removedDead.contains(animalToTrack)) {
            animalToTrack.notifyAnimalTracker();
        }
        removedDead.forEach(getStatsController()::newDeath);
        animals.removeAll(removedDead);
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            worldMap.move(animal);
            if (animal == animalToTrack) {
                animalToTrack.notifyAnimalTracker();
            }
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

    public void setAnimalToTrack(Animal animal) {
        animalToTrack = animal;
        AnimalChangeListener animalChangeListener = new AnimalTracker();
        animalToTrack.addAnimalTracker(animalChangeListener);
    }

}
