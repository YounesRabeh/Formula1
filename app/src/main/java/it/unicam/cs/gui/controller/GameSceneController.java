package it.unicam.cs.gui.controller;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.engine.manager.GameManager;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.engine.util.Useful;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.util.GuiTools;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static it.unicam.cs.engine.util.Useful.getGameMap;
import static it.unicam.cs.gui.util.GuiTools.align;


/**
 * Controller for the game scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.1
 */
public class GameSceneController extends SceneController {
    @FXML
    private AnchorPane anchorPane;
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

    private Button[] commandButtons;

    private List<Driver> drivers;

    private static GameManager GAME_MANAGER;

    //FIXME: the extra field are
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
                drivers = gameMap.getDrivers();
                Driver driver = drivers.get(0);
                putDriversOnTrack(gameMap);
                GAME_MANAGER = GameManager.getInstance(gameMap);
                GAME_MANAGER.addAllDrivers(drivers);
                Platform.runLater(GAME_MANAGER::execute);

                GameMap.Waypoint[] possibleNextWaypoints =
                        RouteFinder.getPossibleNextWaypoints(
                                gameMap,
                                driver
                        );

                Group canvasGroup = gameMap.getCanvasGroup();
                Canvas[] canvases = gameMap.getCanvases();
                //Useful.drawWaypoints(
                //        canvases[WAYPOINT_LVL].getGraphicsContext2D(),
                //        possibleNextWaypoints
                //);
                GuiTools.drawDriversOnTrack(gameMap);
                System.out.println("Driver name:" + driver.getName());
                System.out.println("Driver position:" + driver.getPosition());
                System.out.println("Possible position:" + Arrays.toString(possibleNextWaypoints));

                Collection<GameMap.Waypoint> targets = gameMap.getFinishLine().getWaypoints();
                GameMap.Waypoint bestTarget = RouteFinder.getBestTarget(
                        driver.getPosition(),
                        targets
                );
                //Useful.drawWaypoint(
                //        canvases[WAYPOINT_LVL].getGraphicsContext2D(),
                //        bestTarget
                //);
                System.out.println("Best target:" + bestTarget);
                align(anchorPane, canvasGroup);
                updateCommandButtons(driver, gameMap);
                //drawGameElements(gameMap);
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void draw(){

    }

    private void putDriversOnTrack(GameMap gameMap) {
        List<GameMap.Waypoint> startingPositions = gameMap.getStartingPosition();
        //FIXME: the first file sometimes gets selected in the background
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

    @FXML
    private void abandonButtonClick() {

        changeScene(GAME_SETUP_SCENE_FXML);
    }

    private void updateCommandButtons(Driver driver, GameMap gameMap) {
        GameMap.Waypoint[] possibleNextWaypoints =
                RouteFinder.getPossibleNextWaypoints(
                        gameMap,
                        driver
                );
        for (int i = 0; i < commandButtons.length; i++) {
            Button button = commandButtons[i];
            GameMap.Waypoint nextWaypoint = possibleNextWaypoints[i];
            button.setDisable(nextWaypoint == null);
        }
    }
}