package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

/**
 * Controller for the map editor scene
 * @see it.unicam.cs.gui.controller.SceneController
 * @author Younes Rabeh
 * @version 1.0
 */
public class MapEditorController extends SceneController {
    @FXML
    private SplitPane splitPane;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;

    /** Minimum width for the left pane (the map preview)*/
    public static final int LEFT_PANE_MIN_WIDTH = 200;
    /** Minimum width for the right pane (the control panel)*/
    public static final int RIGHT_PANE_MIN_WIDTH = 300;


    /**
     * Initialize the map editor scene
     */
    @FXML
    public void initialize() {
        leftPane.setMinWidth(LEFT_PANE_MIN_WIDTH);
        rightPane.setMinWidth(RIGHT_PANE_MIN_WIDTH);

        splitPane.setDividerPositions(0.6);
    }



}
