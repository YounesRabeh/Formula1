package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;

import static it.unicam.cs.engine.util.Useful.getGameMap;
import static it.unicam.cs.gui.util.GuiTools.*;

/**
 * Controller for the game scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0
 */
public class GameSceneController extends SceneController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        try {
            getGameMap(selectedMap).ifPresent(gameMap -> {
                Group canvases = gameMap.getCanvasGroup();
                align(anchorPane, canvases);
                //drawGameElements(gameMap);
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}