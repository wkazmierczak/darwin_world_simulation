package agh.ics.oop.Simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final Map<Simulation, Thread> threads = new HashMap<>();
    private final Map<Simulation, Boolean> waiting = new HashMap<>();
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
            threads.put(sim, new Thread(sim));
        }
        for (Thread th : threads.values()) {
            th.start();
        }
    }

    public void pauseSimulation(Simulation simulation) {
        waiting.put(simulation, true);
        synchronized (threads.get(simulation)) {
            while (waiting.get(simulation)) {
                try {
                    threads.get(simulation).wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void resumeSimulation(Simulation simulation) {
        waiting.remove(simulation);
        threads.get(simulation).notify();
    }


    public void runAsyncInThreadPool() {
        for (Simulation sim : simulations) {
            executorService.submit(sim);
        }
        executorService.shutdown();
    }

}
