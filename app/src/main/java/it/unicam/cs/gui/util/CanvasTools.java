package it.unicam.cs.gui.util;

import it.unicam.cs.engine.util.Check;
import javafx.scene.image.PixelReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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

    public static List<int[]> getBlackPixels(Canvas trackCanvas, int step, WritableImage snapshot) {
        Check.checkNull(trackCanvas, snapshot);
        Check.checkNumbers(step);
        List<int[]> blackPixels = new ArrayList<>();

        for (int x = 0; x < trackCanvas.getWidth(); x += step) {
            for (int y = 0; y < trackCanvas.getHeight(); y += step) {
                Color pixelColor = CanvasTools.getPixelColor(x, y, snapshot);
                if (pixelColor.equals(Color.BLACK)) {
                    blackPixels.add(new int[]{x, y});
                }
            }
        }
        return blackPixels;
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
}
