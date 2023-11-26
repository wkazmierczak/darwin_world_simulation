package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine{
    private final List<Simulation> simulations;
    private final List<Thread> threads =  new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;

    }
    public void runSync(){
        for(Simulation sim : simulations){
            sim.run();
        }
    }
    public void runAsync(){
        for(Simulation sim : simulations){
            threads.add(new Thread(sim));
        }

        for (Thread th : threads){
            th.start();
        }
    }

    public void awaitSimulationEnd(){
//        for (Thread th : threads){
//            try {
//                th.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                System.out.println("Force shutdown after 10 secs");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void runAsyncInThreadPool(){
        for(Simulation sim : simulations){
            executorService.submit(sim);
        }
    }

}
