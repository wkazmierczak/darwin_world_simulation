package agh.ics.oop.model.listeners;

import agh.ics.oop.Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationCSVSaver implements SimulationChangeListener {

    private final File destinationFile;
    private final Map<String, Method> methods;

    SimulationCSVSaver(String destinationFile, Map<String, Method> methods) {
        this.destinationFile = new File(destinationFile);
        this.methods = methods;
    }

    private void printHeader() {
        try (PrintWriter pw = new PrintWriter(destinationFile)) {
            String header = String.join(",", methods.keySet());
            pw.println(header);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void simulationChanged(Simulation simulation, String message) {
        try (PrintWriter pw = new PrintWriter(destinationFile)) {
            List<String> values = methods.values().stream()
                    .map(method -> {
                        try {
                            return method.invoke(simulation.getStats()).toString();
                        } catch (
                                IllegalAccessException |
                                InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            String line = String.join(",", values);
            pw.println(line);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
