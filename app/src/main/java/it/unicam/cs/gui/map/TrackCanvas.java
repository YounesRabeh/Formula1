package it.unicam.cs.gui.map;

import it.unicam.cs.api.parser.DrawingParser;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TrackCanvas extends Canvas {
    /** The snapshot of the canvas */
    private final WritableImage image;
    /** The color of the canvas */
    private final Color color;

    /**
     * Create a new TrackCanvas with the given width, height, and drawing parser.
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @param parser the drawing parser to be used
     */
    public TrackCanvas(double width, double height) {
        super(width, height);
        this.color = Color.BLACK;
        image = CanvasTools.getCanvasSnapshot(this);
    }

    /**
     * Get the image of the canvas
     * @return the image of the canvas
     */
    public WritableImage getImage() {
        return image;
    }

    /**
     * Get the color of the canvas
     * @return the color of the canvas
     */
    public Color getColor() {
        return color;
    }
}
