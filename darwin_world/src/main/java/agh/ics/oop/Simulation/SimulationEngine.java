package agh.ics.oop.Simulation;

import agh.ics.oop.Simulation.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public SimulationEngine(Simulation simulation) {
        this.simulations = List.of(simulation);
    }

    public void runSync() {
        for (Simulation sim : simulations) {
            sim.run();
        }
    }

    public void runAsync() {
        for (Simulation sim : simulations) {
            threads.add(new Thread(sim));
        }
        for (Thread th : threads) {
            th.start();
        }
    }


    public void runAsyncInThreadPool() {
        for (Simulation sim : simulations) {
            executorService.submit(sim);
        }
        executorService.shutdown();
    }

}
