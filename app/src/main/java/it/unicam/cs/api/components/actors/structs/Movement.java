package it.unicam.cs.api.components.actors.structs;

/**
 * Represents the possible movements of an object on the map.
 * The offset must be multiplied by the cell size to get the actual movement.
 * @author Younes Rabeh
 * @version 1.2
 */
public enum Movement {
    UP_RIGHT(1, -1),
    UP(0, -1),
    UP_LEFT(-1, -1),
    RIGHT(1, 0),
    CENTER(0, 0),
    LEFT(-1, 0),
    DOWN_LEFT(-1, 1),
    DOWN(0, 1),
    DOWN_RIGHT(1, 1);

    /** The x offset of the movement */
    private final int xOffset;
    /** The y offset of the movement */
    private final int yOffset;

    Movement(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Get the x offset of the movement.
     * @return the x offset of the movement
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Get the y offset of the movement.
     * @return the y offset of the movement
     */
    public int getYOffset() {
        return yOffset;
    }
}
