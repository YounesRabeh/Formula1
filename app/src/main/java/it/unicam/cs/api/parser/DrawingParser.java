package it.unicam.cs.api.parser;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.exceptions.parser.NoActionFoundException;
import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.components.container.Characteristics;
import it.unicam.cs.engine.util.Useful;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasRenderer;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;


/**
 * A parser responsible for drawing on the canvas.
 * by default, it has the following commands: {@link DrawingParser#defaultCommands()}
 *
 * @see AbstractParser
 * @author Younes Rabeh
 * @version 2.3
 */
public class DrawingParser extends AbstractParser {
    /** The current canvas to draw on.*/
    private Canvas currentCanvas;
    /** The GraphicsContext used to draw on the canvas.*/
    private GraphicsContext currentGC;
    /** A map of GraphicsContexts */
    private Stack<? extends Canvas> canvasStack;
    /** The game map */
    private GameMap map;


    /**
     * Creates a new DrawingParser with the given file, GraphicsContext.
     * The {@link DrawingParser#defaultCommands()} are added if the useDefaultCommands is true.
     *
     * @param file the file to be parsed
     * @param useDefaultCommands a boolean value to use the default commands
     */
    public DrawingParser(File file, boolean useDefaultCommands) {
        super(file, F1_MAP_FILE_EXTENSION);
        this.canvasStack = new Stack<>();
        baseCommand();
        if(useDefaultCommands) defaultCommands();
    }

    /**
     * Creates a new DrawingParser with the given file and GraphicsContext.
     * The {@link DrawingParser#defaultCommands()} are added.
     * @param file the file to be parsed
     */
    public DrawingParser(File file) {
        this(file, true);
    }


    @Override
    public Optional<GameMap> start() throws IOException, NoActionFoundException, IllegalStateException {
        super.start();
        return Optional.of(this.map);
    }

    /**
     * Get the GraphicsContext.
     * @return the GraphicsContext
     */
    public GraphicsContext getCurrentGC() {
        return currentGC;
    }

    /**
     * Set the parser's GraphicsContext.
     * @param currentGC the GraphicsContext to be set
     */
    public void setCurrentGC(GraphicsContext currentGC) {
        this.currentGC = currentGC;
    }


    /**
     * The base commands of the drawing parser. contains the following commands:
     * <ul>
     *     <li> {@code >} - create a new canvas with the given cell size, cell number x, cell number y</li>
     *     <li> {@code @} - switch to the next canvas</li>
     *
     */
    private void baseCommand(){
        // cell size, cell number x, cell number y
        functionMap.put('>', command -> {
            this.map = new GameMap(command.params());
            this.canvasStack = this.map.getCanvasStack();
        });

        functionMap.put('@', command -> {
            if (this.currentGC == null){
                this.currentCanvas = canvasStack.pop();
                this.currentGC = currentCanvas.getGraphicsContext2D();
                if (this.currentCanvas instanceof TrackCanvas trackCanvas){
                    trackCanvas.setTrackWidth(Characteristics.DEFAULT_TRACK_WIDTH);
                }
                return;
            }

            this.currentCanvas = canvasStack.pop();
            this.currentGC = currentCanvas.getGraphicsContext2D();
        });
    }

    /**
     * The default commands of the parser.
     * Contains the following commands:
     * <ul>
     *     <li> {@code G} - render the grid</li>
     *     <li> {@code O} - render the grid outline</li>
     *     <li> {@code $} - render the starting line</li>
     *     <li> {@code £} - render the finish line <b>(not yet implemented)</b></li>
     *     <li> {@code B} - begin a new path</li>
     *     <li> {@code K} - stroke the path</li>
     *     <li> {@code C} - set the stroke color</li>
     *     <li> {@code W} - set the line width</li>
     *     <li> {@code M} - move to the given coordinates</li>
     *     <li> {@code Q} - draw a quadratic curve</li>
     *     <li> {@code L} - draw a line</li>
     *     <li> {@code R} - fill a rectangle</li>
     *     <li> {@code F} - set the fill color</li>
     *     <li> {@code P} - draw a point</li>
     *</ul>
     */
    private void defaultCommands(){
        // Render the grid
        functionMap.put('G', (command) -> {
            if (currentCanvas instanceof GridCanvas) {
                CanvasRenderer.renderGrid((GridCanvas) currentCanvas);
            }
        });

        // Render the grid outline
        functionMap.put('O', (command) -> {
            if (currentCanvas instanceof GridCanvas) {
                CanvasRenderer.renderGridOutline((GridCanvas) currentCanvas, command.params()[0]);
            }
        });

        functionMap.put('$', (command) -> {
            if (currentCanvas instanceof TrackCanvas trackCanvas){
                this.makeSnapshot(trackCanvas, currentGC);
                int[] startingLine = CanvasTools.createLineFromPoint(
                        trackCanvas, new Point2D(command.params()[0], command.params()[1])
                );
                trackCanvas.setStartingLine(startingLine);
                trackCanvas.addFirstSegmentsEndPoint(new Point2D(command.params()[0],command.params()[1]));
                CanvasRenderer.renderTrackLineMarker(trackCanvas, startingLine);
            }
        });

        functionMap.put('£', (command) -> {
            if (currentCanvas instanceof TrackCanvas trackCanvas){
                if (trackCanvas.getStartingLine() == null){
                    throw new IllegalStateException("[!!]- NO STARTING LINE");
                }

                int[] endingLine = CanvasTools.createLineFromPoint(
                        trackCanvas, new Point2D(command.params()[0], command.params()[1])
                );
                trackCanvas.setEndingLine(endingLine);
                trackCanvas.addSegmentsEndPoints(new Point2D(command.params()[0],command.params()[1]));
                CanvasRenderer.renderTrackLineMarker(trackCanvas, endingLine);
            }
        });

        functionMap.put('§', command -> {
            if (currentCanvas instanceof TrackCanvas trackCanvas){
                trackCanvas.setTrackState(true);
            }
        });

        functionMap.put('B', (command) -> {
            Graphics.beginPath(currentGC);
        });

        functionMap.put('K', (command) -> {
            currentGC.stroke();
        });

        functionMap.put('C', (command) -> {
            Graphics.setStroke(currentGC, command.params());
        });

        functionMap.put('W', (command) -> {
            if (currentCanvas instanceof TrackCanvas trackCanvas){
                Check.checkNumbersMin(Characteristics.DEFAULT_TRACK_WIDTH, command.params()[0]);
                trackCanvas.setTrackWidth(command.params()[0]);
            }
            Graphics.setLineWidth(currentGC, command.params());
        });

        functionMap.put('M', (command) -> {
            Graphics.moveTo(currentGC, command.params());
        });

        functionMap.put('Q', (command) -> {
            if (currentCanvas instanceof TrackCanvas trackCanvas){
                if (trackCanvas.getSegmentsEndPoints().isEmpty()){
                    trackCanvas.addSegmentsEndPoints(new Point2D(command.params()[0], command.params()[1]));
                }
                trackCanvas.addSegmentsEndPoints(new Point2D(command.params()[2], command.params()[3]));
            }
            Graphics.quadraticCurveTo(currentGC, command.params());
        });

        functionMap.put('L', (command) -> {
            Graphics.strokeLine(currentGC, command.params());
        });

        functionMap.put('R', (command) -> {
            Graphics.fillRect(currentGC, command.params());
        });

        functionMap.put('F', (command) -> {
            Graphics.setFill(currentGC, command.params());
        });

        functionMap.put('P', (command) -> {
            Graphics.strokePoint(currentGC, command.params());
        });


    }

    private void makeSnapshot(TrackCanvas trackCanvas, GraphicsContext currentGC){
        try {
            trackCanvas.getTrackSnapshot();
        } catch (IllegalStateException noSnapshot){
            currentGC.stroke();
            trackCanvas.setSnapshot(CanvasTools.createCanvasSnapshot(currentCanvas));
            trackCanvas.addWaypoints(Useful.exe(this.map));
        }
    }
}
