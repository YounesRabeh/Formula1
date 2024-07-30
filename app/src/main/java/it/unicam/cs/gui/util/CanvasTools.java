package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.container.Characteristics;
import it.unicam.cs.api.components.container.Check;

import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


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
    public static boolean isPixel(int x, int y, WritableImage image, Color color) {
        return getPixelColor(x, y, image).equals(color);
    }

    /**
     * Checks if a specific point on the canvas is on the track.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param trackCanvas the canvas to check
     * @return true if the point (pixel) has the specified track color, false otherwise
     */
    public static boolean isTrackPoint(int x, int y, TrackCanvas trackCanvas) {
        return isPixel(x, y, trackCanvas.getTrackSnapshot(), trackCanvas.getColor());
    }

    /**
     * Checks if a specific point on the canvas is on the track.
     * @param point2D the point to check
     * @param trackCanvas the canvas to check
     * @return true if the point (pixel) has the specified track color, false otherwise
     */
    public static boolean isTrackPoint(TrackCanvas trackCanvas, Point2D point2D){
        return isTrackPoint((int) point2D.getX(), (int) point2D.getY(), trackCanvas);
    }

    /**
     * Creates a line from a point on the canvas.
     * @param trackCanvas the canvas to create the line on
     * @param point2D the point to create the line from
     * @return the line
     */
    public static Line createLineFromPoint(TrackCanvas trackCanvas, Point2D point2D){
        int[] line = createLineCoordsFromPoint(trackCanvas, point2D);
        return new Line(line[0], line[1], line[2], line[3]);
    }

    /**
     * Creates the coordinates of a line from a point on the canvas.
     * @param trackCanvas the canvas to create the line on
     * @param point2D the point to create the line from
     * @return the coordinates of the line
     */
    public static int[] createLineCoordsFromPoint(TrackCanvas trackCanvas, Point2D point2D){
        if (isTrackPoint(trackCanvas, point2D)){
            final int strokeDistance = trackCanvas.getTrackWidth() / 2;
            final int X = (int) point2D.getX();
            final int Y = (int) point2D.getY();
            final int X1 = X - strokeDistance, X2 = X + strokeDistance;
            final int Y1 = Y - strokeDistance, Y2 = Y + strokeDistance;

            int[] lineCoords = verifyLineV(trackCanvas, X1, X2, Y);
            if (lineCoords == null){
                lineCoords = verifyLineH(trackCanvas, Y1, Y2, X);
                if (lineCoords == null) {
                    throw new IllegalArgumentException("not a valide line");
                }
            } return lineCoords;
        }
        throw new IllegalArgumentException("[!!]- THE POINT IS NOT IN TRACK");
    }

    /**
     * Verifies if the line is horizontal.
     * @param trackCanvas the canvas to check
     * @param var1 the first coordinate
     * @param var2 the second coordinate
     * @param still the still coordinate
     * @return the coordinates of the line if it is horizontal, null otherwise
     */
    private static int[] verifyLineH(TrackCanvas trackCanvas, int var1, int var2, int still) {
        final int[] coords = new int[4];
        final int strokeThickness = Characteristics.TRACK_MARKER_LINE_WIDTH / 2;
        if (!CanvasTools.isTrackPoint(still, var1 - strokeThickness, trackCanvas)) {
            coords[0] = still;
            coords[1] = var1 + strokeThickness;
            if (!CanvasTools.isTrackPoint(still, var2 + strokeThickness, trackCanvas)) {
                coords[2] = still;
                coords[3] =  var2 - strokeThickness;
                return coords;
            }
        }
        return null;
    }

    /**
     * Verifies if the line is vertical.
     * @param trackCanvas the canvas to check
     * @param var1 the first coordinate
     * @param var2 the second coordinate
     * @param still the still coordinate
     * @return the coordinates of the line if it is vertical, null otherwise
     */
    private static int[] verifyLineV(TrackCanvas trackCanvas, int var1, int var2, int still) {
        final int[] coords = new int[4];
        final int strokeThickness = Characteristics.TRACK_MARKER_LINE_WIDTH / 2;
        if (!CanvasTools.isTrackPoint(var1 - strokeThickness, still, trackCanvas)) {
            coords[0] = var1 + strokeThickness;
            coords[1] = still;
            if (!CanvasTools.isTrackPoint(var2 + strokeThickness, still, trackCanvas)) {
                coords[2] = var2 - strokeThickness;
                coords[3] = still;
                return coords;
            }
        }
        return null;
    }

}
