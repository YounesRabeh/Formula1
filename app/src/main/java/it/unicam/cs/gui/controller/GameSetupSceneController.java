package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import it.unicam.cs.api.components.container.Resources;
import it.unicam.cs.api.components.container.UiGenerator;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.util.CanvasTools;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.unicam.cs.api.parser.types.AbstractParser.F1_MAP_FILE_EXTENSION;
import static it.unicam.cs.api.parser.types.AbstractParser.PARSER_SEPARATOR;
import static it.unicam.cs.engine.util.Useful.getGameMap;


/**
 * Controller class for the game setup scene.
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.8
 */
public class GameSetupSceneController extends SceneController {
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private Text driverNumberText;
    @FXML
    private Button backButton;
    @FXML
    private Button previousMapButton;
    @FXML
    private Button nextMapButton;
    @FXML
    private Button importMapButton;
    @FXML
    private Button startGameButton;
    @FXML
    private Button clearDriversButton;
    @FXML
    private Button deleteLastDriverButton;

    @FXML
    private Button addBotButton;
    @FXML
    private Button addPlayerButton;
    @FXML
    private VBox driversVBox;

    /** All the present maps files */
    private List<File> mapsFiles = new ArrayList<>();
    /** The map preview */
    ImageView mapView = new ImageView();
    /** The selected map */
    static GameMap currentGameMap;
    /** The layout listener, used to resize the map preview */
    ChangeListener<Bounds> layoutListener;
    /** The match making file, used to save the match making configuration */
    private File matchMakingFile;
    /** The list of added drivers */
    private final List<Driver> drivers = new ArrayList<>();
    /** The current number of drivers ion teh map **/
    int currentDriverNumber = 0;



    int currentPlayerNumber = 0;
    int currentBotNumber = 0;


    public void initialize() throws URISyntaxException, IOException {
        splitPane.setDividerPositions(0.65, 0.35);
        matchMakingFile = Resources.getResourceFile(MATCH_MAKING_FILE_PATH);
        mapsFiles = Resources.getAllFilesInDirectory(
                MAPS_DIRECTORY_PATH,
                F1_MAP_FILE_EXTENSION
        );
        initMapPreviewListener();
        Platform.runLater(this::initDriversSafeFile);
        selectedMap = selectedMap == null ? mapsFiles.getFirst() : selectedMap;
        loadMap(selectedMap);
    }

    /**
     * Load the map from the parsed file. It's a preview of the Track with the track markers.
     * <b>THE MRTHOD RENDERS THE PREVIEW AS AN IMAGE... STOPPAGE CODE, USE WITH CAUTION</b>
     * @param mapFile the map file
     */
    private void loadMap(File mapFile) {
        try {
            getGameMap(mapFile).ifPresent(gameMap -> {
                currentGameMap = gameMap;
                WritableImage trackImage = CanvasTools.createCanvasSnapshot(gameMap.getTrackCanvas());
                mapView.setImage(trackImage);
                mapView.setPreserveRatio(true);
            });
            Platform.runLater(this::updateUI);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the UI. Disable the buttons if the driver limit is reached. Update the driver number text.
     * Desable the driver entries if the limit is reached.
     */
    private void updateUI() {
        boolean isDriverLimitReached = currentDriverNumber >= currentGameMap.getMaxDriversNumber();
        if (currentDriverNumber == 0) {
            startGameButton.setDisable(true);
        }

        addBotButton.setDisable(isDriverLimitReached);
        addPlayerButton.setDisable(isDriverLimitReached);

        int maxDrivers = currentGameMap.getMaxDriversNumber();
        List<Node> driversVboxChildren = driversVBox.getChildren();

        for (int i = 0; i < driversVboxChildren.size(); i++) {
            boolean shouldDisable = i >= maxDrivers;
            driversVboxChildren.get(i).setDisable(shouldDisable);
        }

        updateDriverNumberText();
    }

    /**
     * Update the driver number text
     */
    private void updateDriverNumberText() {
        int maxDrivers = currentGameMap.getMaxDriversNumber();
        driverNumberText.setText(Math.min(currentDriverNumber, maxDrivers) + "/" + maxDrivers);
    }

    /**
     * Initialize the drivers from the match making file.
     */
    private void initDriversSafeFile(){
        if (matchMakingFile.length() == 0) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(matchMakingFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assert PARSER_SEPARATOR != null;
                String[] data = line.split(PARSER_SEPARATOR);
                if (data.length == 3) {
                    String name = data[1];
                    String color = data[2];
                    if (data[0].equals("P")) {
                        Player player = new Player(name);
                        player.setCarColor(Color.valueOf(color));
                        drivers.add(player);
                        currentPlayerNumber++;
                    } else {
                        Bot bot = new Bot(name);
                        bot.setCarColor(Color.valueOf(color));
                        drivers.add(bot);
                        currentBotNumber++;
                    }
                    currentDriverNumber++;
                    UiGenerator.addToVBOX(driversVBox, UiGenerator.createDriverEntry(drivers.getLast()));
                }
            }
        } catch (IOException e) {
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
     * Import a map from a file.
     */
    @FXML
    private void importMapButtonClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Map Files",
                        "*" + F1_MAP_FILE_EXTENSION
                )
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            mapsFiles.add(selectedFile);
            loadMap(selectedFile);
        }
    }
    
    /**
     * Go back to the welcome scene.
     */
    @FXML
    private void backButtonClick() {
        matchMakingFileWrite();
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
        selectedMap = mapsFiles.get(currentMapIndex);
        loadMap(selectedMap);
    }

    /**
     * Load the previous map.
     */
    @FXML
    private void previousMapButtonClick() {
        currentMapIndex = (currentMapIndex - 1 + mapsFiles.size()) % mapsFiles.size();
        selectedMap = mapsFiles.get(currentMapIndex);
        loadMap(selectedMap);
    }

    @FXML
    private void addBotButtonClick() {
        if (currentDriverNumber < currentGameMap.getMaxDriversNumber()) {
            startGameButton.setDisable(false);
            String name = "Bot " + (currentBotNumber + 1);
            Bot bot = new Bot(name);
            drivers.add(bot);
            currentDriverNumber++; currentBotNumber++;
            UiGenerator.addToVBOX(driversVBox, UiGenerator.createDriverEntry(bot));
            updateUI();
        }
    }

    @FXML
    private void addPlayerButtonClick() {
        if (currentDriverNumber < currentGameMap.getMaxDriversNumber()) {
            startGameButton.setDisable(false);
            String name = "Player " + (currentPlayerNumber + 1);
            Player player = new Player(name);
            drivers.add(player);
            currentDriverNumber++; currentPlayerNumber++;
            UiGenerator.addToVBOX(driversVBox, UiGenerator.createDriverEntry(player));
            updateUI();
        }
    }

    @FXML
    private void startGameButtonClick() {
        if (drivers.isEmpty()) {
            alertPopup(Alert.AlertType.WARNING, "Game Start Error",
                    "No Drivers Available", "Please add drivers before starting the game.");
            return; // Exit the method to prevent further execution
        }

        Set<String> driverNames = new HashSet<>();
        for (Driver driver : drivers) {
            if (!driverNames.add(driver.getName())) {
                alertPopup(Alert.AlertType.WARNING, "Game Start Error",
                        "Duplicate Driver Name", "Driver name '" + driver.getName() + "' is duplicated. " +
                                "Please fix this issue.");
                return; // Exit the method to prevent further execution
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(matchMakingFile, false))) {
            int cap = Math.min(currentGameMap.getMaxDriversNumber(), drivers.size());
            for (int i = 0; i < cap; i++) {
                Driver driver = drivers.get(i);
                if (driver instanceof Player) {
                    writer.write("P" + PARSER_SEPARATOR);
                } else {
                    writer.write("B" + PARSER_SEPARATOR);
                }
                writer.write(driver.getName() + PARSER_SEPARATOR + driver.getCarColor().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedMap = mapsFiles.get(currentMapIndex);
        changeScene(GAME_SCENE_FXML);
    }

    /**
     * Show an alert popup.
     * @param type the alert type
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     */
    private void alertPopup(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void clearDriversButtonClick(){
        drivers.clear();
        driversVBox.getChildren().clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(matchMakingFile, false))) {
            // Truncate the file by opening it in write mode without writing anything
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentDriverNumber = 0;
        currentBotNumber = 0;
        currentPlayerNumber = 0;
        initDriversSafeFile();

        updateUI();
    }

    @FXML
    private void deleteLastDriverButtonClick(){
        if (drivers.isEmpty()) {
            return;
        }
        drivers.removeLast();
        driversVBox.getChildren().removeLast();
        currentDriverNumber--;
        if (drivers.getLast() instanceof Player) {
            currentPlayerNumber--;
        } else {
            currentBotNumber--;
        }
        matchMakingFileWrite();
        updateUI();
    }

    /**
     * Write the match making file.
     */
    private void matchMakingFileWrite() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(matchMakingFile, false))) {
            for (Driver driver : drivers) {
                if (driver instanceof Player) {
                    writer.write("P" + PARSER_SEPARATOR);
                } else {
                    writer.write("B" + PARSER_SEPARATOR);
                }
                writer.write(driver.getName() + PARSER_SEPARATOR + driver.getCarColor().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
