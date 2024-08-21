package it.unicam.cs.gui.util;

import it.unicam.cs.gui.controller.SceneController;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import java.util.Collection;
import java.util.List;

import static it.unicam.cs.DebugData.*;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * Utility class for GUI operations
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0
 */
public final class GuiTools {
    private GuiTools() {}

    /**
     * Align all the nodes in the root.
     * @param root the root
     * @param nodes the nodes
     */
    public static void alignAll(AnchorPane root, Node[] nodes){
        for (Node node : nodes) {
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
        }
        root.getChildren().addAll(nodes);
    }



    /**
     * Draws the game elements on the canvases.
     * @param gameMap the game map
     */
    public static void drawGameElements(GameMap gameMap) {
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        Canvas[] canvases = gameMap.getCanvases();
        Collection<GameMap.Waypoint> waypoints = trackCanvas.getWaypoints();
        List<Point2D> segmentsEndPoints = trackCanvas.getSegmentsEndPoints();

        drawWaypoints(canvases[WAYPOINT_LVL].getGraphicsContext2D(), (List<GameMap.Waypoint>) waypoints);
        drawConnections(trackCanvas, canvases[EXTRA_LVL].getGraphicsContext2D(), segmentsEndPoints);
        drawParsedSegmentEndPoints(canvases[END_POINTS_LVL].getGraphicsContext2D(), segmentsEndPoints);
    }
}
