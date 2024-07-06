package it.unicam.cs.gui.map;

import it.unicam.cs.api.components.container.Characteristics;
import it.unicam.cs.engine.util.Check;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.Stack;
import java.util.TreeSet;

import static it.unicam.cs.api.components.container.Characteristics.GRID_COLOR;
import static it.unicam.cs.api.components.container.Characteristics.TRACK_COLOR;

/**
 * {@code GameMap} is a map that contains a grid canvas, a track canvas, and inert canvases.
 * The grid canvas is used to display a grid (the top most layer), the track canvas is used to
 * display the track (the lowest layer),
 * and the inert canvases are used to display inert objects on the map.
 * @see GridCanvas
 * @see TrackCanvas
 * @see InertCanvas
 * @see Characteristics
 * @author Younes Rabeh
 * @version 2.0
 */
public class GameMap {
    /** The grid canvas */
    private final GridCanvas gridCanvas;
    /** The track canvas */
    private final TrackCanvas trackCanvas;
    /** The inert canvases */
    private final TreeSet<InertCanvas> inertCanvases;

    /** The width of the map */
    private int width;
    /** The height of the map */
    private int height;


    /**
     * Create a new map with the given cell size and number of cells in the x and y directions.
     * @param cellSize the size of each cell
     * @param cellNumber_X the number of cells in the x direction
     * @param cellNumber_Y the number of cells in the y direction
     */
    public GameMap(int cellSize, int cellNumber_X, int cellNumber_Y, int layerNumber) {
        Check.checkNumbers(cellSize, cellNumber_X, cellNumber_Y, layerNumber);
        this.width = cellSize * cellNumber_X;
        this.height = cellSize * cellNumber_Y;

        gridCanvas = new GridCanvas(cellSize, cellNumber_X, cellNumber_Y, GRID_COLOR);
        trackCanvas = new TrackCanvas(this.width, this.height, TRACK_COLOR);
        inertCanvases = new TreeSet<>();

        addInertCanvases(layerNumber);
        gridEvent();
    }

    /**
     * Create a new square map with the given cell size and number of cells.
     * @param cellSize the size of each cell
     * @param cellNumber the number of cells in the x,y direction
     */
    public GameMap(int cellSize, int cellNumber, int layerNumber) {
        this(cellSize, cellNumber, cellNumber, layerNumber);
    }

    /**
     * Create a new map with the given parameters. used by the parser.
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
            Color pixelColor = CanvasTools.getPixelColor(x, y, trackCanvas.getCanvasSnapshot());
            if (pixelColor.equals(TRACK_COLOR)){
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
}
