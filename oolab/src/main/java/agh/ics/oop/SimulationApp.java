package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);
        SimulationPresenter presenter = loader.getController();

//        String[] input1 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "l", "f", "r", "f", "f", "b", "b"};
        GrassField map2 = new GrassField(10);
//        List<MoveDirection> directions2 = OptionsParser.parse(input1);
//        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(3,2), new Vector2d(-10, 2));


        presenter.setWorldMap(map2);
        map2.addListener(presenter);



//        Simulation simulation2 = new Simulation(directions2, positions2, map2);


//        simulation2.run();
        primaryStage.show();

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
