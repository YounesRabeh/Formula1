package it.unicam.cs;


import java.util.logging.Logger;

/**
 * Useful data for debugging and testing

 */
public interface DebugData {
    int WIDTH = 1000;
    int HEIGHT = 900;
    String APP_WINDOW_TITLE = "FORMULA 1";



    // NOTE: Do not change the following values (Action dependent)
    Logger LOGGER = Logger.getLogger(App.class.getName());
    String PARSER_FILE_PATH = "/it/unicam/cs/data/test1.f1m";
    String WELCOME_SCENE_FXML = "/it/unicam/cs/fxml/welcome-scene.fxml";
    String GAME_SCENE_FXML = "/it/unicam/cs/fxml/game-scene.fxml";
    int TRACK_LVL = 0;
    int EXTRA_LVL = 1;
    int WAYPOINT_LVL = 2;
    int END_POINTS_LVL = 3;
    int GRID_LVL = 4;


}
