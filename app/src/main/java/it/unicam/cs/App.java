package it.unicam.cs;

import it.unicam.cs.api.components.nodes.Waypoint;
import it.unicam.cs.engine.core.routes.RouteTools;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.map.GridCanvas;
import it.unicam.cs.gui.map.InertCanvas;
import it.unicam.cs.gui.map.TrackCanvas;
import it.unicam.cs.gui.util.CanvasTools;
import it.unicam.cs.api.parser.DrawingParser;
import it.unicam.cs.api.components.container.Graphics;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.io.Serial;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

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


        String PATH = "/it/unicam/cs/test.txt";
        // NOTE: to get gradle to work, you need to put the file in the resources folder
        String absolutePath = Objects.requireNonNull(
                getClass().getResource(PATH)).toURI().getPath();
        File parserFile = new File(absolutePath);
        // use classLoader
        DrawingParser parser = new DrawingParser(parserFile);
        GameMap gameMap = parser.start().get();

        Canvas[] canvases = gameMap.getCanvases();

        // Create a layout pane to hold the canvas
        StackPane root = new StackPane();
        root.setBackground(Background.fill(Color.rgb(0 ,200, 0)));

        // Align the canvases to the left side
        alignAll(Pos.CENTER_LEFT, canvases);
        root.getChildren().addAll(canvases);

        // Create a scene with the layout pane
        Scene scene = new Scene(root, 1000, 1000);

        // Set up the stage and show it
        stage.setTitle("Gagata ");
        stage.setScene(scene);
        stage.show();
        WritableImage trackCanvasSnapshot = gameMap.getTrackCanvas().getCanvasSnapshot();

        // - 3 to get @Waypoints canvas
        printWaypoints(canvases[canvases.length-3].getGraphicsContext2D(), exe(gameMap));

    }

    private List<Waypoint> exe(GameMap gameMap){
        long startTime = System.nanoTime();
        List<Waypoint> waypoints = RouteTools.getGameMapWaypoints(gameMap);
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " nanoseconds " +
                "or " + (float) (endTime - startTime) / 1000000 + " milliseconds\n" +
                "> Found " + waypoints.size() + " black pixels"
        );
        return waypoints;
    }

    private void printWaypoints(GraphicsContext gc, List<Waypoint> waypoints) {
        Graphics.setFill(gc, new int[]{255, 0, 0});
        for (Waypoint coords : waypoints) {
            System.out.printf("Black pixel found at (%d, %d)\n", (int) coords.getX(), (int) coords.getY());
            Graphics.strokePoint(gc, new int[]{(int) coords.getX(), (int) coords.getY()});
        }
    }

    private void alignAll(Pos pos, Node[] nodes){
        for (Node node : nodes) {
            StackPane.setAlignment(node, pos);
        }
    }



    public static void main(String[] args) {
        launch();
    }
}