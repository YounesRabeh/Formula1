package it.unicam.cs;

import javafx.application.Application;
import javafx.geometry.Point2D;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.unicam.cs.api.components.container.Resources.*;
import static it.unicam.cs.engine.util.Useful.*;


/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            initializeAndShowStage(stage);
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }


    private void initializeAndShowStage(Stage stage) throws IOException, URISyntaxException {
        DrawingParser parser = new DrawingParser(getResourceFile(PARSER_FILE_PATH));
        Optional<GameMap> optionalGameMap = parser.start();

        if (optionalGameMap.isEmpty()) {
            LOGGER.severe("No game map found");
            return;
        }

        GameMap gameMap = optionalGameMap.get();
        Canvas[] canvases = gameMap.getCanvases();

        StackPane root = new StackPane();
        alignAll(root, Pos.CENTER_LEFT, canvases);

        Scene scene = createScene(root);
        configureStage(stage, scene);

        drawGameElements(gameMap, canvases);
    }

    private Scene createScene(StackPane root) throws IOException {
        if (FXML_TEST) {
            FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(FXML_FILE_PATH));
            return new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        } else {
            return new Scene(root, WIDTH, HEIGHT);
        }
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setTitle("Gagata");
        stage.setScene(scene);
        stage.show();
    }

    private void drawGameElements(GameMap gameMap, Canvas[] canvases) {
        List<Waypoint> waypoints = exe(gameMap);
        List<Point2D> segmentsEndPoints = gameMap.getTrackCanvas().getSegmentsEndPoints();

        drawWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), waypoints);
        drawConnections(gameMap.getTrackCanvas(), canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
    }



    public static void main(String[] args) {
        launch();
    }
}