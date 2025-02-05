package it.unicam.cs.engine.nav;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.structs.Inertia;
import it.unicam.cs.api.components.actors.structs.Movement;
import it.unicam.cs.gui.map.GameMap;

import java.util.*;

/**
 *
 * @author Younes Rabeh
 * @version 1.3
 */
public final class RouteFinder {
    /** Prevent instantiation of this utility class. */
    private RouteFinder() {}

    /**
     * Gets the best target for the current waypoint.
     * The best target is the waypoint that is closest to the current waypoint.
     *
     * @param currentPosition the current waypoint
     * @param targets the possible targets
     * @return the best target
     */
    public static GameMap.Waypoint getBestTarget(
            GameMap.Waypoint currentPosition,
            Collection<GameMap.Waypoint> targets
    ) { //TODO: add the parsed (snapped) segment endpoints to the path
        return targets.stream()
                .filter(Objects::nonNull)  // Ignore null waypoints (non-admissible)
                .min(Comparator.comparingDouble(wp -> wp.distance(currentPosition)))
                .orElse(null);
    }

    /**
     * This method reuses the logic from the previous one for getting the best target
     * from an array of waypoints.
     *
     * @param waypoints the array of waypoints
     * @param targets the possible targets
     * @return the best target from the waypoints array
     */
    public static GameMap.Waypoint getBestTarget(
            GameMap.Waypoint[] waypoints,
            Collection<GameMap.Waypoint> targets
    ) {

        if (waypoints == null || waypoints.length == 0 || targets == null || targets.isEmpty()) {
            return null;
        }

        // Reusing the logic from the collection-based version
        return Arrays.stream(waypoints)
                .filter(Objects::nonNull)  // Ignore null waypoints
                .min(Comparator.comparingDouble(wp -> calculateMinimumDistance(wp, targets)))
                .orElse(null);  // Return null if no valid waypoint found
    }

    /**
     * Calculate the minimum distance from the given waypoint to any target in the collection of targets.
     * @param waypoint the waypoint to evaluate
     * @param targets the collection of target waypoints
     * @return the minimum distance to any target
     */
    private static double calculateMinimumDistance(GameMap.Waypoint waypoint, Collection<GameMap.Waypoint> targets) {
        return targets.stream()
                .filter(Objects::nonNull)  // Ignore null targets
                .mapToDouble(target -> waypoint.distance(target))  // Calculate distance from waypoint to each target
                .min()  // Get the smallest distance
                .orElse(Double.MAX_VALUE);  // Return a large value if no targets are available
    }



    /**
     * Gets the possible next waypoints for the driver.
     * The possible next waypoints are the waypoints that the driver can move to.
     * The waypoints are calculated based on the driver's inertia and the possible movements.
     *
     * @param gameMap the game map
     * @param driver the driver
     * @return the possible next waypoints, always 9 elements (null if not admissible)
     */
    public static GameMap.Waypoint[] getPossibleNextWaypoints(
            GameMap gameMap,
            Driver driver
    ) {

        int cellSize = gameMap.getGridCanvas().getCellSize();
        GameMap.Waypoint current = driver.getPosition();
        GameMap.Waypoint[] waypoints = new GameMap.Waypoint[Movement.values().length];

        Inertia inertia = driver.getInertia();
        Movement[] movements = Movement.values();

        for (int i = 0; i < movements.length; i++) {
            double newX = current.getX() + (movements[i].getXOffset() + inertia.getX()) * cellSize;
            double newY = current.getY() + (movements[i].getYOffset() + inertia.getY()) * cellSize;

            try {
                GameMap.Waypoint nextWaypoint = gameMap.createWaypoint(newX, newY);
                if (gameMap.getTrackCanvas().containsWaypoint(nextWaypoint)) {
                    if (gameMap.isWaypointFree(nextWaypoint)) {
                        waypoints[i] = nextWaypoint;
                    }
                }
            } catch (IllegalArgumentException ignored) {
                waypoints[i] = null;
            }
        }

        return waypoints;
    }



}

