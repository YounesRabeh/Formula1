package it.unicam.cs;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import java.util.List;


import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.api.parser.DrawingParser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;

import static it.unicam.cs.api.components.container.Resources.*;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(FXML_FILE_PATH));
        DrawingParser parser = new DrawingParser(getResourceFile(PARSER_FILE_PATH));

        AtomicReference<GameMap> gameMap = new AtomicReference<>();
        parser.start().ifPresentOrElse(
            gameMap::set,
            () -> {System.err.println("No game map found"); System.exit(0);}
        );

        Canvas[] canvases = gameMap.get().getCanvases();
        // Create a layout pane to hold the canvas
        StackPane root = new StackPane();
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
        List<Waypoint> waypoints = exe(gameMap.get());
        List<Point2D> segmentsEndPoints = gameMap.get().getTrackCanvas().getSegmentsEndPoints();
        //printWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), exe(gameMap));
        drawWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), waypoints);
        //drawParsedSegmentEndPoints(canvases[END_POINTS_LVL].getGraphicsContext2D(), segmentsEndPoints);
        drawConnections(canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
        //drawSegments(canvases[WAYPOINT_LVL].getGraphicsContext2D(), exe(gameMap.get()));
    }



    public static void main(String[] args) {
        launch();
    }
}