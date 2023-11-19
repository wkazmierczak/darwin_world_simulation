package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args){
        System.out.println("system wystartował");


        GrassField map1 = new GrassField(5);
        ConsoleMapDisplay mapDisplay = new ConsoleMapDisplay();

        map1.addListener(mapDisplay);

        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(-1, 2));
        Simulation simulation = new Simulation(directions, positions, map1);
        simulation.run();

        System.out.println("system zakończył działanie");

    }

    public static void run(MoveDirection[] directions){

        System.out.println("Start");

        for (MoveDirection argument: directions){


            switch (argument) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            };

        }

        System.out.println("Stop");

    }
}
