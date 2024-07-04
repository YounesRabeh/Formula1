package it.unicam.cs.gui.map;

import it.unicam.cs.engine.util.Check;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TrackCanvas extends Canvas {
    /** The snapshot of the canvas */
    private WritableImage snapshot;
    /** The color of the canvas */
    private final Color color;

    /**
     * Create a new TrackCanvas with the given width, height, and drawing parser.
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    public TrackCanvas(double width, double height) {
        super(width, height);
        Check.checkNumbers(width, height);
        this.color = Color.BLACK;
    }

    /**
     * Get the image of the canvas
     * @return the image of the canvas
     */
    public WritableImage getCanvasSnapshot() {
        return snapshot;
    }

    /**
     * Set the snapshot of the canvas, use it after drawing on the canvas.
     * <strong>you can only set the snapshot once</strong>. If you try to set it again, an IllegalStateException will be thrown.
     *
     * @param snapshot the snapshot of the canvas
     * @throws IllegalStateException if the snapshot has already been set
     */
    public void setSnapshot(WritableImage snapshot) throws IllegalStateException {
        if (this.snapshot == null){
            this.snapshot = snapshot;
        } else {
            throw new IllegalStateException("[!!]- The snapshot has already been set");
        }
    }

    /**
     * Get the color of the canvas
     * @return the color of the canvas
     */
    public Color getColor() {
        return color;
    }
}
