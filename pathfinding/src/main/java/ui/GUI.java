package ui;

import graph.Graph;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {

    // Constants for graph size
    private final int SIZE_X = 25, SIZE_Y = 25, NODE_SIZE = 30;
    private GraphicsContext gc;
    /*
    0 = Empty
    1 = Start
    2 = End
    3 = Wall
    4 = Path
     */
    private final Color[] NODE_COLORS = new Color[]{
        Color.WHITE,
        Color.GREEN,
        Color.RED,
        Color.BLACK,
        Color.YELLOW
    };
    private int selectedNodeType;
    private int[][] state;
    private Graph graph;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pathfinding");
        Canvas canvas = new Canvas(SIZE_X * NODE_SIZE, SIZE_Y * NODE_SIZE);
        gc = canvas.getGraphicsContext2D();
        selectedNodeType = 0;
        state = new int[SIZE_X][SIZE_Y];
        graph = new Graph(state, SIZE_X, SIZE_Y);
        drawGrid();
        drawNodes();

        // Radio buttons and toggle group for selecting node type
        RadioButton startNodeButton = new RadioButton("Start");
        RadioButton endNodeButton = new RadioButton("End");
        RadioButton wallNodeButton = new RadioButton("Wall");
        RadioButton emptyNodeButton = new RadioButton("Empty");
        ToggleGroup nodeTypeGroup = new ToggleGroup();

        // Add radio buttons to toggle group
        startNodeButton.setToggleGroup(nodeTypeGroup);
        endNodeButton.setToggleGroup(nodeTypeGroup);
        wallNodeButton.setToggleGroup(nodeTypeGroup);
        emptyNodeButton.setToggleGroup(nodeTypeGroup);

        // By default empty will be selected
        emptyNodeButton.setSelected(true);

        // Setting user data for event listener
        emptyNodeButton.setUserData(0);
        startNodeButton.setUserData(1);
        endNodeButton.setUserData(2);
        wallNodeButton.setUserData(3);

        // Event listener for changing node type
        nodeTypeGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) -> {
            if (nodeTypeGroup.getSelectedToggle() != null) {
                int selected = Integer.parseInt(newToggle.getUserData().toString());
                selectedNodeType = selected;
            }
        });

        canvas.setOnMouseDragged((event) -> {
            changeNode(event);
            drawNodes();
        });

        canvas.setOnMouseClicked((event) -> {
            changeNode(event);
            drawNodes();
        });

        Button solveButton = new Button("Solve");
        solveButton.setOnAction((event) -> {
            graph.solve();
            drawNodes();
        });

        VBox menuItems = new VBox(emptyNodeButton, startNodeButton, endNodeButton, wallNodeButton, solveButton);
        HBox root = new HBox(canvas, menuItems);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawGrid() {
        gc.setStroke(Color.BLACK);

        int graphSizeX = SIZE_X * NODE_SIZE;
        int graphSizeY = SIZE_Y * NODE_SIZE;

        for (int i = 0; i <= graphSizeX; i += NODE_SIZE) {
            gc.strokeLine(i, 0, i, graphSizeY);
        }
        for (int i = 0; i <= graphSizeY; i += NODE_SIZE) {
            gc.strokeLine(0, i, graphSizeX, i);
        }
    }

    private void drawNodes() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                gc.setFill(NODE_COLORS[state[x][y]]);
                gc.fillRect(x * NODE_SIZE + 1, y * NODE_SIZE + 1, NODE_SIZE - 2, NODE_SIZE - 2);
            }
        }
    }

    private void changeNode(MouseEvent event) {
        int posX = (int) event.getX() / NODE_SIZE;
        int posY = (int) event.getY() / NODE_SIZE;
        // Check added so dragging mouse outside canvas doesn't cause errors
        if (posX > -1 && posY > -1 && posX < SIZE_X && posY < SIZE_Y) {
            graph.changeNode(posX, posY, selectedNodeType);
        }
    }
}
