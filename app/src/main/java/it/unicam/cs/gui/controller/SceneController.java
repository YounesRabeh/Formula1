package it.unicam.cs.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static it.unicam.cs.DebugData.APP_WINDOW_TITLE;

public final class SceneController {
    /** The stage of the application. */
    private static Stage stage;

    /** To prevent instantiation. */
    private SceneController() {}

    /**
     * Set the stage of the application.
     * @param stage the stage of the application
     */
    public static void setStage(Stage stage) {
        SceneController.stage = stage;
        SceneController.stage.setMaximized(true);
        SceneController.stage.setResizable(false);
        SceneController.stage.setTitle(APP_WINDOW_TITLE);
    }

    /**
     * Set the scene of the application.
     * @param fxmlFile the FXML file
     * @throws IOException if the FXML file is not found
     */
    public static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        Scene scene = new Scene(loader.load());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Get the stage of the application.
     * @return the stage of the application
     */
    public static Stage getStage() {
        return stage;
    }
}