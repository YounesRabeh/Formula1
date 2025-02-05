package it.unicam.cs.gui.util;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Collection;
import java.util.List;


import static it.unicam.cs.api.parser.types.PropertiesParser.CONFIG_PROPERTIES_PATH;
import static it.unicam.cs.api.parser.types.PropertiesParser.getIntProperty;
import static it.unicam.cs.engine.util.Useful.*;

/**
 * Utility class for GUI operations
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.5
 */
public final class GuiTools {
    private GuiTools() {}

    // TEMP: For testing purposes
    public static final int TRACK_LVL = getIntProperty(CONFIG_PROPERTIES_PATH, "TRACK_LVL");
    public static final int EXTRA_LVL = getIntProperty(CONFIG_PROPERTIES_PATH, "EXTRA_LVL");
    public static final int WAYPOINT_LVL = getIntProperty(CONFIG_PROPERTIES_PATH, "WAYPOINT_LVL");
    public static final int END_POINTS_LVL = getIntProperty(CONFIG_PROPERTIES_PATH, "END_POINTS_LVL");
    public static final int GRID_LVL = getIntProperty(CONFIG_PROPERTIES_PATH, "GRID_LVL");

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
     * Aligns a node in the root.
     * @param root the root
     * @param node the node
     */
    public static void align(AnchorPane root, Node node){
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        root.getChildren().add(node);
    }


    // TEMP: For visualizing the parsed data
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

    public static void mapUpdate(GameMap gameMap, AnchorPane mapArea) {
        for (Driver driver : gameMap.getDrivers()) {
            GuiTools.draw(mapArea, gameMap, driver);
        }
        GuiTools.align(mapArea, gameMap.getCanvasGroup());
    }

    /**
     * Draws the drivers on the track.
     * @param gameMap the game map
     */
    public static void drawDriversOnTrack(GameMap gameMap) {
        List<Driver> drivers = gameMap.getDrivers();
        for (Driver driver : drivers) {
            GameMap.Waypoint position = driver.getPosition();
            Canvas[] canvases = gameMap.getCanvases();
            drawWaypoint(canvases[WAYPOINT_LVL].getGraphicsContext2D(), position, driver.getCarColor());
        }
    }

    public static void clearWaypointsGC(GameMap gameMap) {
        Canvas canvas = gameMap.getCanvases()[WAYPOINT_LVL];
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public static void draw(AnchorPane mapArea, GameMap gameMap, Driver driver) {

        mapArea.getChildren().clear();
        clearWaypointsGC(gameMap);

        //Canvas[] canvases = gameMap.getCanvases();
        //GameMap.Waypoint[] possibleNextWaypoints =
        //        RouteFinder.getPossibleNextWaypoints(
        //                gameMap,
        //                driver
        //        );

        //for (GameMap.Waypoint waypoint : possibleNextWaypoints) {
        //    drawWaypoint(canvases[WAYPOINT_LVL].getGraphicsContext2D(), waypoint, Color.AQUA);
        //}
        GuiTools.drawDriversOnTrack(gameMap);
        //System.out.println("Driver name:" + driver.getName() + " Driver position:" + driver.getPosition());
        //System.out.println("Possible position:" + Arrays.toString(possibleNextWaypoints));

        Collection<GameMap.Waypoint> targets = gameMap.getFinishLine().getWaypoints();
        GameMap.Waypoint bestTarget = RouteFinder.getBestTarget(
                driver.getPosition(),
                targets
        );

        //System.out.println("Best target:" + bestTarget);

    }

    public static void updateCommandButtons(Driver driver, GameMap gameMap, Button[] commandButtons) {
        GameMap.Waypoint[] possibleNextWaypoints =
                RouteFinder.getPossibleNextWaypoints(
                        gameMap,
                        driver
                );
        for (int i = 0; i < commandButtons.length; i++) {
            Button button = commandButtons[i];
            GameMap.Waypoint nextWaypoint = possibleNextWaypoints[i];
            button.setDisable(nextWaypoint == null);
        }
    }

    /**
     * Show an alert popup.
     * @param type the alert type
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     */
    public static void alertPopup(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
