package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;

import static it.unicam.cs.DebugData.PARSER_FILE_PATH;
import static it.unicam.cs.engine.util.Useful.getGameMap;
import static it.unicam.cs.gui.util.GuiTools.alignAll;
import static it.unicam.cs.gui.util.GuiTools.drawGameElements;

/**
 * Controller for the game scene
 * @see it.unicam.cs.gui.controller.SceneController
 */
public class GameSceneController extends SceneController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        try {
            getGameMap(PARSER_FILE_PATH).ifPresent(gameMap -> {
                Canvas[] canvases = gameMap.getCanvases();
                alignAll(anchorPane, canvases);
                drawGameElements(gameMap);
            });
        } catch (URISyntaxException | IOException e) {
            e.getCause();
        }
    }


}