package it.unicam.cs.api.components.nodes;

import it.unicam.cs.gui.map.GameMap;

import java.util.Collection;

/** 
 * Represents a track marker in the game, which consists of a collection of waypoints
 * serving a specific purpose.
 * @author Younes Rabeh
 * @version 1.0
 *  
 */
public abstract class TrackMarker {
    /** The waypoints of the track marker */
    private Collection<GameMap.Waypoint> waypoints;

    /**
     * Construct a track marker with the specified waypoints.
     * @param waypoints the waypoints that make up the track marker
     */
    public TrackMarker(Collection<GameMap.Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Return the waypoints of the track marker.
     * @return the waypoints of the track marker
     */
    public Collection<GameMap.Waypoint> getWaypoints() {
        return waypoints;
    }

    /**
     * Set the waypoints of the track marker.
     * @param waypoints the new waypoints of the track marker
     */
    public void setWaypoints(Collection<GameMap.Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Adds a waypoint to the track marker.
     * @param waypoint the waypoint to add
     */
    public void addWaypoint(GameMap.Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    /**
     * Checks if the track marker contains the specified waypoint.
     * @param waypoint the waypoint to check
     * @return true if the track marker contains the waypoint, false otherwise
     */
    public boolean contains(GameMap.Waypoint waypoint) {
        return waypoints.contains(waypoint);
    }
}
