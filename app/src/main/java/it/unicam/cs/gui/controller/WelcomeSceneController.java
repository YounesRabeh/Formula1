package it.unicam.cs.gui.controller;

import it.unicam.cs.DebugData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;

public class WelcomeSceneController {
    @FXML
    private Button localButton;
    @FXML
    private Button networkButton;
    @FXML
    private Button quitButton;


    @FXML
    protected void quitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void localButtonClick() {
        try {
            SceneController.setScene(getResourceURL(DebugData.GAME_SCENE_FXML));
        } catch (IOException e) {
            e.getCause();
        }
    }
}
