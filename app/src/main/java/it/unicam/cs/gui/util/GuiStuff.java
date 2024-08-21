package it.unicam.cs.gui.util;

import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

import java.util.Collection;
import java.util.List;

import static it.unicam.cs.DebugData.*;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * A class that contains useful methods for the GUI.
 */
public class GuiStuff {
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
