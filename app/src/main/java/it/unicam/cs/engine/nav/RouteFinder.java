package it.unicam.cs.engine.nav;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.structs.Inertia;
import it.unicam.cs.api.components.actors.structs.Movement;
import it.unicam.cs.gui.map.GameMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Younes Rabeh
 * @version 1.0
 */
public final class RouteFinder {
    /** Prevent instantiation of this utility class. */
    private RouteFinder() {}

    /**
     * Gets the best target for the current waypoint.
     * The best target is the waypoint that is closest to the current waypoint.
     *
     * @param current the current waypoint
     * @param targets the possible targets
     * @return the best target
     */
    public static GameMap.Waypoint getBestTarget(
            GameMap.Waypoint currentPosition,
            Collection<GameMap.Waypoint> targets
    ) {
        return targets.stream()
                .filter(Objects::nonNull)  // Ignore null waypoints
                .min(Comparator.comparingDouble(wp -> wp.distance(currentPosition)))
                .orElse(null);
    }


    /**
     * Gets the possible next waypoints for the driver.
     * The possible next waypoints are the waypoints that the driver can move to.
     * The waypoints are calculated based on the driver's inertia and the possible movements.
     *
     * @param current the current waypoint
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
        GameMap.Waypoint[] waypoints = new GameMap.Waypoint[9];

        Inertia inertia = driver.getInertia();

        // Index for movement mapping: [0] upLeft, [1] up, [2] upRight, etc.
        Movement[] movements = Movement.values();

        // Inertia-based waypoint is treated as the center [4]
        double centerX = current.getX() + inertia.getX() * cellSize;
        double centerY = current.getY() + inertia.getY() * cellSize;

        try {
            GameMap.Waypoint centerWaypoint = gameMap.createWaypoint(centerX, centerY);
            if (gameMap.getTrackCanvas().containsWaypoint(centerWaypoint)) {
                waypoints[4] = centerWaypoint;
            }
        } catch (IllegalArgumentException ignored) {
            waypoints[4] = null;
        }

        for (int i = 0; i < movements.length; i++) {
            double newX = current.getX() + (movements[i].getXOffset() + inertia.getX()) * cellSize;
            double newY = current.getY() + (movements[i].getYOffset() + inertia.getY()) * cellSize;

            try {
                GameMap.Waypoint nextWaypoint = gameMap.createWaypoint(newX, newY);
                if (gameMap.getTrackCanvas().containsWaypoint(nextWaypoint)) {
                    waypoints[i] = nextWaypoint;
                }
            } catch (IllegalArgumentException ignored) {
                waypoints[i] = null;
            }
        }

        return waypoints;
    }



}

