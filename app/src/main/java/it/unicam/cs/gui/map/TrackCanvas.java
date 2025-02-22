package it.unicam.cs.gui.map;


import it.unicam.cs.api.components.container.Check;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
    private Color color;
    /** The width of the track */
    public static final int DEFAULT_TRACK_WIDTH = 50;
    /** The color of the track markers */
    public static final Color TRACK_LINE_MARKER_COLOR = Color.WHITE;
    /** The color of the track */
    public static final Color TRACK_COLOR = Color.BLACK;
    /** The width of the track markers line */
    public static final int TRACK_MARKER_LINE_WIDTH = 6;
    /** The width of the track */
    private int trackWidth = 50;
    /** The admissible waypoints of the track */
    private final Collection<GameMap.Waypoint> waypoints = new ArrayList<>();
    /** The parsed segments end points of the track plus the start/finish line */
    private final List<Point2D> segmentsEndPoints = new LinkedList<>();
    /** To see if the track is closed (a circular track). Default is false **/
    private boolean isTrackClosed = false;
    /** The start line of the track */
    private Line startLine;
    /** The finish line of the track */
    private Line finishLine;

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
     * Check if the waypoint is contained in the track.
     * @param waypoint the waypoint to check
     * @return true if the waypoint is contained in the track, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean containsWaypoint(GameMap.Waypoint waypoint){
        return waypoints.contains(waypoint);
    }

    /**
     * Get the image of the canvas
     * @return the image of the canvas
     * @throws IllegalStateException if the snapshot has not been set
     */
    public WritableImage getTrackSnapshot() {
        if (snapshot == null) throw new IllegalStateException("THE SNAPSHOT HAS NOT BEEN SET YET");
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

    /**
     * Get the calculated waypoints of the track
     * @return the calculated waypoints of the track
     */
    public Collection<GameMap.Waypoint> getWaypoints(){
        return this.waypoints;
    }

    /**
     * Get the parsed end points of the track's segments
     * @return the parsed end points of the track's segments
     */
    public List<Point2D> getSegmentsEndPoints() {
        return segmentsEndPoints;
    }

    /**
     * Get the state of the track (closed or not)
     * @return true if the track is closed, false otherwise
     */
    public boolean getTrackState() {
        return isTrackClosed;
    }

    /**
     * Get the start line of the track
     * @return the start line of the track
     */
    public Line getStartLine() {
        return startLine;
    }

    /**
     * Get the finish line of the track
     * @return the finish line of the track
     */
    public Line getFinishLine() {
        return finishLine;
    }

    /**
     * Get the layer of the canvas
     * @return the layer of the canvas
     */
    @SuppressWarnings("unused")
    public int getLayer(){
        return Integer.MIN_VALUE;
    }



    /**
     * Add the calculated waypoints to the track
     * @param calculatedWaypoints the calculated waypoints
     */
    public void addWaypoints(Collection<GameMap.Waypoint> calculatedWaypoints){
        Check.checkNull(calculatedWaypoints);
        this.waypoints.addAll(calculatedWaypoints);
    }

    /**
     * Add the segment end point to the track
     * @param segmentEndPoint the segment end point
     */
    public void addSegmentsEndPoint(Point2D segmentEndPoint) {
        this.segmentsEndPoints.add(segmentEndPoint);
    }

    /**
     * Add the first segment end point to the track
     * @param segmentEndPoint the segment end point
     */
    public void addFirstSegmentsEndPoint(Point2D segmentEndPoint){
        this.segmentsEndPoints.addFirst(segmentEndPoint);
    }

    /**
     * Delete the first segment end point of the track
     */
    public void removeFirstSegmentsEndPoint(){
        this.segmentsEndPoints.removeFirst();
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
            throw new IllegalStateException("THE SNAPSHOT HAS ALREADY BEEN SET");
        }
    }

    /**
     * Set the track width, (the width of the track to be drawn on the canvas)
     * @param trackWidth the width of the track
     */
    public void setTrackWidth(int trackWidth) {
        Check.checkNumbersMin(DEFAULT_TRACK_WIDTH, trackWidth);
        this.trackWidth = trackWidth;
        this.getGraphicsContext2D().setLineWidth(trackWidth);
    }

    /**
     * Set the color of the canvas
     * @param color the color of the canvas
     */
    public void setColor(Color color) {
        if (this.color == null){
            this.color = color;
        } else {
            throw new IllegalStateException("THE COLOR HAS ALREADY BEEN SET");
        }
        //this.getGraphicsContext2D().setStroke(color);
    }

    /**
     * Set the track state (closed or not)
     * @param isTrackClosed the new state of the track
     */
    public void setTrackState(boolean isTrackClosed){
        this.isTrackClosed = isTrackClosed;
    }

    /**
     * Set the start line of the track, Every track needs a start line.
     * In the case of a closed track, the start line is the same as the ending line.
     * @param startLine the start line of the track
     */
    public void setStartLine(Line startLine){
        if (this.startLine == null){
            this.startLine = startLine;
        } else {
            throw new IllegalStateException("THE START LINE HAS ALREADY BEEN SET");
        }
    }

    /**
     * Set the finish line of the track,
     * @param finishLine the finish line of the track
     */
    public void setFinishLine(Line finishLine){
        if (this.finishLine == null){
            this.finishLine = finishLine;
        } else {
            throw new IllegalStateException("THE FINISH LINE HAS ALREADY BEEN SET");
        }
    }


}
