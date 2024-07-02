package it.unicam.cs.gui.util;

import it.unicam.cs.gui.map.GridCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public final class CanvasRenderer {
    /** Prevent instantiation of this utility class. */
    private CanvasRenderer() {}

    /**
     * Draws a squared grid on the canvas. The grid lines are drawn in the specified color,
     * and the size of each cell is adjusted to fit the canvas dimensions.
     * @param gridCanvas the canvas on which to draw the grid
     */
    public static void RenderGrid(GridCanvas gridCanvas) {
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        final double width = gridCanvas.getWidth();
        final double height = gridCanvas.getHeight();
        final double cellSize = gridCanvas.getCellSize();

        gc.setStroke(gridCanvas.getColor()); // Set the stroke color
        for (double y = 0; y <= height; y += cellSize) { // Draw horizontal lines
            gc.strokeLine(0, y, width, y);
        }
        for (double x = 0; x <= width; x += cellSize) { // Draw vertical lines
            gc.strokeLine(x, 0, x, height);
        }
    }

    /**
     * Draws an outline around the canvas.
     *
     *
     * @param gridCanvas the canvas on which to draw the outline
     * @param color the color of the outline
     */
    public static void RenderGridOutline(GridCanvas gridCanvas, Color color) {
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        Paint paint = gc.getStroke();
        gc.setStroke(color);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        gc.setLineWidth(1); // reset the line width
        gc.setStroke(paint); // reset the stroke color
    }



}
