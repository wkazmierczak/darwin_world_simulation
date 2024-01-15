package agh.ics.oop.presenter;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.MapChangeListener;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.EquatorMap;
import agh.ics.oop.model.maps.PlanetMap;
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
import agh.ics.oop.model.worldElements.plants.Plant;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.stage.Window;

import javax.swing.*;
import java.util.Collection;




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
    private Label description;

    @FXML
    private TextField movesInput;

    @FXML
    private GridPane mapGrid;
    private final Image penguinImg = new Image(getClass().getClassLoader().getResource("penguin.png").toString());
    private final Image wolfImg = new Image(getClass().getClassLoader().getResource("wolf.png").toString());
    private final Image plantImg = new Image(getClass().getClassLoader().getResource("plant.png").toString());
    private final Image poisonousPlantImg = new Image(getClass().getClassLoader().getResource("plant_poisonous.png").toString());
    private double CELL_SIZE;
    private double CELL_ELEMENT_SIZE;
    private double ANIMAL_IMG_SIZE;

    public void assignSimulationAndMap(Simulation simulation, PlanetMap map) {
        this.simulation = simulation;
        this.map = map;
        setSizes();
    }

    @FXML
    void onSimulationStartClicked() {
        SimulationEngine engine = new SimulationEngine(simulation);
        engine.runAsync();
//        engine.awaitSimulationEnd();
//        simulationState.setText("finished");
    }

    public String[] collectInput() {
        return movesInput.getText().split(" ");
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


    private void configureGrid() {

        mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        Label label = new Label();
        label.setText("x/y");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        Boundary bounds = map.getCurrentBounds();
        for (int i = 1; i <= bounds.upperRight().getX() - bounds.bottomLeft().getX() + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            label = new Label();
            int labelText = bounds.bottomLeft().getX() + i - 1;
            label.setText(Integer.toString(labelText));
            mapGrid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int j = 1; j <= bounds.upperRight().getY() - bounds.bottomLeft().getY(); j++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            label = new Label();
            int labelText = bounds.upperRight().getY() - j + 1;
            label.setText(Integer.toString(labelText));
            mapGrid.add(label, 0, j);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    public void setSizes() {
        int cell_columns = 3;
        Window window = mapGrid.getScene().getWindow();
        CELL_SIZE = window.getWidth() / Math.max(map.getCurrentBounds().getWidth(), map.getCurrentBounds().getHeight()) * 0.5;
        CELL_ELEMENT_SIZE = (CELL_SIZE / cell_columns) * 0.95;
        ANIMAL_IMG_SIZE = CELL_ELEMENT_SIZE * 2;
    }

    public void drawMap() {
        clearGrid();
        setSizes();
        configureGrid();
        Boundary bounds = map.getCurrentBounds();
        for (int i = 0; i <= bounds.upperRight().getX() - bounds.bottomLeft().getX(); i++) {
            for (int j = 0; j <= bounds.upperRight().getY() - bounds.bottomLeft().getY(); j++) {

                mapGrid.add(drawCell(i, j), i + 1, j);

        }

    }

    private GridPane drawCell(int i, int j) {
        Boundary bounds = map.getCurrentBounds();
        int y = bounds.upperRight().getY() - j + 1;
        int x = bounds.bottomLeft().getX() + i;
        Vector2d position = new Vector2d(x, y);
        Plant plant = map.plantAt(position);
        Collection<Animal> animals = map.animalsAt(position);
        GridPane cell = configureCell();
        if (animals != null && !animals.isEmpty()) {
            drawAnimal(cell, animals.size());
        }
        if (plant != null) {
            drawPlant(cell, plant);
        }
        if (position.inBounds(map.getSpecialAreaBounds())) {
            drawSpecialArea(cell, new Color(0, 0.9, 0, 0.2));
        }
        return cell;
    }

    private void drawAnimal(GridPane cell, int count) {

        ImageView animalImgView = new ImageView(wolfImg);
        animalImgView.setFitHeight(ANIMAL_IMG_SIZE);
        animalImgView.setFitWidth(ANIMAL_IMG_SIZE);
        VBox animalBox = new VBox();
        animalBox.setAlignment(Pos.CENTER_LEFT);
        animalBox.getChildren().add(animalImgView);
        animalBox.setPadding(new Insets(CELL_ELEMENT_SIZE * 1.2));
//        animalBox.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(100), new Insets(10))));

        if (CELL_ELEMENT_SIZE > 15) { //visible size
            Label animalCount = new Label('x' + Integer.toString(count));
            animalCount.setFont(new Font(CELL_ELEMENT_SIZE / 2));
            cell.add(animalCount, 2, 2);
        } else {
            Color color = new Color(1, 0, 0, Math.min(count / 5.0, 1));
            animalBox.setBackground(new Background(new BackgroundFill(color, new CornerRadii(100), new Insets(10))));
        }
        cell.add(animalBox, 0, 1);
    }

    private void drawPlant(GridPane cell, Plant plant) {
        ImageView plantImgView = new ImageView(plant.isPoisonous() ? poisonousPlantImg : plantImg);
        plantImgView.setFitHeight(CELL_ELEMENT_SIZE * 2);
        plantImgView.setFitWidth(CELL_ELEMENT_SIZE * 2);
        cell.add(plantImgView, 0, 1);
    }

    private void drawSpecialArea(GridPane cell, Color color) {
        cell.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0))));
    }

    private GridPane configureCell() {
        GridPane cell = new GridPane();
        int cellRows = 3;
        int cellColumns = 3;
        for (int i = 0; i < cellColumns; i++)
            cell.getColumnConstraints().add(new ColumnConstraints((double) CELL_SIZE / 3));


    @Override
    public void simulationChanged(Simulation simulation) {
        Platform.runLater(this::drawMap);
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
