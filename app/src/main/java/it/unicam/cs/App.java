package it.unicam.cs;

import it.unicam.cs.gui.controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.unicam.cs.api.components.container.Resources.getResourceFile;
import static it.unicam.cs.api.parser.types.PropertiesParser.loadPropertiesFile;


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
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}