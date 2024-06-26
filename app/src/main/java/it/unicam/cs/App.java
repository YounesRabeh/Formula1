package it.unicam.cs;

import it.unicam.cs.gui.tools.CanvasTools;
import it.unicam.cs.parser.DrawingParser;
import it.unicam.cs.gui.map.MapRenderer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // Create a Canvas
        Canvas trackCanvas = new Canvas(800, 800);
        Canvas gridCanvas = new Canvas(800, 800);

        // Get the GraphicsContext of the canvas
        GraphicsContext track_gc = trackCanvas.getGraphicsContext2D();
        GraphicsContext grid_gc = gridCanvas.getGraphicsContext2D();


        // Draw something on the canvas

        MapRenderer.drawGrid(gridCanvas, 24, Color.GRAY);
        CanvasTools.drawOutline(gridCanvas, Color.GRAY);
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

        // Get the color of a specific pixel on mouse click (example)
        // get the color of the track pixel on mouse click
        gridCanvas.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            Color pixelColor = CanvasTools.getPixelColor(trackCanvas, x, y);
            System.out.println("Pixel color at (" + x + ", " + y + "): " +
                    CanvasTools.colorToRGBString(pixelColor));
        });
    }

    private void drawCircuit(GraphicsContext gc) throws IOException {
        // Clear the canvas
        gc.clearRect(0, 0, 900, 900);

        String PATH = "app/src/main/resources/it/unicam/cs/test.txt";
        // use classLoader
        DrawingParser parser = new DrawingParser(new File(PATH), gc);
        parser.start();
        System.out.println(parser.getAllIdentifiers());
    }



    public static void main(String[] args) {
        launch();
    }
}