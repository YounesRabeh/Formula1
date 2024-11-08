package it.unicam.cs;


import java.util.logging.Logger;

/**
 * Useful data for debugging and testing

 */
public interface DebugData {
    String APP_WINDOW_TITLE = "FORMULA 1";
    String F1_APP_ICONS_FOLDER = "/it/unicam/cs/icons/";



    //TEMP: For testing purposes
    // NOTE: Do not change the following values (Action dependent)
    Logger LOGGER = Logger.getLogger(App.class.getName());
    String PARSER_FILE_PATH = "/it/unicam/cs/data/test1.f1m";
    String NEW_MAP_FILE_PATH = "/it/unicam/cs/data/default/&new-map.f1m";


    String WELCOME_SCENE_FXML = "/it/unicam/cs/fxml/welcome-scene.fxml";
    String MAP_EDITOR_SCENE_FXML = "/it/unicam/cs/fxml/map-editor-scene.fxml";
    String GAME_SCENE_FXML = "/it/unicam/cs/fxml/game-scene.fxml";

    String SEGMENT_BOX_ENTRY = "/it/unicam/cs/css/ui/SegmentEndpointEntryBoxStyle.css";
    int TRACK_LVL = 0;
    int EXTRA_LVL = 1;
    int WAYPOINT_LVL = 2;
    int END_POINTS_LVL = 3;
    int GRID_LVL = 4;




}
