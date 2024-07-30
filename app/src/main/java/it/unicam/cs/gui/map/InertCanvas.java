package it.unicam.cs.gui.map;

import it.unicam.cs.api.components.container.Check;
import javafx.scene.canvas.Canvas;

/**
 * {@code InertCanvas} is a canvas that is used to display inert objects on the map.
 * Inert canvases are drawn under the grid and on top of the track canvas.
 * @author Younes Rabeh
 * @version 1.8
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
    InertCanvas(int width, int height, int layer) {
        super(width, height);
        Check.checkNumbers(layer);
        this.layer = layer;
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
    void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Compare this canvas to another canvas based on the layer. in descending order.
     * @param canvas the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(InertCanvas canvas) {
        return Integer.compare(canvas.getLayer(), this.getLayer());
    }
}
