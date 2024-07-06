package it.unicam.cs.gui.map;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * {@code TrackCanvas} is a canvas that displays a track.
 * It is used to draw the track on the map.
 * @author Younes Rabeh
 * @version 1.4
 */
public class TrackCanvas extends Canvas {
    /** The snapshot of the canvas */
    private WritableImage snapshot;
    /** The color of the canvas */
    private final Color color;
    /** The width of the track */
    private int trackWidth;

    /**
     * Create a new TrackCanvas with the given width, height, and drawing parser.
     * @param width the width of the track
     * @param height the height of the track
     * @param color the color of the track
     */
    TrackCanvas(double width, double height, Color color) {
        super(width, height);

        this.color = color;
    }

    /**
     * Get the image of the canvas
     * @return the image of the canvas
     * @throws IllegalStateException if the snapshot has not been set
     */
    public WritableImage getCanvasSnapshot() {
        if (snapshot == null) throw new IllegalStateException("[!!]- The snapshot has not been set");
        return snapshot;
    }

    /**
     * Get the track width
     * @return the track width
     */
    public int getTrackWidth() {
        return trackWidth;
    }

    /**
     * Get the color of the canvas
     * @return the color of the canvas
     */
    public Color getColor() {
        return color;
    }


    public int getLayer(){
        return Integer.MIN_VALUE;
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
     * Set the track width, (the width of the track to be drawn on the canvas)
     * @param trackWidth the width of the track
     */
    public void setTrackWidth(int trackWidth) {
        if (trackWidth < 0) throw new IllegalArgumentException("[!!]- The track width must be non-negative");
        this.trackWidth = trackWidth;
        System.out.println("trackWidth = " + trackWidth);
    }

}
