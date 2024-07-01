package it.unicam.cs.gui.tools;

import it.unicam.cs.api.components.map.GridCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public final class CanvasRenderer {
    private CanvasRenderer() {}

    /**
     * Draws a squared grid on the canvas. The grid lines are drawn in the specified color,
     * and the size of each cell is adjusted to fit the canvas dimensions.
     * @param gridCanvas the canvas on which to draw the grid
     *
     * @return the adjusted cell size
     */
    public static double RenderGrid(GridCanvas gridCanvas) {
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

        return cellSize;
    }

    /**
     * Draws an outline around the canvas.
     *
     * @param gridCanvas the canvas on which to draw the outline
     */
    public static void RenderOutline(GridCanvas gridCanvas, Color color) {
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        gc.setLineWidth(1); // reset the line width
    }

}
