package it.unicam.cs.app.parser;

import it.unicam.cs.app.api.Graphics;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;

/**
 * This class is responsible for parsing the data from the file.
 * The Parser is used to read the data from the file and return it as a stream of strings,
 * which will be used to draw the racing circuit, by utilizing the parsed coordinates of the track.
 *
 * @see Interpretable
 * @author Younes Rabeh
 */
public class DrawingParser extends AbstractParser {
    /** The GraphicsContext used to draw on the canvas.*/
    private GraphicsContext graphicsContext;

    public DrawingParser(File file, GraphicsContext graphicsContext) {
        super(file);
        this.graphicsContext = graphicsContext;
        defaultCommands();
    }


    public GraphicsContext getGC() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    private void defaultCommands(){
        functionMap.put('B', (command) -> {
            Graphics.beginPath(graphicsContext);
        });

        functionMap.put('S', (command) -> {
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
    }

}
