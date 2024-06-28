package it.unicam.cs.api.actors;


import javafx.scene.paint.Color;

/**
 * Represents a bot in the game. A bot is a driver that is controlled by the computer
 * @author Younes Rabeh
 */
public class Bot extends Driver {

    Bot(String name, Color carColor) {
        super(name, carColor);
    }
}
