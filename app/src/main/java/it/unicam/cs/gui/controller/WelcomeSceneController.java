package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Controller for the welcome scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0

 */
public class WelcomeSceneController extends SceneController {
    @FXML
    private Button playButton;
    @FXML
    private Button createMapButton;
    @FXML
    private Button quitButton;

    /**
     * Quit the application
     */
    @FXML
    private void quitButtonClick() {
        System.exit(0);
    }

    /**
     * Switch to the local game scene
     */
    @FXML
    private void playButtonClick() {
        changeScene(GAME_SETUP_SCENE_FXML);
    }

    /**
     * Switch to the network game scene
     */
    @FXML
    private void createMapButtonClick() {
        changeScene(MAP_EDITOR_SCENE_FXML);
    }
}
