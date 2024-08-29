package it.unicam.cs.gui.controller;

import it.unicam.cs.DebugData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;

/**
 * Controller for the welcome scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0

 */
public class WelcomeSceneController extends SceneController {
    @FXML
    private Button localButton;
    @FXML
    private Button networkButton;
    @FXML
    private Button quitButton;

    /**
     * Quit the application
     */
    @FXML
    protected void quitButtonClick() {
        // TODO: add the background checks to close without corrupting the data
        System.exit(0);
    }

    /**
     * Switch to the local game scene
     */
    @FXML
    protected void localButtonClick() {
        changeScene(DebugData.GAME_SCENE_FXML);
    }

    /**
     * Switch to the network game scene
     */
    @FXML
    protected void networkButtonClick() {
        changeScene(DebugData.MAP_EDITOR_SCENE_FXML);
    }

    /**
     * Helper method to handle scene changes
     * @param fxmlFilePath the path to the FXML file
     */
    private synchronized void changeScene(String fxmlFilePath) {
        Platform.runLater(() -> {
            try {
                setScene(getResourceURL(fxmlFilePath));
            } catch (IOException e) {
                e.getCause();
            }
        });
    }
}
