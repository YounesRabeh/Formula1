package it.unicam.cs.api.parser;

import it.unicam.cs.api.components.container.Graphics;
import it.unicam.cs.api.exceptions.NoActionFoundException;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;
import java.io.IOException;

/**
 * A parser responsible for drawing on the canvas.
 * by default, it has the following commands: {@link DrawingParser#defaultCommands()}
 *
 * @see AbstractParser
 * @author Younes Rabeh
 * @version 1.5
 */
public class DrawingParser extends AbstractParser {
    /** The GraphicsContext used to draw on the canvas.*/
    private GraphicsContext graphicsContext;
    //TODO: Add the ability to switch between graphicsContexts

    /**
     * Creates a new DrawingParser with the given file and GraphicsContext.
     * The {@link DrawingParser#defaultCommands()} are added.
     * @param file the file to be parsed
     * @param graphicsContext the GraphicsContext to draw on
     */
    public DrawingParser(File file, GraphicsContext graphicsContext) {
        super(file);
        this.graphicsContext = graphicsContext;
        defaultCommands();
    }

    /**
     * Creates a new DrawingParser with the given file, GraphicsContext.
     * The {@link DrawingParser#defaultCommands()} are added if the useDefaultCommands is true.
     *
     * @param file the file to be parsed
     * @param graphicsContext the GraphicsContext to draw on
     * @param useDefaultCommands a boolean value to use the default commands
     */
    public DrawingParser(File file, GraphicsContext graphicsContext, boolean useDefaultCommands) {
        super(file);
        this.graphicsContext = graphicsContext;
        if(useDefaultCommands){
            defaultCommands();
        }
    }

    /**
     * Creates a new DrawingParser with the given file and <strong>no GraphicsContext</strong>.
     * The {@link DrawingParser#defaultCommands()} are added.
     * @param file the file to be parsed
     */
    public DrawingParser(File file) {
        super(file);
        defaultCommands();
    }

    /**
     * Creates a new DrawingParser with the given file and <strong>no GraphicsContext</strong>.
     * The {@link DrawingParser#defaultCommands()} are added if the useDefaultCommands is true.
     * @param file the file to be parsed
     * @param useDefaultCommands a boolean value to use the default commands
     */
    public DrawingParser(File file, boolean useDefaultCommands) {
        super(file);
        if(useDefaultCommands){
            defaultCommands();
        }
    }

    @Override
    public void start() throws IOException, NoActionFoundException, IllegalStateException {
        if(graphicsContext == null){
            throw new IllegalStateException("[!!!] - No GraphicsContext found");
        }
        super.start();
    }


    /**
     * Get the GraphicsContext.
     * @return the GraphicsContext
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    /**
     * Set the parser's GraphicsContext.
     * @param graphicsContext the GraphicsContext to be set
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * The default commands of the parser.
     */
    private void defaultCommands(){
        functionMap.put('B', (command) -> {
            Graphics.beginPath(graphicsContext);
        });

        functionMap.put('K', (command) -> {
            graphicsContext.stroke();
        });

        functionMap.put('C', (command) -> {
            Graphics.setStroke(graphicsContext, command.params());
        });

        functionMap.put('W', (command) -> {
            Graphics.setLineWidth(graphicsContext, command.params());
        });

        functionMap.put('Q', (command) -> {
            Graphics.quadraticCurveTo(graphicsContext, command.params());
        });

        functionMap.put('L', (command) -> {
            Graphics.strokeLine(graphicsContext, command.params());
        });

        functionMap.put('R', (command) -> {
            Graphics.fillRect(graphicsContext, command.params());
        });

        functionMap.put('F', (command) -> {
            Graphics.setFill(graphicsContext, command.params());
        });

        functionMap.put('P', (command) -> {
            Graphics.strokePoint(graphicsContext, command.params());
        });
    }
}
