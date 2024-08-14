package it.unicam.cs.gui.util;


import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.TrackCanvas;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This utility class provides methods to interact with the map.
 * @see it.unicam.cs.gui.map.GameMap
 * @author Younes Rabeh
 * @version 1.0
 */
public final class MapTools {
    /** Prevent instantiation */
    private MapTools() {}

    public static Collection<GameMap.Waypoint> getWaypointsOnLevelX(GameMap gameMap, GameMap.Waypoint origin){
        TrackCanvas trackCanvas = gameMap.getTrackCanvas();
        Line finishLine = trackCanvas.getFinishLine();
        int cellSize = gameMap.getGridCanvas().getCellSize();
        WritableImage snapshot = trackCanvas.getTrackSnapshot();

        Collection<GameMap.Waypoint> waypointsOnLevelX = new ArrayList<>();
        if (trackCanvas.containsWaypoint(origin)) {
            int x = (int) origin.getX();
            int y = (int) origin.getY();
            int width = trackCanvas.getTrackWidth();

            // Correct the x coordinate to the nearest waypoint on the x-axis
            int leftMostX = correctPointToWaypointX(x - width, cellSize);
            int rightMostX = x + width;

            for (int i = leftMostX; i <= rightMostX; i+=cellSize){
                if (i < 0 || i >= snapshot.getWidth()) continue;
                GameMap.Waypoint waypoint = gameMap.createWaypoint(i, y);
                if (trackCanvas.containsWaypoint(waypoint)){
                    waypointsOnLevelX.add(waypoint);
                }
            }
        }

        return waypointsOnLevelX;
    }

    /**
     * Corrects the x coordinate of the given point to the nearest waypoint on the x-axis.
     * @param x the x coordinate to correct
     * @param cellSize the size of the cell
     * @return the corrected x coordinate
     */
    private static int correctPointToWaypointX(int x, int cellSize){
        return x - (x % cellSize);
    }
}
