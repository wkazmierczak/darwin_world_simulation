package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation.Simulation;
import agh.ics.oop.Simulation.SimulationEngine;
import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.genotype.GenotypeType;
import agh.ics.oop.model.listeners.MapChangeListener;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.text.Element;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collection;
import java.util.List;
import java.util.Vector;


public class SimulationPresenter implements MapChangeListener {

    private PlanetMap map;
    private Simulation simulation;

    @FXML
    private Label description;

    @FXML
    private TextField movesInput;

    @FXML
    private GridPane mapGrid;
    private final Image penguin = new Image(getClass().getClassLoader().getResource("penguin.png").toString());
    private final Image wolf = new Image(getClass().getClassLoader().getResource("wolf.png").toString());
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;
    private static final double ANIMAL_IMG_WIDTH = (double) (CELL_WIDTH * 2) /3*0.95;
    private static final double ANIMAL_IMG_HEIGHT = (double) (CELL_HEIGHT * 2) /3*0.95;

    public void assignSimulationAndMap(Simulation simulation, PlanetMap map) {
        this.simulation = simulation;
        this.map = map;
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
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        Label label = new Label();
        label.setText("x/y");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        Boundary bounds = map.getCurrentBounds();
        System.out.println(bounds);
        for (int i = 1; i <= bounds.upperRight().getX() - bounds.bottomLeft().getX() + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            label = new Label();
            Integer labelText = bounds.bottomLeft().getX() + i - 1;
            label.setText(labelText.toString());
            mapGrid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int j = 1; j <= bounds.upperRight().getY() - bounds.bottomLeft().getY(); j++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            label = new Label();
            Integer labelText = bounds.upperRight().getY() - j + 1;
            label.setText(labelText.toString());
            mapGrid.add(label, 0, j);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    
    public void drawMap() {
        clearGrid();
        configureGrid();
        Boundary bounds = map.getCurrentBounds();
        for (int i = 0; i <= bounds.upperRight().getX() - bounds.bottomLeft().getX(); i++) {
            for (int j = 0; j <= bounds.upperRight().getY() - bounds.bottomLeft().getY(); j++) {

                
//                cell.add( label1, 1, 0);
//                cell.add(label2, 2, 0);

                mapGrid.add(drawCell(i,j), i + 1, j);
//                GridPane.setHalignment(label, HPos.CENTER);
            }

        }

    }
    private GridPane drawCell(int i,int j){
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

    private void drawAnimal( GridPane cell,int count) {
//        pengView.setFitHeight(ANIMAL_IMG_HEIGHT);
////                        .bind(cell.heightProperty().divide(3).multiply(1));
//        pengView.setFitWidth(ANIMAL_IMG_WIDTH);
////                        .bind(cell.widthProperty().divide(3).multiply(1));
////                pengView.fitWidthProperty().bind(cell.widthProperty().divide(3).multiply(1));

        ImageView animalImgView = new ImageView(wolf);
        animalImgView.setFitHeight(ANIMAL_IMG_HEIGHT);
        animalImgView.setFitWidth(ANIMAL_IMG_WIDTH);
        cell.add(animalImgView, 0, 1);
        cell.add(new Label('x' + Integer.toString(count)), 2, 2);
    }

    private GridPane configureCell(){
        GridPane cell = new GridPane();
        int cellRows = 3;
        int cellColumns = 3;
        for (int i = 0; i<cellColumns; i++)
            cell.getColumnConstraints().add(new ColumnConstraints((double) CELL_WIDTH /3));
        
        for (int i = 0; i<cellRows; i++)
            cell.getRowConstraints().add(new RowConstraints((double) CELL_HEIGHT /3));
        
        return cell;
    }

    @Override
    public void mapChanged(PlanetMap worldMap, String message) {
        Platform.runLater(() -> {
            description.setText(message);
            drawMap();
        });
    }

}