package it.unicam.cs.gui.util;

import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;

import static it.unicam.cs.DebugData.*;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * Utility class for GUI operations
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0
 */
public final class GuiTools {
    private GuiTools() {}

    /**
     * Attach a listener to the stage to maximize/minimize the window.
     * @param stage the stage of the application
     * @param WIDTH the width of the window
     * @param HEIGHT the height of the window
     * @param MIN_WIDTH the minimum width of the window
     * @param MIN_HEIGHT the minimum height of the window
     */
    public static void attach_WindowMaximizedListener(
            Stage stage,
            double WIDTH, double HEIGHT, double MIN_WIDTH, double MIN_HEIGHT
    ){
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stage.setWidth(WIDTH);
                stage.setHeight(HEIGHT);
            } else {
                stage.setWidth(MIN_WIDTH);
                stage.setHeight(MIN_HEIGHT);
            }
        });
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
        drawConnections(trackCanvas, canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
        drawParsedSegmentEndPoints(canvases[END_POINTS_LVL].getGraphicsContext2D(), segmentsEndPoints);
    }
}
