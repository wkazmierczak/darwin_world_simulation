package agh.ics.oop.presenter;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.genotype.Genotype;
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
import javafx.animation.Timeline;
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
import java.util.List;
import java.util.Vector;


public class SimulationPresenter implements AnimalChangeListener, SimulationChangeListener {
    private PlanetMap map;
    private Simulation simulation;

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


    @FXML
    private GridPane mapGrid;
    private final Image penguinImg = new Image(getClass().getClassLoader().getResource("penguin.png").toString());
    private final Image wolfImg = new Image(getClass().getClassLoader().getResource("wolf.png").toString());
    private final Image plantImg = new Image(getClass().getClassLoader().getResource("plant.png").toString());
    private final Image poisonousPlantImg = new Image(getClass().getClassLoader().getResource("plant_poisonous.png").toString());
    private double CELL_SIZE;
    private double CELL_ELEMENT_SIZE;
    private double ANIMAL_IMG_SIZE;
    private SimulationEngine engine;
    private boolean markMostPopular = false;
    private List<Integer> mostPopular;

    public void setSetupData(SimulationSetupData setupData) {
        Simulation simulation = new Simulation(setupData);
        simulation.addSimulationChangeListener(this);
//        simulation.setMapChangeListener(presenter);
        this.simulation = simulation;
        this.map = simulation.getWorldMap();
    }

    public void onSimulationStartClicked() {
        this.engine = new SimulationEngine(simulation);
        engine.runAsync();
    }

    public void onPauseClicked() {
        System.out.println("Pause clicked");
        simulation.freeze();
        markMostPopular = true;
        mostPopular = simulation.getStatsController().getMostPopularGenotype();
        simulationChanged(simulation);
    }


    public void onResumeClicked() {
        simulation.unfreeze();
        markMostPopular = false;
//        engine.resumeSimulation(simulation);
    }

    private boolean isMostPopular(int i, int j, List<Integer> mostPopular) {
        Collection<Animal> animals = map.animalsAt(new Vector2d(i, j));
        return animals != null && animals.stream().anyMatch(a -> a.getGenotype().getGenotypeList().equals(mostPopular));
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

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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
//        markMostPopular = false;
    }

    private GridPane drawCell(int i, int j) {
        Boundary bounds = map.getCurrentBounds();
        int y = bounds.upperRight().getY() - j + 1;
        int x = bounds.bottomLeft().getX() + i;
        Vector2d position = new Vector2d(x, y);
        Plant plant = map.plantAt(position);
        Collection<Animal> animals = map.animalsAt(position);
        GridPane cell = configureCell();
        if (markMostPopular && isMostPopular(x, y, mostPopular)) {
            VBox highlightBox = new VBox();
            highlightBox.setAlignment(Pos.CENTER_LEFT);
            highlightBox.setPadding(new Insets(CELL_SIZE * 0.5));
            highlightBox.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(50), new Insets(0))));
            cell.add(highlightBox, 0, 1);
        }
        if (animals != null && !animals.isEmpty()) {
            drawAnimal(cell, animals.size(), markMostPopular && isMostPopular(i, j, mostPopular));
        }
        if (plant != null) {
            drawPlant(cell, plant);
        }
        if (position.inBounds(map.getSpecialAreaBounds())) {
            drawSpecialArea(cell, new Color(0, 0.9, 0, 0.2));
        }

        return cell;
    }

    private void drawAnimal(GridPane cell, int count, boolean hasMostPopular) {

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
//        if (markMostPopular && hasMostPopular) {
//            animalBox.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(80), new Insets(0))));
//        }

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

        for (int i = 0; i < cellRows; i++)
            cell.getRowConstraints().add(new RowConstraints((double) CELL_SIZE / 3));


        cell.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            Boundary bounds = map.getCurrentBounds();
            int x = GridPane.getColumnIndex(cell);
            int y = GridPane.getRowIndex(cell);
            int y2 = bounds.upperRight().getY() - y + 1;
            int x2 = bounds.bottomLeft().getX() + x - 1;
            Vector2d position = new Vector2d(x2, y2);
            System.out.println("Clicked on " + position);
            handleOnClick(position);
        });
        return cell;
    }

    private void handleOnClick(Vector2d position) {
        Collection<Animal> animals = map.animalsAt(position);
        if (animals == null || animals.isEmpty()) {
            System.out.println("No animals here");
            return;
        }
        Animal animal = animals.stream().findFirst().get();
        animal.addAnimalTracker(this);
        System.out.println("Animal: " + animal.toString());
    }

    @Override
    public synchronized void animalInfoChanged(Animal animal) {
        String genotypeListStr = String.valueOf(animal.getGenotype().getGenotypeList());
        String activeGenotypeStr = String.valueOf(animal.getGenotype().getCurrent());
        String energyLevelStr = String.valueOf(animal.getEnergyLevel());
        String numOfEatenPlantsStr = String.valueOf(animal.getStats().getPlantsEaten());
        String numOfChildrenStr = String.valueOf(animal.getStats().getChildrenCount());
        String numOfDescendantsStr = String.valueOf(animal.getStats().getDescendantsCount());
        String ageStr = String.valueOf(animal.getStats().getAge());
        String dayOfDeathStr = String.valueOf(animal.getStats().getDayOfDeath());

        Platform.runLater(() -> {
            genotype.setText(genotypeListStr);
            activeGenotype.setText(activeGenotypeStr);
            energyLevel.setText(energyLevelStr);
            numOfEatenPlants.setText(numOfEatenPlantsStr);
            numOfChildren.setText(numOfChildrenStr);
            numOfDescendants.setText(numOfDescendantsStr);
            age.setText(ageStr);
            dayOfDeath.setText(dayOfDeathStr);
        });
    }

    @Override
    public synchronized void simulationChanged(Simulation simulation) {
        String dayOfSimStr = String.valueOf(simulation.getDayOfSimulation());
        String numOfAnimalsStr = String.valueOf(simulation.getAnimals().size());
        String numOfPlantsStr = String.valueOf(simulation.getWorldMap().getPlantsCount());
        String numOfFreeSpotsStr = String.valueOf(simulation.getWorldMap().getFreePositionsCount());
        String mostCommonGenotypeStr = String.valueOf(simulation.getStatsController().getMostPopularGenotype());
        String avgEnergyLevelStr = String.valueOf(simulation.getStatsController().getAvgEnergyLevel());
        String avgLifespanStr = String.valueOf(simulation.getStatsController().getAvgLifespanForDeadAnimals());
        String avgChildrenNumStr = String.valueOf(simulation.getStatsController().getAvgNumOfChildrenForAliveAnimals(simulation.getAnimals()));

        Platform.runLater(() -> {
            drawMap();
            dayOfSim.setText(dayOfSimStr);
            numOfAnimals.setText(numOfAnimalsStr);
            numOfPlants.setText(numOfPlantsStr);
            numOFreeSpots.setText(numOfFreeSpotsStr);
            mostCommonGenotype.setText(mostCommonGenotypeStr);
            avgEnergyLevel.setText(avgEnergyLevelStr);
            avgLifespan.setText(avgLifespanStr);
            avgChildrenNum.setText(avgChildrenNumStr);
        });
    }


//    public void setAnimalToTrack(Animal animalToTrack){
//        animalToTrack.addAnimalTracker(this);
//    }
}
