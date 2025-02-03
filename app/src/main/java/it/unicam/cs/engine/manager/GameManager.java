package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.gui.map.GameMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



/**
 * Manages the game
 * @author Younes Rabeh
 * @version 1.1
 */
public class GameManager {
    /** The instance of the game manager */
    private static GameManager instance;
    /** The game map */
    private static GameMap gameMap;
    /** The list of drivers in the game */
    private static List<Driver> currentDrivers = new LinkedList<>();
    /** The current round */
    private static Round round;


    /**
     * Creates a new game manager
     */
    private GameManager(GameMap gameMap) {
        GameManager.gameMap = gameMap;
        round = new Round();
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
        round = new Round();
        round.addAllDrivers(currentDrivers);
        System.gc();
    }

    public static void startRound() {
        initRound();

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
     * Gets the next driver from the stack
     * @return the next driver
     */
    public static Driver next() {
        if (round.isEmpty()) {
            return null;
        }
        return round.pop();
    }
}
