package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.container.Characteristics;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import static it.unicam.cs.api.components.container.Check.checkNumbers;

/**
 * A utility class for rendering on canvases.
 * @author  Younes Rabeh
 * @version 1.5
 */
public final class CanvasRenderer {
    /** Prevent instantiation of this utility class. */
    private CanvasRenderer() {}

    /**
     * Draws a squared grid on the canvas. The grid lines are drawn in the specified color,
     * and the size of each cell is adjusted to fit the canvas dimensions.
     * @param gridCanvas the canvas on which to draw the grid
     */
    public static void RenderGrid(GridCanvas gridCanvas) {
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
    public static void RenderGridOutline(GridCanvas gridCanvas, int width) {
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
     * Renders the starting line on the track canvas.
     * @param trackCanvas the canvas on which to render the starting line
     * @param waypoint the waypoint of the starting line
     * @param width the width of the starting line
     * @throws IllegalArgumentException if the starting line is not valid
     */
    public static void renderStartingLine(TrackCanvas trackCanvas, Waypoint waypoint, int width) {
        if (trackCanvas.contains(waypoint)) {
            final int strokeDistance = trackCanvas.getTrackWidth() / 2;
            final int X = (int) waypoint.getX();
            final int Y = (int) waypoint.getY();
            final int X1 = X - strokeDistance, X2 = X + strokeDistance;
            final int Y1 = Y - strokeDistance, Y2 = Y + strokeDistance;

            GraphicsContext gc = trackCanvas.getGraphicsContext2D();
            final double lineWidth = gc.getLineWidth();
            gc.setLineWidth(width);

            if (verifyStartLineV(trackCanvas, X1, X2, Y)) return;
            if (verifyStartLineH(trackCanvas, Y1, Y2, X)) return;

            gc.setLineWidth(lineWidth); // reset the line width
        }

        throw new IllegalArgumentException("[!!]- The starting line is not valid.");
    }


    /**
     * Verifies the legitimacy of a Horizontal start line on the track canvas.
     * @param trackCanvas the canvas on which to verify the start line
     * @param var1 the first variable
     * @param var2 the second variable
     * @param still the still variable
     * @return true if the start line is legit, false otherwise
     */
    private static boolean verifyStartLineH(TrackCanvas trackCanvas, int var1, int var2, int still) {
        final int[] coords = new int[4];
        final int strokeThickness = Characteristics.DEFAULT_STARTING_LINE_WIDTH / 2;
        if (!CanvasTools.isTrackPixel(still, var1 - strokeThickness, trackCanvas)) {
            coords[0] = still;
            coords[1] = var1 + strokeThickness;
            if (!CanvasTools.isTrackPixel(still, var2 + strokeThickness, trackCanvas)) {
                coords[2] = still;
                coords[3] =  var2 - strokeThickness;
                strokeExistingStartLine(trackCanvas.getGraphicsContext2D(), coords);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies the legitimacy of a Vertical start line on the track canvas.
     * @param trackCanvas the canvas on which to verify the start line
     * @param var1 the first variable
     * @param var2 the second variable
     * @param still the still variable
     * @return true if the start line is legit, false otherwise
     */
    private static boolean verifyStartLineV(TrackCanvas trackCanvas, int var1, int var2, int still) {
        final int[] coords = new int[4];
        final int strokeThickness = Characteristics.DEFAULT_STARTING_LINE_WIDTH / 2;
        if (!CanvasTools.isTrackPixel(var1 - strokeThickness, still, trackCanvas)) {
            coords[0] = var1 + strokeThickness;
            coords[1] = still;
            if (!CanvasTools.isTrackPixel(var2 + strokeThickness, still, trackCanvas)) {
                coords[2] = var2 - strokeThickness;
                coords[3] = still;
                strokeExistingStartLine(trackCanvas.getGraphicsContext2D(), coords);
                return true;
            }
        }
        return false;
    }

    /**
     * Strokes the existing start line on the track canvas.
     * @param gc the GraphicsContext
     * @param coords the coordinates of the start line
     */
    private static void strokeExistingStartLine(GraphicsContext gc, int[] coords) {
        Graphics.setStroke(gc, Color.WHITE);
        Graphics.setLineWidth(gc, new int[]{Characteristics.DEFAULT_STARTING_LINE_WIDTH});
        Graphics.strokeLine(gc, coords);
    }

}
