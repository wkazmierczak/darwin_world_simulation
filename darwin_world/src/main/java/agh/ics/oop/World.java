package agh.ics.oop;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.listeners.SimulationCSVSaver;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;

import agh.ics.oop.model.maps.PoisonousMap;

import agh.ics.oop.model.setupData.AnimalSetupData;
import agh.ics.oop.model.stats.SimulationStats;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.util.MyRange;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.AnimalComparator;

import java.lang.reflect.Method;
import java.util.*;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        PlanetMap planetMap = new PoisonousMap(10, 10);
//        System.out.println(planetMap);
        Simulation sim = new Simulation(new SimulationSetupData(10, 10, 3, 1, 3, 2, 5, 1, 1, new MyRange(1, 3), 5, GenotypeType.BASIC_GENOTYPE, MapType.EQUATOR_MAP));
        Map<String, Method> methods = new HashMap<>();
        try {
            methods.put("dayOfSimulation", SimulationStats.class.getDeclaredMethod("getDayOfSimulation"));
            methods.put("plants", SimulationStats.class.getDeclaredMethod("getNumOfPlants"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        SimulationChangeListener listener = new SimulationCSVSaver("simulation3.csv", methods);
        sim.addSimulationChangeListener(listener);
        sim.run();
//        TODO symulacja jeszcze nie współgra z obserwatorami


//        Application.launch(SimulationApp.class, args);


//        ConsoleMapDisplay mapDisplay = new ConsoleMapDisplay();
//        List<Simulation> sims = new ArrayList<>();
//        String [] possibleMvs = {"f", "b", "r", "l"};
//
//        for (int i = 0; i< 1000; i++){
//            Simulation sim;
//            List<String> mvs = new ArrayList<>();
//            for (int j = 0; j < 20; j++){
//                Random rand= new Random();
//                int idx = rand.nextInt(4);
//                mvs.add(possibleMvs[idx]);
//            }
//            List<MoveDirection> directions = OptionsParser.parse(mvs.toArray(mvs.toArray(new String[0])));
//            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(-1, 2));
//
//            if (i%3==0 || i%4==0){
//                GrassField gMap = new GrassField(i%15);
//                gMap.addListener(mapDisplay);
//                sim = new Simulation(directions, positions, gMap);
//
//            }
//            else{
//                RectangularMap rMap = new RectangularMap((i+4)%13, (i+1)%10);
//                rMap.addListener(mapDisplay);
//                sim = new Simulation(directions, positions, rMap);
//            }
//            sims.add(sim);
//
//        }
//
//        SimulationEngine engine = new SimulationEngine(sims);
////        engine.runSync();
////        engine.runAsync();
////        engine.awaitSimulationEnd();
//        engine.runAsyncInThreadPool();
//        engine.awaitSimulationEnd();


//        GrassField map1 = new GrassField(5);
//        ConsoleMapDisplay mapDisplay1 = new ConsoleMapDisplay();
//
//        RectangularMap map2 = new RectangularMap(4, 4);
//        ConsoleMapDisplay mapDisplay2 = new ConsoleMapDisplay();
//
//        map2.addListener(mapDisplay2);
////
////        List<MoveDirection> directions1 = OptionsParser.parse(args);
////        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(-1, 2));
////        Simulation simulation1 = new Simulation(directions1, positions1, map1);
//        String[] input1 = {"f", "f"};
//        List<MoveDirection> directions2 = OptionsParser.parse(input1);
//        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(3,2));
//        Simulation simulation2 = new Simulation(directions2, positions2, map2);
//        simulation2.run();
//        List<Simulation> sims = List.of(simulation1, simulation2);
//        SimulationEngine engine = new SimulationEngine(sims);
////        engine.runSync();
//        engine.runAsync();
//        engine.awaitSimulationEnd();
//
//        System.out.println("system zakończył działanie");

    }

    public static void run(MoveDirection[] directions) {

        System.out.println("Start");

        for (MoveDirection argument : directions) {


            switch (argument) {
                case FORWARD ->
                        System.out.println("Zwierzak idzie do przodu");
                case BACKWARD ->
                        System.out.println("Zwierzak idzie do tyłu");
                case RIGHT ->
                        System.out.println("Zwierzak skręca w prawo");
                case LEFT ->
                        System.out.println("Zwierzak skręca w lewo");
            }
            ;

        }

        System.out.println("Stop");

    }
}
