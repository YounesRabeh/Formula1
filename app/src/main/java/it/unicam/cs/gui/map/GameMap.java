package it.unicam.cs.gui.map;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.actors.structs.Movement;
import it.unicam.cs.api.components.nodes.FinishLine;
import it.unicam.cs.api.components.nodes.StartLine;
import it.unicam.cs.gui.util.CanvasTools;
import it.unicam.cs.gui.util.MapTools;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static it.unicam.cs.api.parser.types.AbstractParser.PARSER_SEPARATOR;
import static it.unicam.cs.gui.controller.GameSetupSceneController.MATCH_MAKING_FILE;


/**
 * {@code GameMap} is a map that contains a grid canvas, a track canvas, and inert canvases.
 * The grid canvas is used to display a grid (the top most layer), the track canvas is used to
 * display the track (the lowest layer),
 * and the inert canvases are used to display inert objects on the map.
 * @see GridCanvas
 * @see TrackCanvas
 * @see InertCanvas
 * @author Younes Rabeh
 * @version 2.4
 */
public class GameMap {
    /** The grid canvas */
    private final GridCanvas gridCanvas;
    /** The track canvas */
    private final TrackCanvas trackCanvas;
    /** The inert canvases */
    private final TreeSet<InertCanvas> inertCanvases;
    /** The width of the map */
    private final int width;
    /** The height of the map */
    private final int height;
    /** The start line of the track */
    private StartLine startLine;
    /** The finish line of the track (The car's target) */
    private FinishLine finishLine;
    /** The maximum number of Drivers on this map*/
    private int maxDriversNumber;
    /** The drivers on this map */
    private List<Driver> drivers;


    /**
     * Create a new map with the given cell size and number of cells in the x and y directions.
     * a map always create a grid and a track canvas. The inert canvases are created based on the layer number.
     * @param cellSize the size of each cell
     * @param cellNumber_X the number of cells in the x direction
     * @param cellNumber_Y the number of cells in the y direction
     * @param layerNumber the number of inert layers
     */
    public GameMap(int cellSize, int cellNumber_X, int cellNumber_Y, int layerNumber) {
        Check.checkNumbers(cellSize, cellNumber_X, cellNumber_Y, layerNumber);
        initDrivers();
        //TODO: check if the generated map is smaller than window size
        this.width = cellSize * cellNumber_X;
        this.height = cellSize * cellNumber_Y;

        gridCanvas = new GridCanvas(cellSize, cellNumber_X, cellNumber_Y, GridCanvas.GRID_COLOR);
        trackCanvas = new TrackCanvas(this.width, this.height, TrackCanvas.TRACK_COLOR);
        inertCanvases = new TreeSet<>();

        addInertCanvases(layerNumber);
        //gridEvent(); //Get pixel color on click
    }

    /**
     * Create a new square map with the given cell size and number of cells.
     *  a map always create a grid and a track canvas. The inert canvases are created based on the layer number.
     * @param cellSize the size of each cell
     * @param cellNumber the number of cells in the x,y direction
     * @param layerNumber the number of inert layers
     */
    public GameMap(int cellSize, int cellNumber, int layerNumber) {
        this(cellSize, cellNumber, cellNumber, layerNumber);
    }

    /**
     * Create a new map with the given parameters. used by the parser.
     * A map always create a grid and a track canvas. The inert canvases are created based on the layer number.
     * @param params the parameters of the map
     */
    public GameMap(int[] params) {
        this(params[0], params[1], params[2], params[3]);
    }

    //TODO: move this to a separate class
    private void gridEvent(){
        gridCanvas.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            Color pixelColor = CanvasTools.getPixelColor(x, y, trackCanvas.getTrackSnapshot());
            if (pixelColor.equals(trackCanvas.getColor())){
                System.out.printf("(%d, %d) is a TRACK pixel\n", x, y);
            } else {
                System.out.printf("(%d, %d) is NOT A TRACK pixel, having %s as a color.\n",
                        x, y, CanvasTools.colorToRGBString(pixelColor));
            }
        });
    }

    /**
     * Add an inert canvas to the map.
     */
    private void addInertCanvases(int layerNumber){
        for (int i = 0; i < layerNumber; i++){
            inertCanvases.add(new InertCanvas(
                    this.width,
                    this.height,
                    this.inertCanvases.size())
            );
        }
    }

    /**
     * Get the grid canvas
     * @return the grid canvas
     */
    public GridCanvas getGridCanvas() {
        return gridCanvas;
    }

    /**
     * Get the track canvas
     * @return the track canvas
     */
    public TrackCanvas getTrackCanvas() {
        return trackCanvas;
    }

    /**
     * Get the all canvases, as a sorted array in ascending order of their layers.
     * @return the canvases
     */
    public Canvas[] getCanvases() {
        int size = inertCanvases.size() + 2;
        Canvas[] canvases = new Canvas[size];

        Stack<Canvas> canvasStack = getCanvasStack();
        int i = 0;
        while (!canvasStack.isEmpty()){
            canvases[i++] = canvasStack.pop();
        }

        return canvases;
    }

    /**
     * Get all the canvases in the map as a stack.
     * @return a stack of canvases
     */
    public Stack<Canvas> getCanvasStack(){
        Stack<Canvas> mapCanvases = new Stack<>();

        mapCanvases.push(gridCanvas);
        for (InertCanvas inertCanvas : inertCanvases) {
            mapCanvases.push(inertCanvas);
        }
        mapCanvases.push(trackCanvas);

        return mapCanvases;
    }

    /**
     * Get the canvases as a group.
     * @return the canvases as a group
     */
    public Group getCanvasGroup() {
        Group group = new Group();
        group.getChildren().addAll(getCanvases());
        return group;
    }

    /**
     * Get the grid canvas
     * @return the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the map
     * @return the height of the map
     */
    public int getHeight() {
        return height;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    /**
     * Initialize the drivers from the match making file.
     */
    private void initDrivers() {
        List<Driver> driversFromFile = new ArrayList<>();
        if (MATCH_MAKING_FILE == null || !MATCH_MAKING_FILE.exists() || MATCH_MAKING_FILE.length() == 0) {
            drivers = driversFromFile;
        }
        try {
            assert MATCH_MAKING_FILE != null;
            try (BufferedReader reader = new BufferedReader(new FileReader(MATCH_MAKING_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    assert PARSER_SEPARATOR != null;
                    String[] data = line.split(PARSER_SEPARATOR);
                    if (data.length == 3) {
                        String type = data[0];
                        String name = data[1];
                        String color = data[2];
                        if ("P".equals(type)) {
                            Player player = new Player(name);
                            player.setCarColor(Color.valueOf(color));
                            driversFromFile.add(player);
                        } else if ("B".equals(type)) {
                            Bot bot = new Bot(name);
                            bot.setCarColor(Color.valueOf(color));
                            driversFromFile.add(bot);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        drivers = driversFromFile;
    }


    /**
     * Get the finish line of the track, as a collection of waypoints.
     * @return the finish line of the track
     */
    public FinishLine getFinishLine() {
        return finishLine;
    }

    /**
     * Create a finish line with the given origin waypoint.
     * The origin should be the waypoint fetched from the {@code finishLine command} in the parser.
     * @param origin the origin waypoint
     */
    public void createFinishLine(Waypoint origin){
        finishLine = new FinishLine(MapTools.getWaypointsOnLevelX(this, origin));
    }

    public void createStartLine(Waypoint origin){
        startLine = new StartLine(MapTools.getWaypointsOnLevelX(this, origin));
        maxDriversNumber = startLine.getWaypoints().size();
    }

    /**
     * Get the maximum number of drivers on this map.
     * @return the maximum number of drivers on this map
     */
    public int getMaxDriversNumber() {
        return maxDriversNumber;
    }

    public List<Waypoint> getStartingPosition(){
        return (List<Waypoint>) startLine.getWaypoints();
    }

    /**
     * Create a new waypoint with the given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a new waypoint
     * @throws IllegalArgumentException if the waypoint is not on a grid intersection
     */
    public Waypoint createWaypoint(double x, double y) throws IllegalArgumentException {
        checkWaypointCoordinates(x, y);
        return new Waypoint(x, y);
    }

    /**
     * Check if the waypoint is on a grid intersection.
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if the waypoint is not on a grid intersection or
     * if x or y are non-positive numbers
     */
    private void checkWaypointCoordinates(double x, double y){
        Check.checkNumbers(x, y);
        int currentCellSize = gridCanvas.getCellSize();
        if ((x % currentCellSize != 0) || (y % currentCellSize != 0)) {
            throw new IllegalArgumentException(String.format("THE WAYPOINT (x=%d,y=%d) IS NOT ON A GRID INTERSECTION. " +
                    "THE CELL SIZE IS %d", (int) x, (int) y, currentCellSize));
        }
    }

    /**
     * Check if the waypoint is free (no driver is on it).
     * @param waypoint the waypoint to check
     * @return true if the waypoint is free, false otherwise
     */
    public boolean isWaypointFree(Waypoint waypoint) {
        return drivers.stream().noneMatch(driver -> driver.getPosition().equals(waypoint));
    }

    /**
     * Get the bots on this map.
     * @return the bots on this map
     */
    public List<Bot> getBots() {
        List<Bot> bots = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver instanceof Bot) {
                bots.add((Bot) driver);
            }
        }
        return bots;
    }

    /**
     * A {@code Waypoint} is a point in a 2D space that is on a grid intersection.
     * so It's an admissible point in the map (an admissible position for ta car).
     * @see Point2D
     * @author Younes Rabeh
     * @version 1.2
     */
    public static class Waypoint extends Point2D {
        private Waypoint(double x, double y) {
            super(x, y);
        }

        @Override
        public String toString() {
            return String.format("Waypoint (x=%d, y=%d)" , (int) getX(), (int) getY());
        }
    }

    //TEMP: will be deleted

    /**
     * Get the possible next (reachable) waypoints, given a waypoint.
     * @return the possible next waypoints
     */
    public Collection<Waypoint> getPossibleNextWaypoints(GameMap.Waypoint waypoint) {
        // The function suppose that the waypoint is on the grid intersection
        Collection<GameMap.Waypoint> possibleNextWaypoints = new ArrayList<>();
        int cellSize = this.getGridCanvas().getCellSize();

        for (Movement movement : Movement.values()) {
            double newX = waypoint.getX() + movement.getXOffset() * cellSize;
            double newY = waypoint.getY() + movement.getYOffset() * cellSize;
            try {
                GameMap.Waypoint nextWaypoint = this.createWaypoint(newX, newY);
                if (this.getTrackCanvas().containsWaypoint(nextWaypoint)) {
                    possibleNextWaypoints.add(nextWaypoint);
                }
            } catch (IllegalArgumentException ignored) {
                // Ignore waypoints that are not on the grid intersection
            }
        }

        return possibleNextWaypoints;
    }
}
