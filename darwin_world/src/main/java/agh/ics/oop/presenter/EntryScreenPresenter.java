package agh.ics.oop.presenter;

import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.util.MyRange;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Button readCSVButton;
    public ComboBox<String> filesComboBox;
    public CheckBox saveToCSVCheckBox;
    public TextField delayTextField;
    private SimulationSetupData setupData;
    @FXML
    private ComboBox<GenotypeType> genotypeTypeComboBox;

    @FXML
    private ComboBox<MapType> mapTypeComboBox;

    @FXML
    private void initialize() {
        genotypeTypeComboBox.setItems(FXCollections.observableArrayList(GenotypeType.values()));
        mapTypeComboBox.setItems(FXCollections.observableArrayList(MapType.values()));

        List<String> results = Arrays.stream(Objects.requireNonNull(new File("src/main/java/agh/ics/oop/model/setupFiles").listFiles(File::isFile)))
                .map(File::getName)
                .collect(Collectors.toList());

        filesComboBox.setItems(FXCollections.observableArrayList(results));
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
            int delay = Integer.parseInt(delayTextField.getText());

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
                    mapType,
                    delay
            );

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        }
    }

    @FXML
    private void setDefaultValuesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/agh/ics/oop/model/setupFiles/" + filesComboBox.getValue()))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 16) {
                    widthTextField.setText(values[0].trim());
                    heightTextField.setText(values[1].trim());
                    maxDaysTextField.setText(values[2].trim());
                    startingPlantsCountTextField.setText(values[3].trim());
                    energyAfterConsumingPlantTextField.setText(values[4].trim());
                    plantsPerDayTextField.setText(values[5].trim());
                    initialNumOfAnimalsTextField.setText(values[6].trim());
                    initialAnimalEnergyTextField.setText(values[7].trim());
                    reproduceEnergyMinTextField.setText(values[8].trim());
                    energySpendToReproduceTextField.setText(values[9].trim());
                    genotypeLengthTextField.setText(values[10].trim());

                    if (values[11].trim().equals("BASIC_GENOTYPE")) {
                        genotypeTypeComboBox.getSelectionModel().select(0);
                    } else if (values[11].trim().equals("PING_PONG_GENOTYPE")) {
                        genotypeTypeComboBox.getSelectionModel().select(1);
                    }
                    if (values[12].trim().equals("EQUATOR_MAP")) {
                        mapTypeComboBox.getSelectionModel().select(0);
                    } else if (values[12].trim().equals("POISONOUS_MAP")) {
                        mapTypeComboBox.getSelectionModel().select(1);
                    }
                    lowTextField.setText(values[13].trim());
                    highTextField.setText(values[14].trim());
                    delayTextField.setText(values[15].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SimulationSetupData getSetupData() {
        return setupData;
    }

    public boolean saveToCSV() {
        return saveToCSVCheckBox.isSelected();
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}
