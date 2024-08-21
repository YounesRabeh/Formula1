package it.unicam.cs.gui.controller;

import it.unicam.cs.api.parser.types.DrawingParser;
import it.unicam.cs.gui.map.GameMap;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static it.unicam.cs.DebugData.PARSER_FILE_PATH;
import static it.unicam.cs.api.parser.Information.F1_MAP_FILE_EXTENSION;

import static it.unicam.cs.api.components.container.Resources.getResourceFile;
import static it.unicam.cs.engine.util.Useful.alignAll;
import static it.unicam.cs.gui.util.GuiTools.drawGameElements;

public class GameSceneController extends SceneController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        try {
            getGameMap().ifPresent(gameMap -> {
                Canvas[] canvases = gameMap.getCanvases();
                alignAll(anchorPane, Pos.CENTER_LEFT, canvases);
                drawGameElements(gameMap);
            });
        } catch (URISyntaxException | IOException e) {
            e.getCause();
        }
    }

    /**
     * Get the game map from the corresponding file. {@link it.unicam.cs.DebugData#PARSER_FILE_PATH}
     * @return the game map
     * @throws URISyntaxException if the URI syntax is incorrect
     * @throws IOException if an I/O error occurs
     */
    private Optional<GameMap> getGameMap() throws URISyntaxException, IOException {
        DrawingParser parser = new DrawingParser(
                getResourceFile(PARSER_FILE_PATH),
                F1_MAP_FILE_EXTENSION
        );
        return parser.start();
    }
}