package agh.ics.oop;

public class World {
    public static void main(String[] args){
        System.out.println("system wystartował");
        run(args);
        System.out.println("system zakończył działanie");

    }

    public static void run(String[] arguments){
//        System.out.println("Zwierzak idzie do przodu");
//        int i = 0;
//        int l = arguments.length;
        System.out.println("Start");
        for (String argument: arguments){
//            System.out.print(argument);
//            i++;
//            if(i < l){
//                System.out.print(", ");
//            }
//            else{
//                System.out.print("\n");
//            }
            switch (argument) {
                case "f" -> System.out.println("Zwierzak idzie do przodu");
                case "b" -> System.out.println("Zwierzak idzie do tyłu");
                case "r" -> System.out.println("Zwierzak skręca w prawo");
                case "l" -> System.out.println("Zwierzak skręca w lewo");
            };

        }
        System.out.println("Stop");

    }
}
