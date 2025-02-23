package it.unicam.cs.api.components.actors;


import it.unicam.cs.gui.map.GameMap;

/**
 * Represents the basic functionality of a racer in the game
 * @author Younes Rabeh
 * @version 1.0
 */
interface Racer {
    /**
     * Moves the racer to the specified waypoint
     * @param waypoint the waypoint to move to
     */
    void move(GameMap.Waypoint waypoint);
}
