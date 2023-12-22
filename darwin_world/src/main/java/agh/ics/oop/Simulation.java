package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.stats.SimulationStats;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.genotype.BasicGenotype;
import agh.ics.oop.model.worldElements.WorldElement;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final PlanetMap<WorldElement, Vector2d> worldMap;
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final SimulationStats stats;

    public Simulation(List<MoveDirection> moves, List<Vector2d> initialPositions, PlanetMap<WorldElement, Vector2d> worldMap) {
        this.animals = new ArrayList<>(); //
        this.stats = new SimulationStats();
        this.moves = moves;
        this.worldMap = worldMap;
        for (Vector2d pos : initialPositions) {
            Animal animal = new Animal(pos, new BasicGenotype(5));
            try {
                worldMap.place(animal);
                this.animals.add(animal);
            } catch (
                    PositionAlreadyOccupiedException ignored) {
            }
        }
    }

    @Override
    public void run() {
        int numOfAnimals = animals.size();
        int i = 0;
        for (MoveDirection m : moves) {
            worldMap.move(animals.get(i % numOfAnimals));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }

    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<MoveDirection> getMoves() {
        return moves;
    }

    public SimulationStats getStats() {
        return stats;
    }

    public int getDayOfSimulation() {
        return stats.getDayOfSimulation();
    }

}
