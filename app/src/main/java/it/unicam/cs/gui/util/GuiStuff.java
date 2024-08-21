package it.unicam.cs.gui.util;

import it.unicam.cs.api.parser.types.DrawingParser;
import it.unicam.cs.gui.controller.SceneController;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCombination;
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
     * Creates a scene with the given root.
     * @param root the root of the scene
     * @return the scene
     * @throws IOException if an I/O error occurs
     */
    public static Scene createScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(WELCOME_SCENE_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        return scene;
    }

    /**
     * Configures the stage with the given scene.
     * @param stage the stage to configure
     * @param scene the scene to set
     */
    public static void configureStage(Stage stage, Scene scene) {
        stage.setTitle(APP_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draws the game elements on the canvases.
     * @param gameMap the game map
     */
    public static void drawGameElements(GameMap gameMap) {
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        Canvas[] canvases = gameMap.getCanvases();
        Collection<GameMap.Waypoint> waypoints = trackCanvas.getWaypoints();
        List<Point2D> segmentsEndPoints = trackCanvas.getSegmentsEndPoints();

        drawWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), (List<GameMap.Waypoint>) waypoints);
        //drawConnections(trackCanvas, canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
        drawParsedSegmentEndPoints(canvases[END_POINTS_LVL].getGraphicsContext2D(), segmentsEndPoints);
    }
}
