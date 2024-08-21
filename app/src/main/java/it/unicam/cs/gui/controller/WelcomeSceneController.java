package it.unicam.cs.gui.controller;

import it.unicam.cs.DebugData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;

public class WelcomeSceneController {
    @FXML
    private Button quitButton;

    @FXML
    private Button localButton;


    //@FXML
    //public void initialize() {
    //    Stage stage = (Stage) localButton.getScene().getWindow();
    //    sceneController = new SceneController(stage);
    //}

    /**
     * Handles the quit button click event, closing the application.
     */
    @FXML
    protected void quitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void localButtonClick() {
        try {
            SceneController.setScene(getResourceURL(DebugData.GAME_SCENE_FXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
