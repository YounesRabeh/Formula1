package it.unicam.cs.engine.util;

import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public final class Useful {
    /**
     * Print the waypoints of the game map.
     * @param waypoints the waypoints of the game map
     */
    public static void printWaypoints(GraphicsContext gc, List<GameMap.Waypoint> waypoints) {
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (GameMap.Waypoint coords : waypoints) {
            System.out.printf("Black pixel found at (%d, %d)\n", (int) coords.getX(), (int) coords.getY());
        }
    }

    /**
     * Draw the waypoints of the game map.
     * @param gc the GraphicsContext
     * @param waypoints the waypoints of the game map
     */
    public static void drawWaypoints(GraphicsContext gc, List<GameMap.Waypoint> waypoints){
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (GameMap.Waypoint coords : waypoints) {
            Graphics.strokePoint(gc, new int[]{(int) coords.getX(), (int) coords.getY()});
        }
    }

    /**
     * Draw the parsed segment end points of the track.
     * @param gc the GraphicsContext
     * @param segmentsEndPoints the parsed segment end points of the track
     */
    public static void drawParsedSegmentEndPoints(GraphicsContext gc, List<Point2D> segmentsEndPoints){
        Graphics.setFill(gc, new int[]{0, 0, 255});
        for (Point2D coords : segmentsEndPoints) {
            Graphics.strokePoint(gc, new int[]{(int) coords.getX(), (int) coords.getY()});
        }
    }

    /**
     * Draw the connections between the endpoints.
     * @param trackCanvas the track canvas
     * @param drawingGC the Graphics context to drw the connections on
     * @param points2D the points to connect
     */
    public static void drawConnections(TrackCanvas trackCanvas, GraphicsContext drawingGC, List<Point2D> points2D){
        Check.checkNull(trackCanvas, drawingGC, points2D);
        Graphics.setStroke(drawingGC, new int[]{0, 0, 255});
        Graphics.setLineWidth(drawingGC, new int[]{5});
        for(int i = 0; i < points2D.size() - 1; i++){
            Point2D p1 = points2D.get(i);
            Point2D p2 = points2D.get(i + 1);
            Graphics.quadraticCurveTo(drawingGC, new int[]{(int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY()});
        } // close the circuit
        if (trackCanvas.getTrackState()){
            Point2D p1 = points2D.getLast();
            Point2D p2 = points2D.getFirst();
            Graphics.quadraticCurveTo(drawingGC, new int[]{(int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY()});
        }
        drawingGC.stroke();
    }


    /**
     * Align all the nodes in the root.
     * @param root the root
     * @param pos the position
     * @param nodes the nodes
     */
    public static void alignAll(AnchorPane root, Pos pos, Node[] nodes){
        for (Node node : nodes) {
            StackPane.setAlignment(node, pos);
        }
        root.getChildren().addAll(nodes);
    }

}
