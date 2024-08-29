package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.container.Resources;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static it.unicam.cs.DebugData.APP_WINDOW_TITLE;
import static it.unicam.cs.DebugData.F1_APP_ICONS_FOLDER;

/**
 * Abstract class for the scene controller.
 * The scene controller is responsible for setting up the stage and the scene of the application.
 * @see it.unicam.cs.App
 * @author Younes Rabeh
 * @version 1.3
 */
public abstract class SceneController {
    /** The stage of the application. */
    private static Stage stage;
    /** The width of the stage. */
    private static double WIDTH;
    /** The height of the stage. */
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
                screenDimensionsSetup();
                stageSetup(stage);
                setScene(fxmlFile);
            } catch (URISyntaxException | IOException e) {
                e.getCause();
            }
        } else { //TODO: add the custom resources & app logic exceptions
            throw new IllegalStateException("The stage is already set");
        }
    }

    /**
     * Set the stage of the application. The application is maximized by default.
     * Also listens for the window maximization/minimization, and set the window icon.
     * @param stage the stage of the application
     * @throws URISyntaxException if the URI syntax is incorrect
     * @throws IOException if the file cannot be read
     */
    protected static void stageSetup(Stage stage) throws URISyntaxException, IOException {
        stage.setTitle(APP_WINDOW_TITLE);
        stage.getIcons().addAll(Resources.getIcons(F1_APP_ICONS_FOLDER));
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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

        stage.setScene(scene);
        Platform.runLater(() -> {
            stage.setFullScreen(true);
            stage.show();
        });
    }

    /**
     * Set up the screen dimensions. {@link SceneController#WIDTH} and {@link SceneController#HEIGHT}
     * Used to make sure that all screens are maximized.
     * <b>This must be called before setting the stage.</b>
     */
    private static void screenDimensionsSetup() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        WIDTH = screenBounds.getWidth();
        HEIGHT = screenBounds.getHeight();
    }

    /**
     * Get the stage of the application.
     * @return the stage of the application
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Get the width of the stage.
     * @return the width of the stage
     */
    public static double getWidth() {
        return WIDTH;
    }

    /**
     * Get the height of the stage.
     * @return the height of the stage
     */
    public static double getHeight() {
        return HEIGHT;
    }
}