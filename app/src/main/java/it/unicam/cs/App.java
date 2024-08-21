package it.unicam.cs;

import it.unicam.cs.gui.controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;

import static it.unicam.cs.api.components.container.Resources.getResourceURL;


/**
 * JavaFX App
 * @author Younes Rabeh
 */
public class App extends Application implements DebugData {
    @Override
    public void start(Stage stage) {
        try {
            SceneController.init(stage, getResourceURL(WELCOME_SCENE_FXML));
            SceneController.init(stage, getResourceURL(WELCOME_SCENE_FXML));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}