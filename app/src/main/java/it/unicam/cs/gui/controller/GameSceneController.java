package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.structs.Movement;
import it.unicam.cs.engine.manager.GameManager;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.util.GuiTools;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

import static it.unicam.cs.engine.util.Useful.getGameMap;


/**
 * Controller for the game scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.3
 */
public class GameSceneController extends SceneController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane mapArea;  //NOTE: shouldn't be static
    @FXML
    private GridPane commandGridPane;
    @FXML
    private Button abandonButton;
    @FXML
    private Button upRightButton;
    @FXML
    private Button upButton;
    @FXML
    private Button upLeftButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button centerButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button downLeftButton;
    @FXML
    private Button downButton;
    @FXML
    private Button downRightButton;

    public static Button[] commandButtons;

    private static List<Driver> drivers;

    private GameMap currentGameMap;


    private static Thread uiUpdater;


    @FXML
    public void initialize() {
        commandButtons = new Button[]{
                upRightButton,
                upButton,
                upLeftButton,
                rightButton,
                centerButton,
                leftButton,
                downLeftButton,
                downButton,
                downRightButton
        };

        try {
            getGameMap(selectedMap).ifPresent(gameMap -> {
                this.currentGameMap = gameMap;
                drivers = gameMap.getDrivers();
                putDriversOnTrack(gameMap);
                printInitInfo();

                GameManager.getInstance(gameMap, mapArea);
                //GameManager.endGame(); //ERROR proofing
                GuiTools.drawDriversOnTrack(gameMap);
                GuiTools.drawGameElements(gameMap);

                //NOTE: THE FOLLOWING CODE SHOULD BE MOVED TO GameManager if possible
                Platform.runLater(() -> {
                    GameManager.initRound();
                    GuiTools.mapUpdate(gameMap, mapArea);
                });

            });
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Puts the drivers on the track, at the starting positions
     * @param gameMap the game map
     */
    private void putDriversOnTrack(GameMap gameMap) {
        List<GameMap.Waypoint> startingPositions = gameMap.getStartingPosition();
        if (startingPositions.size() < drivers.size()) {
            throw new IllegalStateException("Not enough starting positions for all drivers.");
        }

        drivers.stream()
                .limit(startingPositions.size())
                .forEach(driver -> {
                    int index = drivers.indexOf(driver);
                    driver.move(startingPositions.get(index));
                });
    }

    /**
     * Handles the player movement, initiated by the player clicking on a command button
     * @param movement the movement
     */
    private void handlePlayerMove(Movement movement) {
        Driver currentDriver = null;
        try {
            currentDriver = GameManager.getCurrentDriver();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println("No more drivers to move");
        }
        if (currentDriver instanceof Bot) {
            return;
        }

        //currentDriver.incrementInertia(movement);
        GameMap.Waypoint[] possibleNextWaypoints =
                RouteFinder.getPossibleNextWaypoints(currentGameMap, currentDriver);
        GameMap.Waypoint chosenWaypoint = possibleNextWaypoints[movement.ordinal()];

        if (chosenWaypoint != null) {
            currentDriver.move(chosenWaypoint);
            GuiTools.mapUpdate(currentGameMap, mapArea);
        }
        GameManager.continueRound();
    }



    @FXML
    private void abandonButtonClick() {
        GameManager.endGame();
        System.out.println("######## GAME ABANDONED ########");

        changeScene(GAME_SETUP_SCENE_FXML);
    }

    @FXML
    private void upRightButtonClick() {
        handlePlayerMove(Movement.UP_RIGHT); // Button for moving up-right
    }

    @FXML
    private void upButtonClick() {
        handlePlayerMove(Movement.UP); // Button for moving up
    }

    @FXML
    private void upLeftButtonClick() {
        handlePlayerMove(Movement.UP_LEFT); // Button for moving up-left
    }

    @FXML
    private void rightButtonClick() {
        handlePlayerMove(Movement.RIGHT); // Button for moving right
    }

    @FXML
    private void centerButtonClick() {
        handlePlayerMove(Movement.CENTER); // Button for staying in the center
    }

    @FXML
    private void leftButtonClick() {
        handlePlayerMove(Movement.LEFT); // Button for moving left
    }

    @FXML
    private void downLeftButtonClick() {
        handlePlayerMove(Movement.DOWN_LEFT); // Button for moving down-left
    }

    @FXML
    private void downButtonClick() {
        handlePlayerMove(Movement.DOWN); // Button for moving down
    }

    @FXML
    private void downRightButtonClick() {
        handlePlayerMove(Movement.DOWN_RIGHT); // Button for moving down-right
    }

    /**
     * Prints the initial information about the game
     */
    private void printInitInfo(){
        System.out.println("########### MAP LOADED ###########");
        System.out.println("The Drivers:\n" + currentGameMap.getDrivers());
        System.out.println("######### THE RACE IS UP #########");
    }
}