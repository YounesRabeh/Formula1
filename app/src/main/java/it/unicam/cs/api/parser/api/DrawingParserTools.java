package it.unicam.cs.api.parser.api;


import it.unicam.cs.engine.core.route.RouteTools;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;

import static it.unicam.cs.engine.core.route.RouteTools.findMapWaypoints;

/**
 * A utility class that provides helper methods for the drawing parser.
 * @author Younes Rabeh
 * @version 1.0
 */
public final class DrawingParserTools {
    /** To prevent instantiation */
    private DrawingParserTools() {}

    /**
     * Check if the finish line is set in a non-closed track.
     * Used before switching the context (moving on to the next canvas).
     * @param trackCanvas the track canvas
     * @throws IllegalStateException if the finish line is not set
     */
    public static void checkFinishLineBeforeSwitchingContext(TrackCanvas trackCanvas){
        if (!trackCanvas.getTrackState() && (trackCanvas.getFinishLine() == null)){
            throw new IllegalStateException("[!!]- NO FINISH LINE FOUND FOR THE NON CLOSED TRACK");
        }
    }

    /**
     * Check if the drawing of the finish line is admissible.
     * @param trackCanvas the track canvas
     * @throws IllegalStateException if the start line is not set or the track is closed
     */
    public static void checkIfDrawingFinishLineIsAdmissible(TrackCanvas trackCanvas){
        if (trackCanvas.getStartLine() == null){
            throw new IllegalStateException("[!!]- NO STARTING LINE");
        } else if (trackCanvas.getTrackState()) {
            throw new IllegalStateException("[!!]- THE FINISH LINE CAN'T BE DRAWN ON A CLOSED TRACK");
        }
    }

    /**
     * Generates a track marker from the given command. A track marker is a line that serves a specific purpose in the track.
     * @param trackCanvas the track canvas
     * @param params the parameters of the origin point of the track marker
     * @return the generated line
     */
    public static Line generateTrackMarker(TrackCanvas trackCanvas, int[] params){
        return CanvasTools.createLineFromPoint(
                trackCanvas, new Point2D(params[0], params[1])
        );
    }

    /**
     * Generates the track snapshot. The track snapshot is a snapshot of the track canvas used
     * to get the admissible waypoints of the track & run the routeFinder algorithm.
     * <p> The track snapshot is generated only once. </p>
     * <b> THIS METHOD ALSO RUNS THE WAYPOINTS FINDER ALGORITHM (stoppage code for sum time)</b>
     * as in {@link RouteTools#findMapWaypoints}
     * @param map the game map
     * @param trackCanvas the track canvas
     * @param currentGC the current graphics context
     * @throws IllegalStateException if the snapshot has not been set
     */
    public static void generateTrackSnapshot(GameMap map, TrackCanvas trackCanvas, GraphicsContext currentGC){
        try {
            trackCanvas.getTrackSnapshot();
        } catch (IllegalStateException noSnapshot){
            currentGC.stroke();
            trackCanvas.setSnapshot(CanvasTools.createCanvasSnapshot(trackCanvas));
            trackCanvas.addWaypoints(findMapWaypoints(map));
        }
    }

}
