package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.presenter.EntryScreenPresenter;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("entryScreen.fxml"));
        GridPane viewRoot = loader.load();
        configureStageGridPane(primaryStage, viewRoot);
        EntryScreenPresenter presenter = loader.getController();
        primaryStage.show();

        Button submitButton = presenter.getSubmitButton();
        submitButton.setOnAction(e -> {
            presenter.getInput();
            SimulationSetupData setupData = presenter.getSetupData();
            openSimWindow(setupData, presenter.saveToCSV());
        });

    }


    private void openSimWindow(SimulationSetupData setupData,boolean saveToCSV) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            Stage scndStage = new Stage();
            BorderPane viewRoot = loader.load();
            configureStage(scndStage, viewRoot);
            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.setSetupData(setupData,saveToCSV);
            scndStage.show();

        } catch (Exception e) {
            System.out.println("Can't load new window: " + e.getMessage());
        }
    }


    private void configureStageGridPane(Stage primaryStage, GridPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        viewRoot.setHgap(10);
        viewRoot.setVgap(10);
        viewRoot.setPadding(new Insets(10, 10, 10, 10));
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
