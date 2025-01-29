package it.unicam.cs.engine.manager;

import it.unicam.cs.api.components.actors.Driver;

import java.util.Collection;
import java.util.Stack;

public class Round extends Stack<Driver> {

    public Round() {
        super();
    }

    public void addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        push(driver);
    }


    public void addAllDrivers(Collection<? extends Driver> c) {
        for (Driver driver: c) addDriver(driver);
    }
}
