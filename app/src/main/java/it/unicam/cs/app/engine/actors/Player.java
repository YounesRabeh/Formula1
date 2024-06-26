package it.unicam.cs.app.engine.actors;

import javafx.scene.paint.Color;


/**
 * Represents a player in the game. A player is a driver that can be controlled by the user
 * @author Younes Rabeh
 */
public class Player extends Driver implements Playable {


    Player(String name, Color carColor) {
        super(name, carColor);
    }
}
