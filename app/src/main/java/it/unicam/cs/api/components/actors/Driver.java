package it.unicam.cs.api.components.actors;

import javafx.scene.paint.Color;

abstract class Driver implements Racer {
    /** The driver's name. */
    private String name;
    /** The color of the driver's car. */
    private Color carColor;
    /** The maximum length of the driver's name. */
    private static final int MAX_DRIVER_NAME_LENGTH = 30;

    // NOTE: every driver has a car with a unique color
    Driver(String name, Color carColor){
        this.name = checkName(name);
        this.carColor = checkColor(carColor);

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
        //TODO: make sure the color is not already used by another driver

        return color;
    }

}
