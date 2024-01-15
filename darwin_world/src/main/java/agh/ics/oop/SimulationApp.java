package agh.ics.oop;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.EquatorMap;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.util.MyRange;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);
        SimulationPresenter presenter = loader.getController();
        SimulationSetupData setupData = new SimulationSetupData(15, 15, 2000, 25, 10, 3, 50, 10, 2, 2, new MyRange(1, 3), 5, GenotypeType.BASIC_GENOTYPE, MapType.EQUATOR_MAP);
        initSimulation(setupData, presenter);
        primaryStage.show();

    }

    private void initSimulation(SimulationSetupData setupData, SimulationPresenter presenter) {
        Simulation simulation = new Simulation(setupData);
        simulation.addSimulationChangeListener(presenter);
//        simulation.setMapChangeListener(presenter);
        presenter.assignSimulationAndMap(simulation, simulation.getWorldMap());

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
