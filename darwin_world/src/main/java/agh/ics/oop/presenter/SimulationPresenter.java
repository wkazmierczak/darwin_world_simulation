package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.*;

import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.listeners.AnimalChangeListener;
import agh.ics.oop.model.listeners.MapChangeListener;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.PlanetMap;

import agh.ics.oop.model.setupData.SimulationSetupData;
import agh.ics.oop.model.util.MyRange;
import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.worldElements.WorldElement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class SimulationPresenter implements MapChangeListener, AnimalChangeListener, SimulationChangeListener {
    private static final double CELL_WIDTH = 30 ;
    private static final double CELL_HEIGHT = 30;
    private static PlanetMap map;
    public Label genotype;
    public Label dayOfSim;
    public Label numOfAnimals;
    public Label numOfPlants;
    public Label numOFreeSpots;
    public Label mostCommonGenotype;
    public Label avgEnergyLevel;
    public Label avgLifespan;
    public Label avgChildrenNum;
    public Label activeGenotype;
    public Label numOfEatenPlants;
    public Label numOfChildren;
    public Label numOfDescendants;
    public Label energyLevel;
    public Label age;
    public Label dayOfDeath;
    private SimulationSetupData setupData;

    @FXML
    public Button startButton;

    @FXML
    public GridPane mapGrid;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField textField;

    public void setWorldMap(PlanetMap map) {
        this.map = map;
    }

    public void simulationSettings(){
        Vector2d lowerLeft = map.getCurrentBounds().bottomLeft();
        Vector2d upperRight = map.getCurrentBounds().upperRight();

        int rows = upperRight.getY() - lowerLeft.getY() + 1;
        int columns = upperRight.getX() - lowerLeft.getX() + 1;

        for (int i = 0; i < columns+1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = 0; i < rows+1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        Label cellLabel1 = new Label();
        cellLabel1.setMinSize(10, 10);
        cellLabel1.setText("y\\x");
        mapGrid.add(cellLabel1, 0, 0);
        GridPane.setHalignment(cellLabel1, HPos.CENTER);

        for (int i = 1; i < columns+1; i++) {
            Label cellLabel = new Label();
            cellLabel.setMinSize(10, 10);
            cellLabel.setText("" + (lowerLeft.getX()+i-1));
            mapGrid.add(cellLabel, i, 0);
            GridPane.setHalignment(cellLabel, HPos.CENTER);

        }

        for (int i = 1; i < rows+1; i++) {
            Label cellLabel = new Label();
            cellLabel.setMinSize(10, 10);
            cellLabel.setText("" + (upperRight.getY()-i+1));
            mapGrid.add(cellLabel, 0, i);
            GridPane.setHalignment(cellLabel, HPos.CENTER);
        }

        int col = 1;
        int row;

        for (int x = lowerLeft.getX();x<=upperRight.getX();x++) {
            row = 1;
            for (int y = upperRight.getY();y>=lowerLeft.getY();y--) {

                Vector2d currentPosition = new Vector2d(x, y);
                WorldElement elem = (WorldElement) map.plantAt(currentPosition);
                if (elem != null){
                    Label cellLabel = new Label();
                    cellLabel.setMinSize(10, 10);
                    cellLabel.setText(map.plantAt(currentPosition).toString());
                    mapGrid.add(cellLabel, col,row);
                    GridPane.setHalignment(cellLabel, HPos.CENTER);


                }
                row++;
            }
            col++;
        }
    }

    public void drawMap(){

        clearGrid();

        Vector2d lowerLeft = map.getCurrentBounds().bottomLeft();
        Vector2d upperRight = map.getCurrentBounds().upperRight();

        int rows = upperRight.getY() - lowerLeft.getY() + 1;
        int columns = upperRight.getX() - lowerLeft.getX() + 1;

        for (int i = 0; i < columns+1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = 0; i < rows+1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        Label cellLabel1 = new Label();
        cellLabel1.setMinSize(10, 10);
        cellLabel1.setText("y\\x");
        mapGrid.add(cellLabel1, 0, 0);
        GridPane.setHalignment(cellLabel1, HPos.CENTER);

        for (int i = 1; i < columns+1; i++) {
            Label cellLabel = new Label();
            cellLabel.setMinSize(10, 10);
            cellLabel.setText("" + (lowerLeft.getX()+i-1));
            mapGrid.add(cellLabel, i, 0);
            GridPane.setHalignment(cellLabel, HPos.CENTER);

        }

        for (int i = 1; i < rows+1; i++) {
            Label cellLabel = new Label();
            cellLabel.setMinSize(10, 10);
            cellLabel.setText("" + (upperRight.getY()-i+1));
            mapGrid.add(cellLabel, 0, i);
            GridPane.setHalignment(cellLabel, HPos.CENTER);
        }

        int col = 1;
        int row;

        for (int x = lowerLeft.getX();x<=upperRight.getX();x++) {
            row = 1;
            for (int y = upperRight.getY();y>=lowerLeft.getY();y--) {

                Vector2d currentPosition = new Vector2d(x, y);
                WorldElement elem = (WorldElement) map.plantAt(currentPosition);
                if (elem != null){
                    Label cellLabel = new Label();
                    cellLabel.setMinSize(10, 10);
                    cellLabel.setText(map.plantAt(currentPosition).toString());
                    mapGrid.add(cellLabel, col,row);
                    GridPane.setHalignment(cellLabel, HPos.CENTER);


                }
                row++;
            }
            col++;
        }



    }


    @Override
    public void mapChanged(PlanetMap worldMap, String message) {// zmiana na animal

        Platform.runLater(() -> {
            infoLabel.setText(message);
            drawMap();
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {


        Simulation simulation2 = new Simulation(setupData);
        simulation2.addSimulationChangeListener(this);
        List<Simulation> sims = List.of(simulation2);
        SimulationEngine engine = new SimulationEngine(sims);
//        engine.runSync();
        engine.runAsyncInThreadPool();
//        engine.awaitSimulationEnd();


//        simulation2.run();
    }



    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public SimulationSetupData getSetupData() {
        return setupData;
    }

    public void setSetupData(SimulationSetupData setupData) {
        this.setupData = setupData;
    }

    //TODO dont know how to connect animal with animal listener
    @Override
    public synchronized void animalInfoChanged(Animal animal) {
        Platform.runLater(()->{
            genotype.setText(String.valueOf(animal.getGenotype().getGenotypeList()));
            activeGenotype.setText(String.valueOf(animal.getGenotype().next()));
            energyLevel.setText(String.valueOf(animal.getEnergyLevel()));
            numOfEatenPlants.setText(String.valueOf(animal.getStats().getPlantsEaten()));
            numOfChildren.setText(String.valueOf(animal.getStats().getChildrenCount()));
            numOfDescendants.setText(String.valueOf(animal.getStats().getDescendantsCount()));
            age.setText(String.valueOf(animal.getStats().getAge()));
            dayOfDeath.setText(String.valueOf(animal.getStats().getDayOfDeath()));
        });
    }

    @Override
    public synchronized void simulationChanged(Simulation simulation) {
        Platform.runLater(()-> {
            dayOfSim.setText(String.valueOf(simulation.getDayOfSimulation()));
            numOfAnimals.setText(String.valueOf(simulation.getAnimals().size()));
            numOfPlants.setText(String.valueOf(simulation.getWorldMap().getPlantsCount()));
            numOFreeSpots.setText(String.valueOf(simulation.getWorldMap().getFreePositionsCount()));
            mostCommonGenotype.setText(String.valueOf(simulation.getStatsController().getMostPopularGenotype()));
            avgEnergyLevel.setText(String.valueOf(simulation.getStatsController().getAvgEnergyLevel()));
            avgLifespan.setText(String.valueOf(simulation.getStatsController().getAvgLifespanForDeadAnimals()));
            avgChildrenNum.setText(String.valueOf(simulation.getStatsController().getAvgNumOfChildrenForAliveAnimals(simulation.getAnimals())));
        });
    }

//    public void setAnimalToTrack(Animal animalToTrack){
//        animalToTrack.addAnimalTracker(this);
//    }
}
