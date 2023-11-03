package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap worldMap;
    private final List<Animal> animals;
    private final List<MoveDirection> moves;

    public Simulation(List<MoveDirection> moves, List<Vector2d> initialPositions, WorldMap worldMap){
        this.animals = new ArrayList<>(); // wybrałem ArrayList, gdyż szybciej jest realizowane odczytywanie elementu
        // na i-tej pozycji(niż w LinkedList), a z animals będziemy często potrzebowali element na i-tym polu

        this.moves = moves;
        this.worldMap = worldMap;
        for (Vector2d pos: initialPositions){
            Animal animal = new Animal(pos);
            if (worldMap.place(animal)) {
                this.animals.add(animal);
            }
        }
    }

    public void run(){
        System.out.println(worldMap);
        int numOfAnimals = animals.size();
        int i = 0;
        for (MoveDirection m : moves){
            worldMap.move(animals.get(i % numOfAnimals), m);
            System.out.println(worldMap);
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
