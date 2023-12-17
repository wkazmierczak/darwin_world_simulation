package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
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
import javafx.scene.text.Text;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private static final double CELL_WIDTH = 30 ;
    private static final double CELL_HEIGHT = 30;
    private static WorldMap<WorldElement, Vector2d> map;

    @FXML
    public Button startButton;

    @FXML
    public GridPane mapGrid;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField textField;

    public void setWorldMap(WorldMap<WorldElement, Vector2d> map) {
        this.map = map;
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
                WorldElement elem = map.objectAt(currentPosition);
                if (elem != null){
                    Label cellLabel = new Label();
                    cellLabel.setMinSize(10, 10);
                    cellLabel.setText(map.objectAt(currentPosition).toString());
                    mapGrid.add(cellLabel, col,row);
                    GridPane.setHalignment(cellLabel, HPos.CENTER);


                }
                row++;
            }
            col++;
        }



    }


    @Override
    public void mapChanged(WorldMap<WorldElement, Vector2d> worldMap, String message) {


        Platform.runLater(() -> {
            infoLabel.setText(message);
            drawMap();
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {


        String[] input1 = textField.getText().split(" ");

        List<MoveDirection> directions2 = OptionsParser.parse(input1);
        List<Vector2d> positions2 = List.of(new Vector2d(2, 2), new Vector2d(-3,2));


        Simulation simulation2 = new Simulation(directions2, positions2, map);
        List<Simulation> sims = List.of(simulation2);
        SimulationEngine engine = new SimulationEngine(sims);
//        engine.runSync();
        engine.runAsync();
        engine.awaitSimulationEnd();


//        simulation2.run();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
