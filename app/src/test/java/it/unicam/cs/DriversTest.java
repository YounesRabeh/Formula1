package it.unicam.cs;

import it.unicam.cs.api.components.actors.Bot;
import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import it.unicam.cs.gui.map.GameMap;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriversTest {
    @Test
    void testDrivers() {
        Driver driver = new Player("PlayerDriver", Color.GOLD);
        assertEquals("PlayerDriver", driver.getName());

        assertEquals(Color.GOLD, driver.getCarColor());
        driver.setName("NewName");
        assertEquals("NewName", driver.getName());

        driver.setCarColor(Color.BLUE);
        assertEquals(Color.BLUE, driver.getCarColor());

        assertNotNull(driver.getInertia());

        assertNull(driver.getPosition());
    }

    @Test
    void testDriverPosition() {
        GameMap map = new GameMap(10, 10, 3);
        Driver driver = new Bot("PlayerDriver", Color.GOLD) {
        };
        driver.move(map.createWaypoint(10, 10));
        assertEquals(10, driver.getPosition().getX());
        assertEquals(10, driver.getPosition().getY());
    }

}
