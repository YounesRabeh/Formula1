package it.unicam.cs.api.components.actors;


import javafx.scene.paint.Color;

/**
 * Represents a bot in the game. A bot is a driver that is controlled by the computer
 * @author Younes Rabeh
 */
public class Bot extends Driver {

    public Bot(String name, Color carColor) {
        super(name, carColor);
    }

    public Bot(String name) {
        super(name, Color.RED);
    }
}
