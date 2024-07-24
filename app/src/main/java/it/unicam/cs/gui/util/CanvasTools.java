package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Check;

import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * This utility class provides methods to interact with the canvas.
 *
 * @author Younes Rabeh
 * @version 1.0
 */
public final class CanvasTools {
    /** Prevent instantiation of this utility class. */
    private CanvasTools() {}

    /**
     * Gets the RGB color of a specific pixel on the canvas.
     *
     * @param image the image from which to get the pixel color
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the Color of the specified pixel
     */
    public static Color getPixelColor(int x, int y,  WritableImage image) {
        Check.checkNull(image);
        Check.checkNumbers(x, y);

        return image.getPixelReader().getColor(x, y);
    }

    /**
     * Gets the snapshot of the canvas.
     *
     * @param canvas the canvas to take a snapshot of
     * @return the snapshot of the canvas
     */
    public static WritableImage createCanvasSnapshot(Canvas canvas) {
        Check.checkNull(canvas);
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        WritableImage image = new WritableImage(width, height);
        canvas.snapshot(null, image);
        return image;
    }

    /**
     * Converts the Color to an RGB string.
     *
     * @param color the Color object
     * @return a string representation of the RGB values
     */
    public static String colorToRGBString(Color color) {
        Check.checkNull(color);
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("RGB(%d, %d, %d)", r, g, b);
    }

    /**
     * Checks if a specific pixel on the canvas has the specified color.
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @param image the image to check
     * @param color the color to check for
     * @return true if the pixel has the specified color, false otherwise
     */
    public static boolean isTrackPixel(int x, int y, WritableImage image, Color color) {
        return getPixelColor(x, y, image).equals(color);
    }

    /**
     * Checks if a specific pixel on the canvas has the specified color.
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @param trackCanvas the canvas to check
     * @return true if the pixel has the specified color, false otherwise
     */
    public static boolean isTrackPixel(int x, int y, TrackCanvas trackCanvas) {
        return isTrackPixel(x, y, trackCanvas.getCanvasSnapshot(), trackCanvas.getColor());
    }

}
