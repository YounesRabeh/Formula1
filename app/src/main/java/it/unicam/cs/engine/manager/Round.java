package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Driver;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a round in the game, which is a circular list of drivers.
 * When a driver is taken, it gets added back to the end of the list in FIFO order.
 * @see it.unicam.cs.api.components.actors.Driver
 * @see it.unicam.cs.engine.manager.GameManager
 * @author Younes Rabeh
 * @version 1.9
 */
public class Round extends CopyOnWriteArrayList<Driver> {

    public Round() {
        super();
    }

    /**
     * Adds a single driver to the list.
     * @param driver the driver to add
     * @throws IllegalArgumentException if the driver is null
     */
    @Override
    public boolean add(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        return super.add(driver);
    }

    /**
     * Adds a collection of drivers to the list.
     * @param drivers the drivers to add
     * @throws IllegalArgumentException if the collection is null or contains null drivers
     */
    @Override
    public boolean addAll(Collection<? extends Driver> drivers) {
        if (drivers == null) {
            throw new IllegalArgumentException("Collection of drivers cannot be null");
        }
        for (Driver driver : drivers) {
            if (driver == null) {
                throw new IllegalArgumentException("Collection cannot contain null drivers");
            }
        }
        return super.addAll(drivers);
    }

    /**
     * Takes the first driver from the list and adds them to the end of the list in FIFO order.
     * If the list is empty, this method does nothing.
     * @return the first driver in the list, or null if the list is empty
     */
    public Driver take() {
        Driver driver = null;
        if (!isEmpty()) {
            driver = removeFirst();
            add(driver);
        }
        return driver;
    }
}
