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


    private static double WIDTH;
    private static double HEIGHT;

    /**
     * Initialize the application (The opening scene)
     * @param stage the stage of the application
     * @param fxmlFile the FXML file of the scene
     */
    public static void init(Stage stage, URL fxmlFile) {
        if (SceneController.stage == null) {
            try { //TODO: add the splash screen and resources loading
                System.gc();
                setScreenDimensions();
                setStage(stage);
                setScene(fxmlFile);
            } catch (IOException e) {
                e.getCause();
            }
        } else { //TODO: add the custom resources & app logic exceptions
            throw new IllegalStateException("The stage is already set");
        }
    }

    private static void setScreenDimensions() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        WIDTH = screenBounds.getWidth();
        HEIGHT = screenBounds.getHeight();
    }

    /**
     * Set the stage of the application.
     * @param stage the stage of the application
     */
    protected static void setStage(Stage stage) {
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle(APP_WINDOW_TITLE);
        SceneController.stage = stage;
    }

    /**
     * Set the scene of the application.
     * @param fxmlFile the FXML file
     * @throws IOException if the FXML file is not found
     */
    protected static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        SceneController.stage.setMaximized(true);
        SceneController.stage.setScene(scene);
        SceneController.stage.show();
    }

    /**
     * Get the stage of the application.
     * @return the stage of the application
     */
    public static Stage getStage() {
        return stage;
    }
}