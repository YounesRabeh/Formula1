package it.unicam.cs.engine.util;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.engine.core.route.RouteTools;
import it.unicam.cs.gui.map.GameMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

import java.util.List;

public final class Useful {
    /**
     * Get the waypoints of the game map.
     * @param gameMap the game map
     * @return the waypoints of the game map
     */
    public static List<Waypoint> exe(GameMap gameMap){
        long startTime = System.nanoTime();
        List<Waypoint> waypoints = RouteTools.getGameMapWaypoints(gameMap);
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " nanoseconds " +
                "or " + (float) (endTime - startTime) / 1000000 + " milliseconds\n" +
                "> Found " + waypoints.size() + " black pixels"
        );
        return waypoints;
    }

    /**
     * Print the waypoints of the game map.
     * @param waypoints the waypoints of the game map
     */
    public static void printWaypoints(GraphicsContext gc, List<Waypoint> waypoints) {
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (Waypoint coords : waypoints) {
            System.out.printf("Black pixel found at (%d, %d)\n", (int) coords.getX(), (int) coords.getY());
        }
    }

    /**
     * Draw the waypoints of the game map.
     * @param gc the GraphicsContext
     * @param waypoints the waypoints of the game map
     */
    public static void drawWaypoints(GraphicsContext gc, List<Waypoint> waypoints){
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (Waypoint coords : waypoints) {
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
     * @param gc the GraphicsContext
     * @param points2D the points to connect
     */
    public static void drawConnections(GraphicsContext gc, List<Point2D> points2D){
        Graphics.setStroke(gc, new int[]{0, 0, 255});
        Graphics.setLineWidth(gc, new int[]{10});
        for(int i = 0; i < points2D.size() - 1; i++){
            Point2D p1 = points2D.get(i);
            Point2D p2 = points2D.get(i + 1);
            Graphics.strokeLine(gc, new int[]{(int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY()});
        } // close the circuit
        Point2D p1 = points2D.getLast();
        Point2D p2 = points2D.getFirst();
        Graphics.strokeLine(gc, new int[]{(int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY()});

    }


    /**
     * Align all the nodes in the root.
     * @param root the root
     * @param pos the position
     * @param nodes the nodes
     */
    public static void alignAll(StackPane root, Pos pos, Node[] nodes){
        for (Node node : nodes) {
            StackPane.setAlignment(node, pos);
        }
        root.getChildren().addAll(nodes);
    }

}
