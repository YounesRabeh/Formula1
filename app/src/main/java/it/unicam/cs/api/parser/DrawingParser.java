package it.unicam.cs.api.parser;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.exceptions.NoActionFoundException;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasRenderer;
import it.unicam.cs.gui.util.CanvasTools;
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
 * @version 2.2
 */
public class DrawingParser extends AbstractParser {
    /** The current canvas to draw on.*/
    private Canvas currentCanvas;
    /** The GraphicsContext used to draw on the canvas.*/
    private GraphicsContext currentGC;
    /** A map of GraphicsContexts */
    private Stack<? extends Canvas> canvasStack;

    private GameMap map;
    /** A boolean value to check if the track is drawn */
    private boolean isTrackDrawn = false;


    /**
     * Creates a new DrawingParser with the given file, GraphicsContext.
     * The {@link DrawingParser#defaultCommands()} are added if the useDefaultCommands is true.
     *
     * @param file the file to be parsed
     * @param useDefaultCommands a boolean value to use the default commands
     */
    public DrawingParser(File file, boolean useDefaultCommands) {
        super(file);
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


    private void baseCommand(){
        // cell size, cell number x, cell number y
        functionMap.put('>', command -> {
            this.map = new GameMap(command.params());
            this.canvasStack = this.map.getCanvasStack();
        });

        functionMap.put('@', command -> {
            if (!isTrackDrawn && currentCanvas instanceof TrackCanvas trackCanvas){
                Graphics.setStroke(currentGC, trackCanvas.getColor());
                currentGC.stroke();
                trackCanvas.setSnapshot(CanvasTools.createCanvasSnapshot(currentCanvas));
                isTrackDrawn = true;
            }

            this.currentCanvas = canvasStack.pop();
            this.currentGC = currentCanvas.getGraphicsContext2D();
        });
    }

    /**
     * The default commands of the parser.
     */
    private void defaultCommands(){
        // Render the grid
        functionMap.put('G', (command) -> {
            if (currentCanvas instanceof GridCanvas) {
                CanvasRenderer.RenderGrid((GridCanvas) currentCanvas);
            }
        });

        // Render the grid outline
        functionMap.put('O', (command) -> {
            if (currentCanvas instanceof GridCanvas) {
                CanvasRenderer.RenderGridOutline((GridCanvas) currentCanvas, command.params()[0]);
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
            Graphics.setLineWidth(currentGC, command.params());
        });

        functionMap.put('Q', (command) -> {
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
}
