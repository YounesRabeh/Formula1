package it.unicam.cs.gui.map;


import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * {@code GridCanvas} is a canvas that displays a grid with a specified cell size,
 * number of cells in the horizontal and vertical directions, and color.
 * This canvas should only be used for displaying a grid.
 * <b> <span>&#9888;</span> It is not recommended to draw on this canvas</b>
 *
 *
 * @see Canvas
 * @author Younes Rabeh
 * @version 1.3
 */
public class GridCanvas extends Canvas {
    /** The color of the grid */
    public static final Color GRID_COLOR = Color.GRAY;
    /** The size of the grid. */
    private final int cellSize;
    /** The number of cells in the horizontal direction. */
    private final int cellNumber_X;
    /** The number of cells in the vertical direction. */
    private final int cellNumber_Y;
    /** The color of the grid. */
    private final Color color;

    /**
     * Create a new GridCanvas with the specified cell size,
     * with a specified number of cells in the horizontal and vertical directions,
     * and a custom color
     * <b>The Canvas size will be </b> {@code [cellSize * .._X} <b>*</b> {@code cellSize * .._Y]}
     *
     * @param cellSize the size of the cell
     * @param cellNumber_X the number of cells in the horizontal direction
     * @param cellNumber_Y the number of cells in the vertical direction
     * @param color the color of the grid
     */
    GridCanvas(int cellSize, int cellNumber_X, int cellNumber_Y, Color color) {
        super(cellNumber_X * cellSize , cellNumber_Y * cellSize);

        this.cellSize = cellSize;
        this.cellNumber_X = cellNumber_X * cellSize;
        this.cellNumber_Y = cellNumber_Y * cellSize;
        this.color = color;
    }


    /**
     * Get the number of cells in the horizontal direction.
     * @return the number of cells in the horizontal direction
     */
    public int getCellNumber_X() {
        return cellNumber_X;
    }

    /**
     * Get the number of cells in the vertical direction.
     * @return the number of cells in the vertical direction
     */
    public int getCellNumber_Y() {
        return cellNumber_Y;
    }

    /**
     * Get the cell size of the grid.
     * @return the size of the grid
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Get the color of the grid.
     * @return the color of the grid
     */
    public Color getColor() {
        return color;
    }

    public int getLayer() {
        return Integer.MAX_VALUE;
    }

}
