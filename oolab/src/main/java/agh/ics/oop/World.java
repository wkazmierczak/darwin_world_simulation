package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

public class World {
    public static void main(String[] args){
        System.out.println("system wystartował");

        MoveDirection[] directions = OptionsParser.parse(args);
        run(directions);

        System.out.println("=======================");

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        System.out.println("=======================");

        MapDirection direction = MapDirection.NORTH;
        System.out.println(direction);
        System.out.println(direction.toUnitVector());
        for (int i =0; i<4; i++){
            direction = direction.previous();
            System.out.println(direction);
            System.out.println(direction.toUnitVector());
        }

        for (int i =0; i<4; i++){
            direction = direction.next();
            System.out.println(direction);
            System.out.println(direction.toUnitVector());
        }

        System.out.println("system zakończył działanie");

    }

    public static void run(MoveDirection[] directions){
//        System.out.println("Zwierzak idzie do przodu");
//        int i = 0;
//        int l = arguments.length;
        System.out.println("Start");

        for (MoveDirection argument: directions){

//            System.out.print(argument);
//            i++;
//            if(i < l){
//                System.out.print(", ");
//            }
//            else{
//                System.out.print("\n");
//            }

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
