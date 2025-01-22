package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.container.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.api.parser.types.AbstractParser.F1_MAP_FILE_EXTENSION;
import static it.unicam.cs.engine.util.Useful.getGameMap;

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

    private List<File> mapsFiles = new ArrayList<>();

    public void initialize() throws URISyntaxException, IOException {
        mapsFiles = Resources.getAllFilesInDirectory(
                MAPS_DIRECTORY_PATH,
                F1_MAP_FILE_EXTENSION
        );
        System.out.println(mapsFiles);
        selectedMap = mapsFiles.get(0);
        loadMap(selectedMap);
    }

    private void loadMap(File mapFile) {
        try {
            getGameMap(mapFile).ifPresent(gameMap -> {
                WritableImage trackImage = gameMap.getTrackCanvas().getTrackSnapshot();
                ImageView mapView = new ImageView(trackImage);

                mapView.setPreserveRatio(true);

                //layoutBounds
                canvasPane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                    mapView.fitWidthProperty().bind(canvasPane.widthProperty());
                    mapView.fitHeightProperty().bind(canvasPane.heightProperty());

                    if (!canvasPane.getChildren().contains(mapView)) {
                        canvasPane.getChildren().add(mapView);
                    }
                });
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void backButtonClick() {
        changeScene(WELCOME_SCENE_FXML);
    }
}
