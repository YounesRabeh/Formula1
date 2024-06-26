package it.unicam.cs.app.gui.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class MapRenderer {
    private MapRenderer() {}

    /**
     * Draws a squared grid on the canvas.
     *
     * @param canvas the canvas on which to draw the grid
     * @param cellSize the size of each cell in the grid
     * @param gridColor the color of the grid lines
     */
    public static double drawGrid(Canvas canvas, double cellSize, Color gridColor) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        final double width = canvas.getWidth();
        final double height = canvas.getHeight();

        double adjustedCellSize = cellSize;  // Adjust cell size to fit canvas dimensions
        while (width % adjustedCellSize != 0 || height % adjustedCellSize != 0) {
            adjustedCellSize--;
        }

        gc.setStroke(gridColor); // Set the stroke color
        for (double y = 0; y <= height; y += adjustedCellSize) { // Draw horizontal lines
            gc.strokeLine(0, y, width, y);
        }
        for (double x = 0; x <= width; x += adjustedCellSize) { // Draw vertical lines
            gc.strokeLine(x, 0, x, height);
        }

        return adjustedCellSize;
    }




}
