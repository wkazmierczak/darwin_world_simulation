package agh.ics.oop.model.listeners;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.model.stats.SimulationStatsController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationCSVSaver implements SimulationChangeListener {

    private final File destinationFile;
    private final Map<String, Method> methods;

    public SimulationCSVSaver(String destinationFile) {
        String destinationFilePath = new File("").getAbsolutePath().concat("\\src\\main\\java\\agh\\ics\\oop\\model\\logs\\") + destinationFile;
        this.destinationFile = new File(destinationFilePath);
        this.methods = SimulationStatsController.getMethods();
        printHeader();
    }

    private void printHeader() {
        try (PrintWriter fw = new PrintWriter(destinationFile)) {
            fw.println(String.join(",", methods.keySet()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void simulationChanged(Simulation
                                          simulation) {
        SimulationStatsController stats = simulation.getStatsController();
        try (FileWriter fw = new FileWriter(destinationFile, true)) {
            List<String> values = methods.values().stream()
                    .map(method -> {
                        try {
                            return method.invoke(stats).toString();
                        } catch (
                                IllegalAccessException |
                                InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            String line = String.join(",", values) + "\n";
            fw.write(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
