package it.unicam.cs;

import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.util.CanvasTools;
import it.unicam.cs.api.parser.DrawingParser;
import it.unicam.cs.gui.util.CanvasRenderer;
import it.unicam.cs.api.components.container.Graphics;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.unicam.cs.gui.util.CanvasTools.getCanvasSnapshot;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
//        URL url = getClass().getResource("hello-view.fxml");
//        System.out.println("url = " + url);
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // Create a Canvas
        int cellSize = 10; //TODO: -> interface
        Canvas trackCanvas = new Canvas(800, 800);
        GridCanvas gridCanvas = new GridCanvas(cellSize,800.0, 800.0, Color.GRAY);

        // Get the GraphicsContext of the canvas
        GraphicsContext track_gc = trackCanvas.getGraphicsContext2D();


        // Draw something on the canvas

        CanvasRenderer.RenderGrid(gridCanvas);
        CanvasRenderer.RenderGridOutline(gridCanvas, Color.GRAY);
        drawCircuit(track_gc);

        // Create a layout pane to hold the canvas
        StackPane root = new StackPane();
        root.setBackground(Background.fill(Color.rgb(0 ,200, 0)));
        // Align the canvases to the left side
        StackPane.setAlignment(trackCanvas, Pos.CENTER_LEFT);
        StackPane.setAlignment(gridCanvas, Pos.CENTER_LEFT);

        root.getChildren().addAll(trackCanvas, gridCanvas);

        // Create a scene with the layout pane
        Scene scene = new Scene(root, 1000, 1000);

        // Set up the stage and show it
        stage.setTitle("Gagata ");
        stage.setScene(scene);
        stage.show();
        WritableImage trackCanvasImage = getCanvasSnapshot(trackCanvas);


        // Get the color of a specific pixel on mouse click (example)
        // get the color of the track pixel on mouse click
        gridCanvas.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            Color pixelColor = CanvasTools.getPixelColor(trackCanvas, x, y,trackCanvasImage);
            System.out.printf("Pixel color at (%d, %d): " +
                    CanvasTools.colorToRGBString(pixelColor) + "\n", x, y);
        });

        long startTime = System.nanoTime();
        List<int[]> blackPixels = getBlackPixels(trackCanvas, cellSize, trackCanvasImage);
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " nanoseconds " +
                "or " + (float) (endTime - startTime) / 1000000 + " milliseconds\n" +
                "> Found " + blackPixels.size() + " black pixels"
        );

        //[
        printBlackPixels(track_gc, blackPixels);

    }

    private void printBlackPixels(GraphicsContext gc, List<int[]> blackPixels) {
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (int[] coords : blackPixels) {
            System.out.printf("Black pixel found at (%d, %d)\n", coords[0], coords[1]);
            Graphics.strokePoint(gc, coords);
        }
    }

    private void drawCircuit(GraphicsContext gc) throws IOException, URISyntaxException {
        // Clear the canvas
        gc.clearRect(0, 0, 900, 900);

        String PATH = "/it/unicam/cs/test.txt";
        // Get the absolute path of the file
        // NOTE: to get gradle to work, you need to put the file in the resources folder
        String absolutePath = Objects.requireNonNull(
                getClass().getResource(PATH)).toURI().getPath();
        // use classLoader
        DrawingParser parser = new DrawingParser(new File(absolutePath), gc);
        parser.start();
        System.out.println(parser.getAllIdentifiers());
    }


    private List<int[]> getBlackPixels(Canvas trackCanvas, int step, WritableImage trackCanvasImage) {
        List<int[]> blackPixels = new ArrayList<>();
        for (int x = 0; x < trackCanvas.getWidth(); x += step) {
            for (int y = 0; y < trackCanvas.getHeight(); y += step) {
                Color pixelColor = CanvasTools.getPixelColor(trackCanvas, x, y, trackCanvasImage);
                if (pixelColor.equals(Color.BLACK)) {
                    blackPixels.add(new int[]{x, y});
                }
            }
        }
        return blackPixels;
    }


    public static void main(String[] args) {
        launch();
    }
}