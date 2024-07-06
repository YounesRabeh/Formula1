package it.unicam.cs.api.components.container;

import javafx.scene.paint.Color;

/**
 * This interface provides the characteristics of the map.
 * better keep the numerical values even.
 */
public interface Characteristics {
    /** The color of the track */
    Color TRACK_COLOR = Color.BLACK;
    /** The color of the grid */
    Color GRID_COLOR = Color.GRAY;
    /** The width of the start line */
    int START_LINE_WIDTH = 6;
    /** The width of the track */
    int DEFAULT_TRACK_WIDTH = 50;
}
