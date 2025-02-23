package it.unicam.cs.engine.nav;


import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.actors.structs.Movement;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.geometry.Point2D;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A utility class that provides tools to work with routes.
 * @author Younes Rabeh
 * @version 1.2
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
     * @see GameMap.Waypoint
     */
    public static Collection<GameMap.Waypoint> generateGameMapWaypoints(GameMap gameMap) {
        Check.checkNull(gameMap);
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        WritableImage snapshot = trackCanvas.getTrackSnapshot();
        final int step = gameMap.getGridCanvas().getCellSize();
        List<GameMap.Waypoint> waypoints = new ArrayList<>();

        for (int x = 0; x < trackCanvas.getWidth(); x += step) {
            for (int y = 0; y < trackCanvas.getHeight(); y += step) {
                Color pixelColor = CanvasTools.getPixelColor(x, y, snapshot);
                if (pixelColor.equals(trackCanvas.getColor())) {
                    waypoints.add(gameMap.createWaypoint(x, y));
                }
            }
        }
        return waypoints;
    }

    /**
     * Get the waypoints of the game map.
     * @param gameMap the game map
     * @return the waypoints of the game map
     */
    public static Collection<GameMap.Waypoint> findMapWaypoints(GameMap gameMap){
        long startTime = System.nanoTime();
        Collection<GameMap.Waypoint> waypoints = RouteTools.generateGameMapWaypoints(gameMap);
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " nanoseconds " +
                "or " + (float) (endTime - startTime) / 1000000 + " milliseconds\n" +
                "> Found " + waypoints.size() + " black pixels"
        );
        return waypoints;
    }

    /**
     * Get the possible next (reachable) waypoints, given a waypoint.
     * @return the possible next waypoints
     */
    public Collection<GameMap.Waypoint> getPossibleNextWaypoints(GameMap.Waypoint waypoint, GameMap gameMap) {
        Collection<GameMap.Waypoint> possibleNextWaypoints = new ArrayList<>();
        int cellSize = gameMap.getGridCanvas().getCellSize();

        for (Movement movement : Movement.values()) {
            double newX = waypoint.getX() + movement.getXOffset() * cellSize;
            double newY = waypoint.getY() + movement.getYOffset() * cellSize;
            try {
                GameMap.Waypoint nextWaypoint = gameMap.createWaypoint(newX, newY);
                if (gameMap.getTrackCanvas().containsWaypoint(nextWaypoint)) {
                    possibleNextWaypoints.add(nextWaypoint);
                }
            } catch (IllegalArgumentException ignored) {
                // Ignore waypoints that are not on the grid intersection
            }
        }

        return possibleNextWaypoints;
    }

    /**
     * Snaps a point to the grid.
     * @param point the point to snap
     * @param gameMap the game map
     * @return the snapped waypoint
     */
    public static GameMap.Waypoint snapToGrid(Point2D point, GameMap gameMap) {
        int cellSize = gameMap.getGridCanvas().getCellSize();
        double x = point.getX();
        double y = point.getY();
        double newX = x - (x % cellSize);
        double newY = y - (y % cellSize);
        return gameMap.createWaypoint(newX, newY);
    }
}
