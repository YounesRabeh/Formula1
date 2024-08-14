package it.unicam.cs.api.components.nodes;

import it.unicam.cs.gui.map.GameMap;

import java.util.Collection;

/**
 * Represents the finish line in the game, which consists of a collection of waypoints.
 * @see GameMap.Waypoint
 * @author Younes Rabeh
 * @version 1.0
 * */
public class FinishLine {
    /** The waypoints of the finish line */
    Collection<GameMap.Waypoint> waypoints;

    /**
     * Construct a FinishLine with the specified waypoints.
     * @param waypoints the waypoints that make up the finish line
     */
    public FinishLine(Collection<GameMap.Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Return the waypoints of the finish line.
     * @return the waypoints of the finish line
     */
    public Collection<GameMap.Waypoint> getWaypoints() {
        return waypoints;
    }

    /**
     * Set the waypoints of the finish line.
     * @param waypoints the new waypoints of the finish line
     */
    public void setWaypoints(Collection<GameMap.Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Adds a waypoint to the finish line.
     * @param waypoint the waypoint to add
     */
    public void addWaypoint(GameMap.Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    /**
     * Checks if the finish line contains the specified waypoint.
     * @param waypoint the waypoint to check
     * @return true if the finish line contains the waypoint, false otherwise
     */
    public boolean contains(GameMap.Waypoint waypoint) {
        return waypoints.contains(waypoint);
    }
}