package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class WelcomeSceneController {
    @FXML
    private Button quitButton;



    /**
     * Handles the quit button click event, closing the application.
     */
    @FXML
    protected void quitButtonClick() {
        System.exit(0);
    }

}
