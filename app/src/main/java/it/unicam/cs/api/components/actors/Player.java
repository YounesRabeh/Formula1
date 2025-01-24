package it.unicam.cs.api.components.actors;

import javafx.scene.paint.Color;


/**
 * Represents a player in the game. A player is a driver that can be controlled by the user
 * @author Younes Rabeh
 */
public class Player extends Driver implements Playable {

    public Player(String name, Color carColor) {
        super(name, carColor);
    }

    public Player() {
        super("PLAYER", Color.BLUE);
    }
}

