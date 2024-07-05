package it.unicam.cs.gui.map;

import javafx.scene.canvas.Canvas;

/**
 * {@code InertCanvas} is a canvas that is used to display inert objects on the map.
 * Inert canvases are drawn under the grid and on top of the track canvas.
 * @author Younes Rabeh
 * @version 1.0
 * @see Canvas
 */
public class InertCanvas extends Canvas implements Comparable<InertCanvas> {
    /** The layer of the canvas (the z-depth) */
    private int layer;

    /**
     * Create a new inert canvas with the given width and height.
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    InertCanvas(int width, int height) {
        super(width, height);
    }

    /**
     * Get the layer of the canvas.
     * @return the layer of the canvas
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Set the layer of the canvas.
     * @param layer the layer of the canvas
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Compare this inert canvas to another inert canvas based on their layers.
     * @param canvas the canvas to compare to
     * @return a negative integer, zero, or a positive integer as this canvas is less than, equal to, or greater than the specified canvas.
     */
    @Override
    public int compareTo(InertCanvas canvas) {
        // descending order
        return Integer.compare(canvas.getLayer(), this.getLayer());
    }
}
