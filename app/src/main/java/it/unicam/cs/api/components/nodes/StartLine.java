package it.unicam.cs.api.components.nodes;

import it.unicam.cs.gui.map.GameMap;

import java.util.Collection;

/**
 * Represents the start line in the game, which consists of a collection of waypoints,
 * these waypoints are the drivers' starting positions.
 * @author Younes Rabeh
 * @version 1.0
 */
public class StartLine extends TrackMarker {
    public StartLine(Collection<GameMap.Waypoint> waypoints) {
        super(waypoints);
    }
}
