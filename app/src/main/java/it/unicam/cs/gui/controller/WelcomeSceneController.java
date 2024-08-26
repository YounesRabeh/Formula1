package it.unicam.cs.gui.controller;

import it.unicam.cs.DebugData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;

/**
 * Controller for the welcome scene
 * @see it.unicam.cs.gui.controller.SceneController
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
        //TODO: add the background checks to close without corrupting the data
        System.exit(0);
    }

    /**
     * switch to the local game scene
     */
    @FXML
    protected void localButtonClick() {
        try {
           setScene(getResourceURL(DebugData.GAME_SCENE_FXML));
        } catch (IOException e) {
            e.getCause();
        }
    }

    /**
     * switch to the network game scene
     */
    @FXML
    protected void networkButtonClick() {
        try { //FIXME: Change this
            setScene(getResourceURL(DebugData.MAP_EDITOR_SCENE_FXML));
        } catch (IOException e) {
            e.getCause();
        }
    }
}
