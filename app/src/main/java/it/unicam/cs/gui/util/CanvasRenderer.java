package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.engine.util.Check;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;


import static it.unicam.cs.engine.util.Check.checkNumbers;

/**
 * A utility class for rendering on canvases.
 * @author  Younes Rabeh
 * @version 1.3
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

    public static void renderStartingLine(TrackCanvas trackCanvas, Waypoint waypoint, int width){
        final int strokeDistance = trackCanvas.getTrackWidth() / 2;
        final int strokeThickness = width / 2;
        final int errorMargin = 2;
        int[] coords = new int[4];

        int X1 = (int) waypoint.getX() - strokeDistance;
        int X2 = (int) waypoint.getX() + strokeDistance;


        int Y1 = (int) waypoint.getY() - strokeDistance;
        int Y2 = (int) waypoint.getY() + strokeDistance;

        if (CanvasTools.getPixelColor((int) waypoint.getX(), (int) waypoint.getY(), trackCanvas.getCanvasSnapshot())
                .equals(trackCanvas.getColor())) {
            System.out.println("X = " + waypoint.getX() + " Y= " + waypoint.getY());
            if (!CanvasTools.getPixelColor(X1 - errorMargin, (int) waypoint.getY(), trackCanvas.getCanvasSnapshot())
                    .equals(trackCanvas.getColor())) {
                System.out.println("X1 = " + X1 + " Y = " + waypoint.getY() +
                        " " + CanvasTools.colorToRGBString(CanvasTools.getPixelColor(X1, (int) waypoint.getY(), trackCanvas.getCanvasSnapshot())));
                coords[0] = X1 + strokeThickness;
                coords[1] = (int) waypoint.getY();
            } if (!CanvasTools.getPixelColor(X2 + errorMargin, (int) waypoint.getY(), trackCanvas.getCanvasSnapshot())
                    .equals(trackCanvas.getColor())) {
                System.out.println("X2 = " + X2 + " Y = " + waypoint.getY() +
                        " " + CanvasTools.colorToRGBString(CanvasTools.getPixelColor(X2, (int) waypoint.getY(), trackCanvas.getCanvasSnapshot())));
                coords[2] = X2 - strokeThickness;
                coords[3] = (int) waypoint.getY();
            }

            if (!CanvasTools.getPixelColor((int) waypoint.getX(), Y1 - errorMargin, trackCanvas.getCanvasSnapshot())
                    .equals(trackCanvas.getColor())) {
                System.out.println("X = " + waypoint.getX() + " Y1 = " + Y1 +
                        " " + CanvasTools.colorToRGBString(CanvasTools.getPixelColor((int) waypoint.getX(), Y1, trackCanvas.getCanvasSnapshot())));
                coords[0] = (int) waypoint.getX();
                coords[1] = Y1  + strokeThickness;
            } if (!CanvasTools.getPixelColor((int) waypoint.getX(), Y2 + errorMargin, trackCanvas.getCanvasSnapshot())
                    .equals(trackCanvas.getColor())) {
                System.out.println("X = " + waypoint.getX() + " Y2 = " + Y2 +
                        " " + CanvasTools.colorToRGBString(CanvasTools.getPixelColor((int) waypoint.getX(), Y2, trackCanvas.getCanvasSnapshot())));
                coords[2] = (int) waypoint.getX();
                coords[3] = Y2 - strokeThickness;
            }

            if (coords[0] == 0 || coords[1] == 0 || coords[2] == 0 || coords[3] == 0) {
                System.out.println("No coords found");
                return;
            }


            Graphics.strokeLine(trackCanvas.getGraphicsContext2D(), coords);


        }








    }



}
