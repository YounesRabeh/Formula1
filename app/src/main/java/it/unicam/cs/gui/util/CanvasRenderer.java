package it.unicam.cs.gui.util;

import it.unicam.cs.api.exceptions.NoActionFoundException;
import it.unicam.cs.api.parser.DrawingParser;
import it.unicam.cs.engine.util.Check;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;


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
     * @param color the color of the outline
     */
    public static void RenderGridOutline(GridCanvas gridCanvas, Color color) {
        Check.checkNull(gridCanvas, color);
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        Paint paint = gc.getStroke();

        gc.setStroke(color);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        gc.setLineWidth(1); // reset the line width
        gc.setStroke(paint); // reset the stroke color
    }

    /**
     * Renders the circuit on the canvas.
     *
     * @param trackCanvas the canvas on which to render the circuit
     * @param parser the parser to use for rendering
     * @throws IOException if an I/O error occurs
     * @throws NoActionFoundException if no action is found
     * @throws IllegalStateException if the graphics context is set
     */
    public static void RenderCircuit(
            TrackCanvas trackCanvas,
            DrawingParser parser
    ) throws IOException, NoActionFoundException, IllegalStateException {
        Check.checkNull(trackCanvas, parser);
        GraphicsContext parser_gc = parser.getGraphicsContext();

        parser.setGraphicsContext(trackCanvas.getGraphicsContext2D());
        parser.start();
        trackCanvas.setSnapshot(CanvasTools.createCanvasSnapshot(trackCanvas));
        parser.setGraphicsContext(parser_gc);
    }


}
