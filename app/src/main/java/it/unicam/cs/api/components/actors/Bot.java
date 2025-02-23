package it.unicam.cs.api.components.actors;


import it.unicam.cs.gui.map.GameMap;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Represents a bot in the game. A bot is a driver that is controlled by the computer
 * @author Younes Rabeh
 * @version 1.2
 */
public class Bot extends Driver {

    private Stack<GameMap.Waypoint> checkpoints = new Stack<>();
    private GameMap.Waypoint currentTarget;

    public Bot(String name, Color carColor) {
        super(name, carColor);
    }

    public Bot(String name) {
        super(name, Color.RED);
    }

    public void setCheckpoints(List<GameMap.Waypoint> checkpoints) {
        if (checkpoints == null || checkpoints.isEmpty()) {
            return;
        }
        LinkedHashSet<GameMap.Waypoint> uniqueWaypoints = new LinkedHashSet<>(checkpoints);
        List<GameMap.Waypoint> filteredCheckpoints = new ArrayList<>(uniqueWaypoints);
        if (!filteredCheckpoints.isEmpty()) {
            filteredCheckpoints.removeFirst();
        }
        Collections.reverse(filteredCheckpoints);
        // Assign to stack
        this.checkpoints = new Stack<>();
        this.checkpoints.addAll(filteredCheckpoints);

        if (!this.checkpoints.isEmpty()) {
            this.currentTarget = this.checkpoints.pop();
        }

        System.out.println("Checkpoints after pop: " + this.checkpoints);
    }


    public void addCheckpoint(GameMap.Waypoint waypoint) {
        checkpoints.push(waypoint);
    }

    public GameMap.Waypoint seeNextCheckpoint() {
        return checkpoints.peek();
    }

    public GameMap.Waypoint getNextCheckpoint() {
        return checkpoints.pop();
    }

    public void setTarget(GameMap.Waypoint target) {
        this.currentTarget = target;
    }

    public GameMap.Waypoint getTarget() {
        return currentTarget;
    }

    public Stack<GameMap.Waypoint> getCheckpoints() {
        return checkpoints;
    }
}
