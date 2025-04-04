package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.container.Resources;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;
import static it.unicam.cs.api.parser.types.PropertiesParser.CONFIG_PROPERTIES_PATH;
import static it.unicam.cs.api.parser.types.PropertiesParser.getProperty;

/**
 * Abstract class for the scene controller.
 * The scene controller is responsible for setting up the stage and the scene of the application.
 * @see it.unicam.cs.App
 * @author Younes Rabeh
 * @version 1.4
 */
public abstract class SceneController {
    /** The stage of the application. */
    private static Stage stage;
    /** The width of the stage. */
    private static double WIDTH;
    /** The height of the stage. */
    private static double HEIGHT;
    /** The selected map file. */
    protected static File selectedMap;
    /** The app window title. */
    public static final String APP_WINDOW_TITLE = getProperty(
            CONFIG_PROPERTIES_PATH, "APP_WINDOW_TITLE"
    );
    /** The app icons folder. */
    public static final String F1_APP_ICONS_FOLDER = getProperty(
            CONFIG_PROPERTIES_PATH, "F1_APP_ICONS_FOLDER"
    );
    /** The welcome scene FXML file. */
    protected static final String WELCOME_SCENE_FXML = getProperty(
            CONFIG_PROPERTIES_PATH, "WELCOME_SCENE_FXML"
    );
    /** The map editor scene FXML file. */
    protected static final String MAP_EDITOR_SCENE_FXML = getProperty(
            CONFIG_PROPERTIES_PATH, "MAP_EDITOR_SCENE_FXML"
    );
    /** The game scene FXML file. */
    protected static final String GAME_SCENE_FXML = getProperty(
            CONFIG_PROPERTIES_PATH, "GAME_SCENE_FXML"
    );
    /** The game setup scene FXML file. */
    protected static final String GAME_SETUP_SCENE_FXML = getProperty(
            CONFIG_PROPERTIES_PATH, "GAME_SETUP_SCENE_FXML"
    );
    /** The maps directory path. */
    protected static final String MAPS_DIRECTORY_PATH = getProperty(
            CONFIG_PROPERTIES_PATH, "MAPS_DIRECTORY_PATH"
    );
    /** The match making file path. */
    protected static final String MATCH_MAKING_FILE_PATH = getProperty(
            CONFIG_PROPERTIES_PATH, "MATCH_MAKING_FILE_PATH"
    );

    public static final String NEW_MAP_FILE_PATH = getProperty(
            CONFIG_PROPERTIES_PATH, "EMPTY_MAP_FILE_PATH"
    );


    /**
     * Initialize the application (The opening scene)
     * @param stage the stage of the application
     */
    public static void init(Stage stage) {
        if (SceneController.stage == null) {
            try {
                System.gc();
                screenDimensionsSetup();
                stageSetup(stage);
                setScene(getResourceURL(WELCOME_SCENE_FXML));
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        } else {
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
    protected synchronized static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);

        if (stage.getScene() == null) {
            stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
        } else {
            stage.getScene().setRoot(loader.load());
        }

        Platform.runLater(() -> stage.show());
    }

    /**
     * Helper method to handle scene changes
     * @param fxmlFilePath the path to the FXML file
     */
    protected synchronized void changeScene(String fxmlFilePath) {
        Platform.runLater(() -> {
            try {
                setScene(getResourceURL(fxmlFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    protected static Stage getStage() {
        return stage;
    }

    /**
     * Get the width of the stage.
     * @return the width of the stage
     */
    protected static double getWidth() {
        return WIDTH;
    }

    /**
     * Get the height of the stage.
     * @return the height of the stage
     */
    protected static double getHeight() {
        return HEIGHT;
    }
}