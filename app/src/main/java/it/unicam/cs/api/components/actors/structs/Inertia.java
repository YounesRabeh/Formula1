package it.unicam.cs.api.components.actors.structs;

/**
 * Represents the inertia of an object.
 * The inertia is used to calculate the movement of an object.
 * The inertia is a vector that represents the speed and direction
 * of the object.
 * @see Movement
 * @see it.unicam.cs.api.components.actors.Driver
 * @author Younes Rabeh
 * @version 1.0
 */
public class Inertia {
    private int x; // Inertia on the X-axis
    private int y; // Inertia on the Y-axis

    public Inertia() {
        this(0, 0);
    }

    public Inertia(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the inertia on the X-axis.
     * @return the inertia on the X-axis
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the inertia on the Y-axis.
     * @return the inertia on the Y-axis
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the inertia values.
     * @param x the inertia on the X-axis
     * @param y the inertia on the Y-axis
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Resets the inertia to zero.
     */
    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Adds another inertia to this one.
     * @param other the other inertia
     */
    public void add(Inertia other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Scales the inertia by a factor.
     * @param factor the scaling factor
     */
    public void scale(int factor) {
        this.x *= factor;
        this.y *= factor;
    }

    @Override
    public String toString() {
        return String.format("Inertia[x=%d, y=%d]", x, y);
    }
}
