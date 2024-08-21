package it.unicam.cs.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static it.unicam.cs.DebugData.APP_WINDOW_TITLE;

public abstract class SceneController {
    /** The stage of the application. */
    private static Stage stage;
    /** The width of the screen. */
    private static double WIDTH;
    /** The height of the screen. */
    private static double HEIGHT;

    private static double MIN_WIDTH;

    private static double MIN_HEIGHT;

    private static final double MIN_SIZE_FACTOR = 0.75;

    /**
     * Initialize the application (The opening scene)
     * @param stage the stage of the application
     * @param fxmlFile the FXML file of the scene
     */
    public static void init(Stage stage, URL fxmlFile) {
        if (SceneController.stage == null) {
            try { //TODO: add the splash screen and resources loading
                System.gc();
                screenDimensionsSetup();
                setStage(stage);
                setScene(fxmlFile);
            } catch (IOException e) {
                e.getCause();
            }
        } else { //TODO: add the custom resources & app logic exceptions
            throw new IllegalStateException("The stage is already set");
        }
    }

    /**
     * Set up the screen dimensions. {@link SceneController#WIDTH} and {@link SceneController#HEIGHT}
     * Used to make sure that all screens are maximized.
     */
    private static void screenDimensionsSetup() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        WIDTH = screenBounds.getWidth();
        HEIGHT = screenBounds.getHeight();
        MIN_WIDTH = WIDTH * MIN_SIZE_FACTOR;
        MIN_HEIGHT = HEIGHT * MIN_SIZE_FACTOR;
    }

    /**
     * Set the stage of the application. The application is maximized by default.
     * @param stage the stage of the application
     */
    protected static void setStage(Stage stage) {
        stage.setTitle(APP_WINDOW_TITLE);
        stage.setMaximized(true);
        SceneController.stage = stage;
    }

    /**
     * Set the scene of the application.
     * @param fxmlFile the FXML file
     * @throws IOException if the FXML file is not found
     */
    protected static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        Scene scene;
        if (stage.isMaximized()) {
            scene = new Scene(loader.load(), WIDTH, HEIGHT);
        } else {
            scene = new Scene(loader.load(), MIN_WIDTH, MIN_HEIGHT);
        }

        stage.setScene(scene);
        stage.show();

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stage.setWidth(WIDTH);
                stage.setHeight(HEIGHT);
            } else {
                stage.setWidth(MIN_WIDTH);
                stage.setHeight(MIN_HEIGHT);
            }
        });
    }

    /**
     * Get the stage of the application.
     * @return the stage of the application
     */
    public static Stage getStage() {
        return stage;
    }
}