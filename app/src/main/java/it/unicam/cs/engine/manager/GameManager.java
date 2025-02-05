package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.util.GuiTools;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import static it.unicam.cs.gui.controller.GameSceneController.commandButtons;


/**
 * Manages the game,
 * @author Younes Rabeh
 * @version 1.8
 */
public class GameManager {
    /** The instance of the game manager */
    private static GameManager instance;
    /** The map AnchorPane */
    private static AnchorPane mapArea;
    /** The game map */
    private static GameMap gameMap;
    /** The list of drivers in the game */
    private static List<Driver> currentDrivers = new LinkedList<>();
    /** The current round */
    private static Round round;
    /** The round thread */
    private static Thread roundThread;

    public static Driver winner;

    private volatile static Driver currentDriver;


    /**
     * Creates a new game manager
     */
    private GameManager(GameMap gameMap, AnchorPane mapArea) {
        GameManager.gameMap = gameMap;
        GameManager.mapArea = mapArea;
        round = new Round();
    }

    /**
     * Returns the next driver in the round
     * @return the next driver
     */
    public static Driver seeNext(){
        return round.getFirst();
    }

    /**
     * Gets the next driver from the stack
     * @return the next driver
     */
    public static Driver next() {
        return round.take();
    }

    /**
     * Returns the instance of the game manager
     * @return the instance of the game manager
     */
    public static GameManager getInstance(GameMap gameMap,  AnchorPane mapArea) {
        if (instance == null) {
            instance = new GameManager(gameMap, mapArea);
        }
        return instance;
    }

    /**
     * Initializes the round, adding all the drivers to the round
     */
    public static void initRound(){
        round = new Round();
        round.addAll(gameMap.getDrivers());
        roundThread = roundThread();
        roundThread.start();
    }

    public static void killRound(){
        roundThread.interrupt();
        roundThread = null;
    }

    private static CountDownLatch latch = new CountDownLatch(1);

    /**
     * The round thread, which will execute the moves of
     * the bots and wait for the player to execute his move
     * */
    private static Thread roundThread(){
        return new Thread(() -> {
            while (!round.isEmpty()) { //The round is empty only if all other drivers did crash
                currentDriver = round.take();
                if (currentDriver instanceof Bot bot) {
                    execute(bot);
                    latch = new CountDownLatch(1);
                    Platform.runLater(() -> GuiTools.mapUpdate(gameMap, mapArea));
                } else {
                    try {
                        GuiTools.updateCommandButtons(currentDriver, gameMap, commandButtons);
                        latch.await();
                        latch = new CountDownLatch(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
    }


    /**
     * Returns the current driver
     * @return the current driver
     */
    public static Driver getCurrentDriver() {
        return currentDriver;
    }



    /**
     * Continues the round, signals to the ga me manager that the player executed his move,
     * the round can continue
     */
    public static void continueRound() {
        latch.countDown();
    }


    /**
     * Executes the bot's move, the bot will move to the best position that is calculated by the route finder
     * @param bot the bot
     */
    private static void execute(Bot bot) {
        GameMap.Waypoint[] possibleNextWaypoints = RouteFinder.getPossibleNextWaypoints(gameMap, bot);
        GameMap.Waypoint bestTarget = RouteFinder.getBestTarget(
                possibleNextWaypoints,
                gameMap.getFinishLine().getWaypoints()
        );
        bot.move(bestTarget);
    }


    public static boolean isRoundOver() {
        return round.isEmpty();
    }


    /**
     * Returns the list of drivers in the game
     * @return the list of drivers
     */
    public List<Driver> getCurrentDrivers() {
        return currentDrivers;
    }

    /**
     * Adds a driver to the manager
     * @param driver the driver to add
     * @throws IllegalArgumentException if the driver is null or already exists
     */
    private void addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("[!!!] - Driver cannot be null");
        }
        if (currentDrivers.contains(driver)) {
            throw new IllegalArgumentException("[!!] - Driver already exists");
        }
        currentDrivers.add(driver);
    }

    /**
     * Adds a collection of drivers to the manager
     * @param c the collection of drivers to add
     */
    public void addAllDrivers(Collection<? extends Driver> c) {
        currentDrivers.clear();
        for (Driver driver: c) addDriver(driver);
    }

    /**
     * End the current Game and reset the game manager to its default (ready for another game)
     */
    public static void endGame() {
        killRound(); // Stop the round thread if it's running
        currentDrivers.clear(); // Clear the list of current drivers
        round.clear(); // Clear the round data
        currentDriver = null; // Reset the current driver
        instance = null; // Allow for reinitialization if needed
        gameMap = null; // Reset the game map
        latch = new CountDownLatch(1); // Reset the latch
    }




}
