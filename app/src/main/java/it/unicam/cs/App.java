package it.unicam.cs;

import it.unicam.cs.api.components.container.DebugData;

import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.engine.core.route.RouteTools;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.api.parser.DrawingParser;
import it.unicam.cs.api.components.container.Graphics;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static it.unicam.cs.api.components.container.Resources.*;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(FXML_FILE_PATH));
        DrawingParser parser = new DrawingParser(getResourceFile(PARSER_FILE_PATH));

        GameMap gameMap = parser.start().get();
        Canvas[] canvases = gameMap.getCanvases();

        // Create a layout pane to hold the canvas
        StackPane root = new StackPane();
        //root.setBackground(Background.fill(Color.rgb(0 ,200, 0)));

        // Align the canvases to the left side
        alignAll(root ,Pos.CENTER_LEFT, canvases);

        // NOTE: THE APP SCENE
        Scene scene;
        if (FXML_TEST){
            scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        } else {
            scene = new Scene(root, WIDTH, HEIGHT);
        }

        // Set up the stage and show it
        stage.setTitle("Gagata ");
        stage.setScene(scene);
        stage.show();

        // - 3 to get @Waypoints canvas
        //printWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), exe(gameMap));
        drawSegments(canvases[WAYPOINT_LVL].getGraphicsContext2D(), exe(gameMap));


    }

    private List<Waypoint> exe(GameMap gameMap){
        long startTime = System.nanoTime();
        List<Waypoint> waypoints = RouteTools.getGameMapWaypoints(gameMap);
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " nanoseconds " +
                "or " + (float) (endTime - startTime) / 1000000 + " milliseconds\n" +
                "> Found " + waypoints.size() + " black pixels"
        );
        return waypoints;
    }

    private void printWaypoints(GraphicsContext gc, List<Waypoint> waypoints) {
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (Waypoint coords : waypoints) {
            System.out.printf("Black pixel found at (%d, %d)\n", (int) coords.getX(), (int) coords.getY());
            Graphics.strokePoint(gc, new int[]{(int) coords.getX(), (int) coords.getY()});
        }
    }

    private void drawSegments(GraphicsContext gc, List<Waypoint> waypoints){


    }

    private void alignAll(StackPane root, Pos pos, Node[] nodes){
        for (Node node : nodes) {
            StackPane.setAlignment(node, pos);
        }
        root.getChildren().addAll(nodes);
    }

    public static void main(String[] args) {
        launch();
    }
}