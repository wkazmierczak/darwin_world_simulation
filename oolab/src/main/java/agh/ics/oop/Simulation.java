package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable{
    private final WorldMap<WorldElement, Vector2d> worldMap;
    private final List<Animal> animals;
    private final List<MoveDirection> moves;

    public Simulation(List<MoveDirection> moves, List<Vector2d> initialPositions, WorldMap<WorldElement, Vector2d> worldMap){
        this.animals = new ArrayList<>(); // wybrałem ArrayList, gdyż szybciej jest realizowane odczytywanie elementu
        // na i-tej pozycji(niż w LinkedList), a z animals będziemy często potrzebowali element na i-tym polu

        this.moves = moves;
        this.worldMap = worldMap;
        for (Vector2d pos: initialPositions){
            Animal animal = new Animal(pos);
            try {
                worldMap.place(animal);
                this.animals.add(animal);
            } catch (PositionAlreadyOccupiedException ignored){}
        }
    }

    @Override
    public void run(){
        int numOfAnimals = animals.size();
        int i = 0;
        for (MoveDirection m : moves){
            worldMap.move(animals.get(i % numOfAnimals), m);
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
}
