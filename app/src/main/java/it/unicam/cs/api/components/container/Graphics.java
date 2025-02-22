package it.unicam.cs.api.components.container;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.rgb;

/**
 * This utility class provides methods to draw shapes on the canvas,
 * by interpreting the commands provided by the parser. The parameters of the
 * target function are stored in an array, thus we can't pass them directly.
 *
 * @author Younes Rabeh
 * @version 1.2
 * @see GraphicsContext
 */
public final class Graphics {
    /** Prevent instantiation of this utility class. */
    private Graphics() {}

    /**
     * Set the stroke color of the graphics context.
     * @param gc the GraphicsContext
     * @param params the parameters of the stroke color
     */
    public static void setStroke(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 3);
        gc.setStroke(rgb(params[0], params[1], params[2]));
    }
    /**
     * Set the stroke color of the graphics context.
     * @param gc the GraphicsContext
     * @param color the stroke color
     */
    public static void setStroke(GraphicsContext gc, Color color) {
        Check.checkNull(gc, color);
        gc.setStroke(color);
    }

    /**
     * Set the line width of the graphics context.
     * @param gc the GraphicsContext
     * @param params the parameters of the line width
     */
    public static void setLineWidth(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 1);
        gc.setLineWidth(params[0]);
    }

    public static void moveTo(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 2);
        gc.moveTo(params[0], params[1]);
    }

    /**
     * Draw a line on the canvas.
     * @param gc the GraphicsContext
     * @param params the parameters of the line
     */
    public static void strokeLine(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 4);
        gc.strokeLine(params[0], params[1], params[2], params[3]);
    }

    /**
     * Begin a new path.
     * @param gc the GraphicsContext
     */
    public static void beginPath(GraphicsContext gc) {
        Check.checkNull(gc);
        gc.beginPath();
    }

    /**
     * Set the fill color of the graphics context.
     * @param gc the GraphicsContext
     * @param params the parameters of the fill color
     */
    public static void setFill(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 3);
        gc.setFill(rgb(params[0], params[1], params[2]));
    }

    /**
     * Draw a fill rectangle on the canvas.
     * @param gc the GraphicsContext
     * @param params the parameters of the rectangle
     */
    public static void fillRect(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 4);
        gc.fillRect(params[0], params[1], params[2], params[3]);
    }

    /**
     * Draw a stroke rectangle (borders only) on the canvas.
     * @param gc the GraphicsContext
     * @param params the parameters of the rectangle
     */
    public static void strokeRect(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 4);
        gc.strokeRect(params[0], params[1], params[2], params[3]);
    }

    /**
     * Draw a quadratic curve on the canvas.
     * @param gc the GraphicsContext
     * @param params the parameters of the curve
     */
    public static void quadraticCurveTo(GraphicsContext gc, int[] params) {
        Check.checkNull(gc);
        Check.checkParams(params, 4);
        gc.quadraticCurveTo(params[0], params[1], params[2], params[3]);
    }

    /**
     * Draw a point on the canvas.A point is represented by a small oval.
     * @param gc the GraphicsContext
     * @param params the parameters of the point
     */
    public static void strokePoint(GraphicsContext gc, int[] params){
        Check.checkNull(gc);
        Check.checkParams(params, 2);
        int size = 8;
        int offset = size / 2;

        gc.fillOval(params[0] - offset, params[1] - offset, size, size);
    }

}
