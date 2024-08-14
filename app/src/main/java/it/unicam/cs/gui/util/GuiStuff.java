package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Movement;
import it.unicam.cs.api.parser.types.DrawingParser;
import it.unicam.cs.engine.core.route.RouteFinder;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static it.unicam.cs.DebugData.*;
import static it.unicam.cs.api.components.container.Resources.getResourceFile;
import static it.unicam.cs.api.components.container.Resources.getResourceURL;
import static it.unicam.cs.api.parser.Information.F1_MAP_FILE_EXTENSION;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * A class that contains useful methods for the GUI.
 */
public class GuiStuff {

    /**
     * Initializes and shows the stage. The stage is initialized with the given game map.
     * The game map is parsed from the drawing parser.
     * @param stage the stage to initialize and show
     * @param LOGGER the logger
     * @throws IOException if an I/O error occurs
     * @throws URISyntaxException if a syntax error occurs
     */
    public static void initializeAndShowStage(Stage stage, Logger LOGGER) throws IOException, URISyntaxException {
        DrawingParser parser = new DrawingParser(
                getResourceFile(PARSER_FILE_PATH),
                F1_MAP_FILE_EXTENSION
        );
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

        drawGameElements(gameMap);
        //System.out.println(
        //        gameMap.getFinishLine().getWaypoints()
        //);

        //System.out.println("Route found: " + RouteFinder.findRoute(
        //        gameMap.getTrackCanvas().getWaypoints(),
        //        gameMap,
        //        gameMap.createWaypoint(100, 300)
        //));
        GameMap.Waypoint origin = gameMap.createWaypoint(100, 300);

        gameMap.getPossibleNextWaypoints(gameMap, origin)
            .forEach(waypoint -> {
                for (Movement movement : Movement.values()) {
                    double expectedX = origin.getX() + movement.getXOffset() * gameMap.getGridCanvas().getCellSize();
                    double expectedY = origin.getY() + movement.getYOffset() * gameMap.getGridCanvas().getCellSize();
                    if (waypoint.getX() == expectedX && waypoint.getY() == expectedY) {
                        System.out.println(waypoint + " " + movement);
                        break;
                    }
                }
            }
        );

    }

    /**
     * Creates a scene with the given root.
     * @param root the root of the scene
     * @return the scene
     * @throws IOException if an I/O error occurs
     */
    private static Scene createScene(StackPane root) throws IOException {
        if (FXML_TEST) {
            FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(FXML_FILE_PATH));
            return new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        } else {
            return new Scene(root, WIDTH, HEIGHT);
        }
    }

    /**
     * Configures the stage with the given scene.
     * @param stage the stage to configure
     * @param scene the scene to set
     */
    private static void configureStage(Stage stage, Scene scene) {
        stage.setTitle(APP_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draws the game elements on the canvases.
     * @param gameMap the game map
     */
    private static void drawGameElements(GameMap gameMap) {
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        Canvas[] canvases = gameMap.getCanvases();
        Collection<GameMap.Waypoint> waypoints = trackCanvas.getWaypoints();
        List<Point2D> segmentsEndPoints = trackCanvas.getSegmentsEndPoints();

        drawWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), (List<GameMap.Waypoint>) waypoints);
        //drawConnections(trackCanvas, canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
        drawParsedSegmentEndPoints(canvases[END_POINTS_LVL].getGraphicsContext2D(), segmentsEndPoints);
    }
}
