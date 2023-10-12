package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args){
        System.out.println("system wystartował");

        MoveDirection[] directions = OptionsParser.parse(args);
        run(directions);

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
