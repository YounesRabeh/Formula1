package it.unicam.cs.gui.map;

import it.unicam.cs.engine.util.Check;

import java.util.TreeSet;

public class GameMap implements Characteristics {
    /** The grid canvas */
    GridCanvas gridCanvas;
    /** The track canvas */
    TrackCanvas trackCanvas;
    /** The inert canvases */
    TreeSet<InertCanvas> inertCanvas = new TreeSet<>();

    private int width;
    private int height;



    //TODO: add the ability to the parser so that it switch gc's and draw based on canvas
    /**
     * Create a new map with the given cell size and number of cells in the x and y directions.
     * @param cellSize the size of each cell
     * @param cellNumber_X the number of cells in the x direction
     * @param cellNumber_Y the number of cells in the y direction
     */
    public GameMap(int cellSize, int cellNumber_X, int cellNumber_Y) {
        Check.checkNumbers(cellSize, cellNumber_X, cellNumber_Y);
        this.width = cellSize * cellNumber_X;
        this.height = cellSize * cellNumber_Y;
        inertCanvas.removeFirst();

        gridCanvas = new GridCanvas(cellSize, cellNumber_X, cellNumber_Y, GRID_COLOR);
        trackCanvas = new TrackCanvas(width, height, TRACK_COLOR);

    } //TODO: add the square map && the color specific map

    /**
     * Get the grid canvas
     * @return the grid canvas
     */
    public GridCanvas getGridCanvas() {
        return gridCanvas;
    }

    /**
     * Get the track canvas
     * @return the track canvas
     */
    public TrackCanvas getTrackCanvas() {
        return trackCanvas;
    }

    /**
     * Get the grid canvas
     * @return the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the map
     * @return the height of the map
     */
    public int getHeight() {
        return height;
    }


}
