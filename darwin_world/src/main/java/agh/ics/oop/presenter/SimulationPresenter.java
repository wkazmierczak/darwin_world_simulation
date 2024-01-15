package agh.ics.oop.presenter;

import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.listeners.MapChangeListener;
import agh.ics.oop.model.listeners.SimulationChangeListener;
import agh.ics.oop.model.maps.EquatorMap;
import agh.ics.oop.model.maps.PlanetMap;
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


public class SimulationPresenter implements SimulationChangeListener {

    private PlanetMap map;
    private Simulation simulation;

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

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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


//                cell.add( label1, 1, 0);
//                cell.add(label2, 2, 0);

                mapGrid.add(drawCell(i, j), i + 1, j);
//                GridPane.setHalignment(label, HPos.CENTER);
            }

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
//        Label label = new Label();

//                cell.setBackground(plant != null ? new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), new Insets(0))) : null);


//        label.setText(plant != null ? plant.toString() : "");
//        Label label1 = new Label();
//        label1.setText(plant != null ? plant.toString() : "");
//        Label label2 = new Label();
//        label2.setText(plant != null ? plant.toString() : "");
//        ImageView pengView = new ImageView(wolf);


//        cell.add(pengView, 0, 1);
//        cell.add(label1,2,2);
        return cell;
    }

    private void drawAnimal(GridPane cell, int count) {
//        pengView.setFitHeight(ANIMAL_IMG_HEIGHT);
////                        .bind(cell.heightProperty().divide(3).multiply(1));
//        pengView.setFitWidth(ANIMAL_IMG_WIDTH);
////                        .bind(cell.widthProperty().divide(3).multiply(1));
////                pengView.fitWidthProperty().bind(cell.widthProperty().divide(3).multiply(1));


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

        for (int i = 0; i < cellRows; i++)
            cell.getRowConstraints().add(new RowConstraints((double) CELL_SIZE / 3));

        return cell;
    }

    @Override
    public void simulationChanged(Simulation simulation) {
        Platform.runLater(this::drawMap);
    }

}