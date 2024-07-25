package it.unicam.cs.api.components.container;

import javafx.scene.paint.Color;

/**
 * This interface provides the characteristics of the map.
 * better keep the numerical values even.
 * @author Younes Rabeh
 */
public interface Characteristics {
    /** The color of the track */
    Color TRACK_COLOR = Color.YELLOW;
    /** The color of the grid */
    Color GRID_COLOR = Color.GRAY;

    Color TRACK_LINE_MARKER_COLOR = Color.WHITE;
    /** The width of the track markers line */
    int TRACK_MARKER_LINE_WIDTH = 6;
    /** The width of the track */
    int DEFAULT_TRACK_WIDTH = 50;
}
