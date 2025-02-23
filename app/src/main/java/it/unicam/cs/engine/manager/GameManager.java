package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.gui.map.GameMap;
import it.unicam.cs.gui.util.GuiTools;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import static it.unicam.cs.api.parser.types.PropertiesParser.CONFIG_PROPERTIES_PATH;
import static it.unicam.cs.api.parser.types.PropertiesParser.getProperty;
import static it.unicam.cs.engine.util.EngineTools.setBotsCheckpoints;
import static it.unicam.cs.gui.controller.GameSceneController.commandButtons;


/**
 * Manages the game,
 * @author Younes Rabeh
 * @version 2.0
 */
public class GameManager {
    /** The instance of the game manager */
    private static GameManager instance;
    /** The map AnchorPane */
    private static AnchorPane mapArea;
    /** The game map */
    private static GameMap gameMap;
    /** The list of drivers in the game */
    private static final List<Driver> currentDrivers = new LinkedList<>();
    /** The current round */
    private static Round round;
    /** The round thread */
    private static Thread roundThread;
    //TODO: Implement the winner
    /** The winner of the game */
    public static Driver winner;
    /** True if all the current drivers are bots */
    private static boolean allDriversBots;
    /** The delay between the bot's moves, used only if all the current drivers are bots */
    private static final int BOT_DELAY = Integer.parseInt(Objects.requireNonNull(getProperty(
            CONFIG_PROPERTIES_PATH, "BOT_DELAY"
    )));

    /** The current driver */
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
        currentDrivers.addAll(gameMap.getDrivers());
        setBotsCheckpoints(gameMap);
        onlyBotsCheck();
        roundThread = roundThread();
        roundThread.start();
    }

    /**
     * Kills the round thread
     */
    private static void killRound() {
        if (roundThread != null) {
            roundThread.interrupt();
            roundThread = null;
        }
    }

    private static CountDownLatch latch = new CountDownLatch(1);

    /**
     * The round thread, which will execute the moves of
     * the bots and wait for the player to execute his move, if all the drivers are bots,
     * it will give a {@link GameManager#BOT_DELAY} between the bot's moves
     * */
    private static Thread roundThread(){
        return new Thread(() -> {
            while (!round.isEmpty()) { //The round is empty only if all other drivers did crash
                currentDriver = round.take();
                if (currentDriver instanceof Bot bot) {
                    if (allDriversBots) {

                    }
                    try {
                        Thread.sleep(BOT_DELAY);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    execute(bot);
                    latch = new CountDownLatch(1);
                    Platform.runLater(() -> GuiTools.mapUpdate(gameMap, mapArea));
                    GuiTools.updateCommandButtons(currentDriver, gameMap, commandButtons);
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
     * Checks if all the drivers are bots, to give a delay between the bot's moves
     */
    private static void onlyBotsCheck(){
        for (Driver driver : currentDrivers){
            if (driver instanceof Player){
                allDriversBots = false;
                return;
            }
        }
        allDriversBots = true;
    }

    public static boolean getIsAllDriversBots(){
        return allDriversBots;
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
        Collection<GameMap.Waypoint> targets = new ArrayList<>();
        GameMap.Waypoint current = bot.getPosition();
        GameMap.Waypoint currentTarget = bot.getTarget();
        if (current.equals(currentTarget)) {
            bot.setTarget(bot.getNextCheckpoint());
        }
        targets.add(bot.getTarget());
        System.out.println("Bot " + bot.getName() + " target: " + bot.getTarget());
        GameMap.Waypoint[] possibleNextWaypoints = RouteFinder.getPossibleNextWaypoints(gameMap, bot);
        GameMap.Waypoint bestTarget = RouteFinder.getBestTarget(
                possibleNextWaypoints,
                targets
        );
        bot.move(bestTarget);
    }

    /**
     * Returns true if the round is over
     * @return true if the round is over
     */
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
     * End the current Game and reset the game manager to its default (ready for another game),
     * used also to close teh app prematurely
     */
    public static void endGame() {
        if (roundThread != null) killRound();
        Optional.ofNullable(currentDrivers).ifPresent(List::clear);
        Optional.ofNullable(round).ifPresent(Round::clear);
        currentDriver = null;
        instance = null;
        gameMap = null;
        latch = new CountDownLatch(1);
    }
}
