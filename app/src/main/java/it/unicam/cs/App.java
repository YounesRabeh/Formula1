package it.unicam.cs;

import it.unicam.cs.gui.controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application {
    /** The app's logger.*/
    public static final Logger LOGGER = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            SceneController.init(stage);
        } catch (Exception e) {
           LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}