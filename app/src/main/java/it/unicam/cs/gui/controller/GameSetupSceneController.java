package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.container.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import static it.unicam.cs.api.parser.types.AbstractParser.F1_MAP_FILE_EXTENSION;

/**
 * Controller class for the game setup scene.
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0
 */
public class GameSetupSceneController extends SceneController {
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private Button backButton;

    private Collection<File> mapsFiles = new ArrayList<>();

    public void initialize() throws URISyntaxException, IOException {
        mapsFiles = Resources.getAllFilesInDirectory(
                "it/unicam/cs/data/maps/primary/",
                F1_MAP_FILE_EXTENSION
        );
        System.out.println(mapsFiles);


    }

    private void loadMap(File mapFile) {

    }

    @FXML
    private void backButtonClick() {
        changeScene(WELCOME_SCENE_FXML);
    }
}
