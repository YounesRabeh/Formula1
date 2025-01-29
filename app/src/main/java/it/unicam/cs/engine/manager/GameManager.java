package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.engine.nav.RouteFinder;
import it.unicam.cs.gui.map.GameMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages the game
 * @author Younes Rabeh
 * @version 1.0
 */
public class GameManager {
    /** The instance of the game manager */
    private static GameManager instance;

    private final GameMap gameMap;

    /** The list of drivers in the game */
    private static List<Driver> currentDrivers = new LinkedList<>();
    /** The current round */
    private static Round round;
    /** The game status */
    private volatile boolean running;

    /**
     * Creates a new game manager
     */
    private GameManager(GameMap gameMap) {
        this.gameMap = gameMap;
        round = new Round();
        running = false;
    }

    /**
     * Returns the instance of the game manager
     * @return the instance of the game manager
     */
    public static GameManager getInstance(GameMap gameMap) {
        if (instance == null) {
            instance = new GameManager(gameMap);
        }
        return instance;
    }

    public static void initRound(){
        currentDrivers.clear();

        round = new Round();
        round.addAllDrivers(currentDrivers);
        System.gc();
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


    public void execute() {
        running = true;
        initRound();
        Driver driver;
        try {
            while ((driver = next()) != null) {
                driver.move(RouteFinder.getBestTarget(
                        driver.getPosition(),
                        Arrays.asList(
                                RouteFinder.getPossibleNextWaypoints(gameMap, driver)
                        ))
                );
            }
        } catch (IllegalStateException ignored) {}
        running = false;
    }


    /**
     * Gets the next driver from the stack
     * @return the next driver
     */
    private Driver next() {
        if (round.isEmpty()) {
            throw new IllegalStateException("No drivers in stack");
        }
        return round.pop();
    }
}
