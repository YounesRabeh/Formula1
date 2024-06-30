package it.unicam.cs.api.actors;

import javafx.scene.paint.Color;

abstract class Driver implements Racing, F1Rules {
    /** The driver's name. */
    private final String NAME;
    /** The color of the driver's car. */
    private final Color CAR_COLOR;

    // TODO: add difficulty

    // NOTE: every driver has a car with a unique color
    Driver(String name, Color carColor){
        this.NAME = checkName(name);
        this.CAR_COLOR = checkColor(carColor);

    }

    /**
     * Checks if the driver's name is valid.
     * @param name the name to check
     * @return the driver's name if it is valid
     * @throws IllegalArgumentException if the name is null, blank, or too long
     * @see F1Rules#MAX_DRIVER_NAME_LENGTH
     */
    private String checkName(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("[!!!] - Name cannot be null");
        if (name.isBlank()) throw new IllegalArgumentException("[!!!] - Name cannot be blank");
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
        if (color.equals(Color.BLACK)) throw new IllegalArgumentException("[!!!] - Color cannot be black");
        //TODO: make sure the color is not already used by another driver

        return color;
    }

}
