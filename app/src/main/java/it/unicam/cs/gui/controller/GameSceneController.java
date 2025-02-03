package it.unicam.cs.gui.controller;

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

import static it.unicam.cs.engine.util.Useful.getGameMap;


/**
 * Controller for the game scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.2
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

    private static Button[] commandButtons;

    private static List<Driver> drivers;

    private GameMap currentGameMap;

    private static GameManager GAME_MANAGER;

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

                GAME_MANAGER = GameManager.getInstance(gameMap);
                GAME_MANAGER.addAllDrivers(drivers);
                GuiTools.drawDriversOnTrack(gameMap);


                Platform.runLater(() -> {
                    GameManager.initRound();
                    uiUpdate(gameMap, mapArea);
                });

            });
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void uiUpdate(GameMap gameMap, AnchorPane mapArea) {
        GuiTools.updateCommandButtons(drivers.getFirst(), gameMap, commandButtons);
        GuiTools.draw(mapArea, gameMap, drivers.getFirst());
        GuiTools.align(mapArea, gameMap.getCanvasGroup());

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
                    driver.setPosition(startingPositions.get(index));
                });
    }


    //NOTE: works only for the first driver
    private void handlePlayerMove(Movement movement) {
        Driver currentDriver;

        try {
            currentDriver = GameManager.next();
        } catch (IllegalStateException e) {
            return;
        }

        if (currentDriver == null) {
            GameManager.initRound();
            currentDriver = GameManager.next();
        }
        assert currentDriver != null;
        //currentDriver.incrementInertia(movement);
        GameMap.Waypoint[] possibleNextWaypoints =
                RouteFinder.getPossibleNextWaypoints(currentGameMap, currentDriver);
        GameMap.Waypoint chosenWaypoint = possibleNextWaypoints[movement.ordinal()];

        if (chosenWaypoint != null) {
            currentDriver.setPosition(chosenWaypoint);
            uiUpdate(currentGameMap, mapArea);
        }
    }

    @FXML
    private void abandonButtonClick() {
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

}