package it.unicam.cs;

import it.unicam.cs.gui.controller.SceneController;
import it.unicam.cs.gui.util.GuiStuff;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;


/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {
    @Override
    public void start(Stage stage) {
        try {
            SceneController.setStage(stage);
            GuiStuff.init(LOGGER);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}