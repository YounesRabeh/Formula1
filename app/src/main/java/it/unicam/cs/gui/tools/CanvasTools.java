package it.unicam.cs.gui.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public final class CanvasTools {
    private CanvasTools() {}
    /**
     * Gets the RGB color of a specific pixel on the canvas.
     *
     * @param canvas the canvas from which to get the pixel color
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the Color of the specified pixel
     */
    public static Color getPixelColor(Canvas canvas, int x, int y) {
        // Create a WritableImage to draw onto the canvas
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        javafx.scene.image.WritableImage image =
                new javafx.scene.image.WritableImage(width, height);
        canvas.snapshot(null, image);

        // Get the PixelReader from the WritableImage
        PixelReader pixelReader = image.getPixelReader();

        // Get the color of the specified pixel
        return pixelReader.getColor(x, y);
    }

    /**
     * Converts the Color to an RGB string.
     *
     * @param color the Color object
     * @return a string representation of the RGB values
     */
    public static String colorToRGBString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("RGB(%d, %d, %d)", r, g, b);
    }

    /**
     * Draws an outline around the canvas.
     *
     * @param canvas the canvas on which to draw the outline
     * @param color the color of the outline
     */
    public static void drawOutline(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1); // reset the line width
    }




}
