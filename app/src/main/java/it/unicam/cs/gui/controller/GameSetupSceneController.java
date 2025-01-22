package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.container.Resources;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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
    @FXML
    private Button previousMapButton;
    @FXML
    private Button nextMapButton;

    /** All the present maps files */
    private List<File> mapsFiles = new ArrayList<>();
    /** The map preview */
    ImageView mapView = new ImageView();
    /** The layout listener, used to resize the map preview */
    ChangeListener<Bounds> layoutListener;


    public void initialize() throws URISyntaxException, IOException {
        mapsFiles = Resources.getAllFilesInDirectory(
                MAPS_DIRECTORY_PATH,
                F1_MAP_FILE_EXTENSION
        );
        initMapPreviewListener();
        loadMap(mapsFiles.getFirst());
    }

    /**
     * Load the map from the parsed file. It's a preview of the Track with the track markers.
     * <b>THE MRTHOD RENDERS THE PREVIEW AS AN IMAGE... STOPPAGE CODE, USE WITH CAUTION</b>
     * @param mapFile the map file
     */
    private void loadMap(File mapFile) {
        try {
            getGameMap(mapFile).ifPresent(gameMap -> {
                WritableImage trackImage = CanvasTools.createCanvasSnapshot(gameMap.getTrackCanvas());
                mapView.setImage(trackImage);
                mapView.setPreserveRatio(true);
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the map preview listener.
     */
    private void initMapPreviewListener(){
        layoutListener = (observable, oldValue, newValue) -> {
            mapView.fitWidthProperty().bind(canvasPane.widthProperty());
            mapView.fitHeightProperty().bind(canvasPane.heightProperty());

            if (!canvasPane.getChildren().contains(mapView)) {
                canvasPane.getChildren().add(mapView);
            }
        };
        canvasPane.layoutBoundsProperty().addListener(layoutListener);
    }


    /**
     * Go back to the welcome scene.
     */
    @FXML
    private void backButtonClick() {
        changeScene(WELCOME_SCENE_FXML);
    }

    /** internal counter to keep track of the current map index */
    private int currentMapIndex = 0;
    /**
     * Load the next map.
     */
    @FXML
    private void nextMapButtonClick() {
        currentMapIndex = (currentMapIndex + 1) % mapsFiles.size();
        loadMap(mapsFiles.get(currentMapIndex));
    }

    /**
     * Load the previous map.
     */
    @FXML
    private void previousMapButtonClick() {
        currentMapIndex = (currentMapIndex - 1 + mapsFiles.size()) % mapsFiles.size();
        loadMap(mapsFiles.get(currentMapIndex));
    }
}
