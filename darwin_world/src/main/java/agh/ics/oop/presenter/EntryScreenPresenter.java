package agh.ics.oop.presenter;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.util.MyRange;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EntryScreenPresenter {
    public TextField widthTextField;
    public TextField reproduceEnergyMinTextField;
    public TextField heightTextField;
    public TextField maxDaysTextField;
    public TextField startingPlantsCountTextField;
    public TextField energyAfterConsumingPlantTextField;
    public TextField plantsPerDayTextField;
    public TextField initialNumOfAnimalsTextField;
    public TextField initialAnimalEnergyTextField;
    public TextField energySpendToReproduceTextField;
    public TextField lowTextField;
    public TextField highTextField;
    public TextField genotypeLengthTextField;
    private SimulationSetupData setupData;
    @FXML
    private ComboBox<GenotypeType> genotypeTypeComboBox;

    @FXML
    private ComboBox<MapType> mapTypeComboBox;

    @FXML
    private void initialize() {
        genotypeTypeComboBox.setItems(FXCollections.observableArrayList(GenotypeType.values()));
        mapTypeComboBox.setItems(FXCollections.observableArrayList(MapType.values()));
    }

    public Button submitButton;


    public void getInput() {
        try {
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            int maxDays = Integer.parseInt(maxDaysTextField.getText());
            int startingPlantsCount = Integer.parseInt(startingPlantsCountTextField.getText());
            int energyAfterConsumingPlant = Integer.parseInt(energyAfterConsumingPlantTextField.getText());
            int plantsPerDay = Integer.parseInt(plantsPerDayTextField.getText());
            int initialNumOfAnimals = Integer.parseInt(initialNumOfAnimalsTextField.getText());
            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyTextField.getText());
            int reproduceEnergyMin = Integer.parseInt(reproduceEnergyMinTextField.getText());
            int energySpendToReproduce = Integer.parseInt(energySpendToReproduceTextField.getText());
            int genotypeLength = Integer.parseInt(genotypeLengthTextField.getText());

            MyRange mutationCountRange = new MyRange(Integer.parseInt(lowTextField.getText()), Integer.parseInt(highTextField.getText()));
            GenotypeType genotypeType = genotypeTypeComboBox.getValue();
            MapType mapType = mapTypeComboBox.getValue();

            setupData = new SimulationSetupData(
                    width,
                    height,
                    maxDays,
                    startingPlantsCount,
                    energyAfterConsumingPlant,
                    plantsPerDay,
                    initialNumOfAnimals,
                    initialAnimalEnergy,
                    reproduceEnergyMin,
                    energySpendToReproduce,
                    mutationCountRange,
                    genotypeLength,
                    genotypeType,
                    mapType
            );

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        }

//        Stage primaryStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//        primaryStage.close();

//        openSecondWindow();

    }


    private void openSecondWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400, 300);
//            SimulationPresenter presenter = loader.getController();

            stage.setTitle("Second Window");
            stage.setScene(scene);
            stage.minWidthProperty().bind(root.minWidthProperty());
            stage.minHeightProperty().bind(root.minHeightProperty());

//            Simulation simulation2 = new Simulation(setupData);
//            simulation2.addSimulationChangeListener(presenter);
//            List<Simulation> sims = List.of(simulation2);
//            SimulationEngine engine = new SimulationEngine(sims);
//            engine.runAsync();

            stage.show();

        }
        catch(Exception e){
            System.out.println("Can't load new window: " + e.getMessage());
        }
    }

    public SimulationSetupData getSetupData() {
        return setupData;
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}
