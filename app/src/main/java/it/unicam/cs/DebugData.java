package it.unicam.cs;


import java.util.logging.Logger;

/**
 * Useful data for debugging and testing

 */
public interface DebugData {
    boolean FXML_TEST = false;
    int WIDTH = 1000;
    int HEIGHT = 900;
    String APP_WINDOW_TITLE = "FORMULA 1";



    // NOTE: Do not change the following values (Action dependent)
    Logger LOGGER = Logger.getLogger(App.class.getName());
    String PARSER_FILE_PATH = "/it/unicam/cs/data/test1.f1m";
    String FXML_FILE_PATH = "/it/unicam/cs/fxml/app.fxml";
    int TRACK_LVL = 0;
    int EXTRA_LVL = 1;
    int WAYPOINT_LVL = 2;
    int END_POINTS_LVL = 3;
    int GRID_LVL = 4;


}
