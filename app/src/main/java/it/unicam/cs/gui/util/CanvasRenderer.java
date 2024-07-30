package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Characteristics;
import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;


import static it.unicam.cs.api.components.container.Check.checkNumbers;

/**
 * A utility class for rendering on canvases.
 * @author  Younes Rabeh
 * @version 1.7
 */
public final class CanvasRenderer {
    /** Prevent instantiation of this utility class. */
    private CanvasRenderer() {}

    /**
     * Draws a squared grid on the canvas. The grid lines are drawn in the specified color,
     * and the size of each cell is adjusted to fit the canvas dimensions.
     * @param gridCanvas the canvas on which to draw the grid
     */
    public static void renderGrid(GridCanvas gridCanvas) {
        Check.checkNull(gridCanvas);
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
     * @param width the width of the outline
     * @throws IllegalArgumentException if the width of the outline is greater than the cell size
     */
    public static void renderGridOutline(GridCanvas gridCanvas, int width) {
        Check.checkNull(gridCanvas);
        checkNumbers(width);
        if (width > gridCanvas.getCellSize()) {
            throw new IllegalArgumentException("The width of the outline cannot be greater than the cell size.");
        }
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        Paint paint = gc.getStroke();

        gc.setStroke(gridCanvas.getColor());
        gc.setLineWidth(width);
        gc.strokeRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        gc.setLineWidth(1); // reset the line width
        gc.setStroke(paint); // reset the stroke color
    }


    /**
     * Renders a line marker on the track canvas.
     * @param trackCanvas the canvas on which to render the line marker
     * @param lineCoords the coordinates of the line
     */
    public static void renderTrackLineMarker(TrackCanvas trackCanvas, int[] lineCoords){
        GraphicsContext gc = trackCanvas.getGraphicsContext2D();
        final double lineWidth = gc.getLineWidth();
        final Paint gcColor = gc.getStroke();
        gc.setLineWidth(Characteristics.TRACK_MARKER_LINE_WIDTH);
        gc.setStroke(Characteristics.TRACK_LINE_MARKER_COLOR);

        Graphics.strokeLine(gc, lineCoords);
        gc.setLineWidth(lineWidth);
        gc.setStroke(gcColor);
    }

    /**
     * Renders a line marker on the track canvas.
     * @param trackCanvas the canvas on which to render the line marker
     * @param line the line to be rendered
     */
    public static void renderTrackLineMarker(TrackCanvas trackCanvas, Line line){
        renderTrackLineMarker(
                trackCanvas,
                new int[]{
                        (int) line.getStartX(),
                        (int) line.getStartY(),
                        (int) line.getEndX(),
                        (int) line.getEndY()
                }
        );
    }

}
