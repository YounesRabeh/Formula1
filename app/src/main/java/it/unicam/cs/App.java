package it.unicam.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;

import static it.unicam.cs.gui.util.GuiStuff.initializeAndShowStage;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {
    @Override
    public void start(Stage stage) {
        try {
            initializeAndShowStage(stage, LOGGER);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}