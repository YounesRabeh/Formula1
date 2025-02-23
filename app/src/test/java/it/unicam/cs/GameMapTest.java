package it.unicam.cs;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.gui.map.GameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameMapTest {
    @Test
    void testGameMap() {
        GameMap map = new GameMap(10, 10, 3);
        assertEquals(100, map.getWidth());
        assertEquals(100, map.getHeight());
        assertEquals(5, map.getCanvases().length);
        assertEquals(100, map.getCanvases()[0].getHeight());

    }

    @Test
    void testGetGridCanvas() {
        GameMap map = new GameMap(30, 30, 3);
        assertNotNull(map.getGridCanvas());
    }

    @Test
    void testGetTrackCanvas() {
        GameMap map = new GameMap(10, 10, 3);
        assertNotNull(map.getTrackCanvas());
    }


    @Test
    void testSetAndGetFinishLine() {
        GameMap map = new GameMap(10, 10, 3);
        GameMap.Waypoint finishLine = map.createWaypoint(90, 90);
        assertThrows(IllegalStateException.class, () -> map.createFinishLine(finishLine));
    }


}
