package it.unicam.cs.api.components.actors;

import it.unicam.cs.api.components.actors.structs.Inertia;
import it.unicam.cs.gui.map.GameMap;
import javafx.scene.paint.Color;


/**
 * Represents a driver in the game. A driver is a racer that can be controlled by the user or the computer
 * @see Racer
 * @see Player
 * @see Bot
 * @author Younes Rabeh
 * @version 1.3
 */
public abstract class Driver implements Racer {
    /** The driver's name. */
    private String name;
    /** The color of the driver's car. */
    private Color carColor;
    /** The current waypoint/ position of the driver. */
    private GameMap.Waypoint currentWaypoint;
    /** The driver's inertia. */
    private final Inertia inertia;
    /** The maximum length of the driver's name. */
    private final int MAX_DRIVER_NAME_LENGTH = 30;

    Driver(String name, Color carColor){
        this.name = checkName(name);
        this.carColor = checkColor(carColor);
        this.inertia = new Inertia();
    }

    /**
     * Checks if the driver's name is valid.
     * @param name the name to check
     * @return the driver's name if it is valid
     * @throws IllegalArgumentException if the name is null, blank, or too long
     */
    private String checkName(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("[!!!] - Name cannot be null");
        if (name.isBlank()) throw new IllegalArgumentException("[!!] - Name cannot be blank");
        if (name.length() > MAX_DRIVER_NAME_LENGTH) {
            throw new IllegalArgumentException("[!!] - Name is too long");
        }

        return name;
    }

    /**
     * Checks if the car's color is valid.
     * @param color the color to check
     * @return the color if it is valid
     * @throws IllegalArgumentException if the color is null, black, or already used
     */
    private Color checkColor(Color color) {
        if (color == null) throw new IllegalArgumentException("[!!!] - Color cannot be null");
        //FIXME: the color of the track is not allowed
        if (color.equals(Color.BLACK)) throw new IllegalArgumentException("[!!] - Color cannot be black");

        return color;
    }

    /**
     * Returns the driver's name.
     * @return the driver's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the driver's car color.
     * @return the driver's car color
     */
    public Color getCarColor() {
        return carColor;
    }

    /**
     * Gets the current waypoint of the driver.
     * @return the current waypoint of the driver
     */
    public GameMap.Waypoint getPosition() {
        return currentWaypoint;
    }

    /**
     * Gets the driver's inertia.
     * @return the driver's inertia
     */
    public Inertia getInertia() {
        return inertia;
    }

    /**
     * Returns the maximum length of the driver's name.
     * @return the maximum length of the driver's name
     */
    public int getMaxDriverNameLength() {
        return MAX_DRIVER_NAME_LENGTH;
    }

    /**
     * Sets the driver's name.
     * @param name the new name of the driver
     * @throws IllegalArgumentException if the name is null, blank, or too long
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("[!!!] - Name cannot be null");
        if(name.isBlank()) throw new IllegalArgumentException("[!!] - Name cannot be blank");
        if (name.length() > MAX_DRIVER_NAME_LENGTH) {
            throw new IllegalArgumentException("[!!] - Name is too long");
        }
        this.name = checkName(name);
    }

    /**
     * Sets the driver's car color.
     * @param carColor the new color of the driver's car
     * @throws IllegalArgumentException if the color is null, black, or already used
     */
    public void setCarColor(Color carColor) {
        if (carColor == null) throw new IllegalArgumentException("[!!!] - Color cannot be null");
        if (carColor.equals(Color.BLACK)) throw new IllegalArgumentException("[!!] - Color cannot be black");
        this.carColor = checkColor(carColor);
    }

    /**
     * Sets the current waypoint of the driver.
     * @param waypoint the new waypoint of the driver
     */
    public void setPosition(GameMap.Waypoint waypoint) {
        this.currentWaypoint = waypoint;
    }


    @Override
    public String toString() {
        return name + " " + carColor.toString();
    }

    public void move(GameMap.Waypoint waypoint) {
        this.currentWaypoint = waypoint;

    }

}
