package it.unicam.cs.engine.core.route;


import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.engine.util.Check;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Younes Rabeh
 */
public final class RouteTools {
    /** Prevent instantiation of this utility class. */
    private RouteTools(){}

    /**
     * Gets the waypoints of the game map.
     * The waypoints are the points where the track intersects the grid.
     * The waypoints are used to create the route of the car.
     *
     *
     * @param gameMap the game map
     * @return the waypoints of the game map
     * @throws IllegalStateException if the track is not yet drawn
     * @see Waypoint
     */
    public static List<Waypoint> getGameMapWaypoints(GameMap gameMap) {
        Check.checkNull(gameMap);
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        WritableImage snapshot = trackCanvas.getCanvasSnapshot();
        final int step = gameMap.getGridCanvas().getCellSize();
        List<Waypoint> waypoints = new ArrayList<>();

        for (int x = 0; x < trackCanvas.getWidth(); x += step) {
            for (int y = 0; y < trackCanvas.getHeight(); y += step) {
                Color pixelColor = CanvasTools.getPixelColor(x, y, snapshot);
                if (pixelColor.equals(trackCanvas.getColor())) {
                    waypoints.add(new Waypoint(x, y));
                }
            }
        }

        return waypoints;
    }
}