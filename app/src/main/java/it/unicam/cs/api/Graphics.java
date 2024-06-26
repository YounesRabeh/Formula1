package it.unicam.cs.api;




import javafx.scene.canvas.GraphicsContext;

import static javafx.scene.paint.Color.rgb;

/**
 * This utility class provides methods to draw shapes on the canvas,
 * by interpreting the commands provided by the parser. The parameters of the
 * target function are stored in an array, thus we can't pass them directly.
 *
 * @author Younes Rabeh
 * @version 1.0
 * @see GraphicsContext
 */
public final class Graphics {

    private Graphics() {}

    /**
     * Set the stroke color of the graphics context.
     */
    public static void setStroke(GraphicsContext gc, int[] params) {
        gc.setStroke(rgb(params[0], params[1], params[2]));
    }

    /**
     * Set the line width of the graphics context.
     */
    public static void setLineWidth(GraphicsContext gc, int[] params) {
        gc.setLineWidth(params[0]);
    }


    public static void strokeLine(GraphicsContext gc, int[] params) {
        gc.strokeLine(params[0], params[1], params[2], params[3]);
    }

    public static void beginPath(GraphicsContext gc) {
        gc.beginPath();
    }

    public static void setFill(GraphicsContext gc, int[] params) {
        gc.setFill(rgb(params[0], params[1], params[2]));
    }

    public static void fillRect(GraphicsContext gc, int[] params) {
        gc.fillRect(params[0], params[1], params[2], params[3]);
    }

    public static void strokeRect(GraphicsContext gc, int[] params) {
        gc.strokeRect(params[0], params[1], params[2], params[3]);
    }

    public static void quadraticCurveTo(GraphicsContext gc, int[] params) {
        gc.quadraticCurveTo(params[0], params[1], params[2], params[3]);
    }



}
