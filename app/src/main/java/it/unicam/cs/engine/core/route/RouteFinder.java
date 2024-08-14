package it.unicam.cs.engine.core.route;

import it.unicam.cs.api.components.nodes.Route;
import it.unicam.cs.gui.map.GameMap;
import javafx.geometry.Point2D;

import java.util.*;

/**
 *
 * @author Younes Rabeh
 * @version 1.0
 */
public final class RouteFinder {
    /** Prevent instantiation of this utility class. */
    private RouteFinder() {}


    public static Route findRoute(Collection<GameMap.Waypoint> waypoints, GameMap gameMap, GameMap.Waypoint start) {
        Queue<List<GameMap.Waypoint>> queue = new LinkedList<>();
        Set<GameMap.Waypoint> visited = new HashSet<>();
        queue.add(Collections.singletonList(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<GameMap.Waypoint> path = queue.poll();
            GameMap.Waypoint current = path.getLast();

            if (gameMap.getFinishLine().contains(current)) {
                Route route = new Route();
                route.addAll(path);
                return route;
            }

            for (GameMap.Waypoint movement : gameMap.getTrackCanvas().getWaypoints()) {
                GameMap.Waypoint next = gameMap.createWaypoint(current.getX() + movement.getX(),
                        current.getY() + movement.getY());
                if (waypoints.contains(next) && !visited.contains(next)) {
                    visited.add(next);
                    List<GameMap.Waypoint> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(newPath);
                }
            }
        }
        return new Route(); // No path found
    }
}

