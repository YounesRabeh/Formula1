package it.unicam.cs.api.components.nodes;

import it.unicam.cs.gui.map.GameMap;

import java.util.Collection;

/**
 * Represents the finish line in the game, which consists of a collection of waypoints.
 * @see GameMap.Waypoint
 * @author Younes Rabeh
 * @version 1.2
 * */
public class FinishLine extends TrackMarker {
    /**
     * Construct a finish line with the specified waypoints.
     * @param waypoints the waypoints that make up the finish line
     */
    public FinishLine(Collection<GameMap.Waypoint> waypoints) {
        super(waypoints);
    }

}