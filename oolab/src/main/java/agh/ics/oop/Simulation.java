package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;

    public Simulation(List<MoveDirection> moves, List<Vector2d> initialPositions){
        this.animals = new ArrayList<>(); // wybrałem ArrayList, gdyż szybciej jest realizowane odczytywanie elementu
        // na i-tej pozycji(niż w LinkedList), a z animals będziemy często potrzebowali element na i-tym polu

        for (Vector2d pos: initialPositions){
            Animal animal = new Animal(pos);
            this.animals.add(animal);
        }
        this.moves = moves;
    }

    public void run(){
        int numOfAnimals = animals.size();
        int i = 0;
        for (MoveDirection m : moves){
            animals.get(i % numOfAnimals).move(m);
            System.out.println("Zwierzę %d : %s".formatted((i)%numOfAnimals+1,
                    animals.get(i%numOfAnimals).to_String()));
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
