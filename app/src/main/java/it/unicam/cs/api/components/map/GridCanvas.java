package it.unicam.cs.api.components.map;


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
 * @version 1.0
 */
public class GridCanvas extends Canvas {
    /** The size of the grid. */
    private final int cellSize;
    /** The color of the grid. */
    private Color color;
    /** The number of cells in the horizontal direction. */
    private int cellNumber_X;
    /** The number of cells in the vertical direction. */
    private int cellNumber_Y;


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
    public GridCanvas(int cellSize, int cellNumber_X, int cellNumber_Y, Color color) {
        super(cellNumber_X * cellSize , cellNumber_Y * cellSize);
        this.cellNumber_X = cellNumber_X * cellSize;
        this.cellNumber_Y = cellNumber_Y * cellSize;
        this.cellSize = cellSize;
        this.color = color;
    }

    /**
     * Create a new GridCanvas with the specified cell size,
     * with a specified number of cells in the horizontal and vertical directions.
     * The color of the grid is set to gray.
     * <b>The Canvas size will be </b> {@code [cellSize * .._X} <b>*</b> {@code cellSize * .._Y]}
     *
     * @param cellSize the size of the cell
     * @param cellNumber_X the number of cells in the horizontal direction
     * @param cellNumber_Y the number of cells in the vertical direction
     */
    public GridCanvas(int cellSize, int cellNumber_X, int cellNumber_Y) {
       this(cellSize, cellNumber_X, cellNumber_Y, Color.GRAY);
    }

    /**
     * Create a new GridCanvas with the specified cell size, width, height, and color.
     * <b>The cell size is adjusted to fit the canvas dimensions</b> by finding the nearest best-fit (descending).
     * @param cellSize the size of the cell
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @param color the color of the grid
     */
    public GridCanvas(int cellSize, double width, double height, Color color) {
        super(width, height);
        this.cellSize = adjustedCellSize(cellSize);
        this.color = color;
    }

    /**
     * Adjust the cell size to fit the canvas dimensions.
     * @param cellSize the size of the cell
     * @return the adjusted cell size
     */
    private int adjustedCellSize(int cellSize){
        double adjustedCellSize = cellSize;  // Adjust cell size to fit canvas dimensions
        while (this.getWidth() % adjustedCellSize != 0 || this.getHeight() % adjustedCellSize != 0) {
            adjustedCellSize--;
        }

        return (int) adjustedCellSize;
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

}
