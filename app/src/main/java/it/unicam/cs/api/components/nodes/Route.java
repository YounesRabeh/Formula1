package it.unicam.cs.api.components.nodes;


import it.unicam.cs.gui.map.GameMap;

import java.util.LinkedList;

/**
 * A route is a sequence of waypoints, they are not directly connected to each other.
 * The route is used to find the shortest path between at least two waypoints.
 * @see GameMap.Waypoint
 * @author Younes Rabeh
 * @version 1.0
 */
public class Route extends LinkedList<GameMap.Waypoint> {


}
