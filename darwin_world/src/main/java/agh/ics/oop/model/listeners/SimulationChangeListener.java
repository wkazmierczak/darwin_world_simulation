package agh.ics.oop.model.listeners;

import agh.ics.oop.Simulation;

public interface SimulationChangeListener {
    void simulationChanged(Simulation simulation, String message);

}
