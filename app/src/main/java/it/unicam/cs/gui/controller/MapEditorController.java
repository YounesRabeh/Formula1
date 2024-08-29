package it.unicam.cs.gui.controller;

import it.unicam.cs.gui.util.GuiEvents;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;

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

    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private Pane rightBottomPane;
    @FXML
    private Pane rightTopPane;

    /** Minimum width for the left pane (the map preview)*/
    public static final double LEFT_PANE_MIN_WIDTH = getWidth() * 0.5;
    /** Minimum width for the right pane (the control panel)*/
    public static final double RIGHT_PANE_MIN_WIDTH = getWidth() * 0.2;
    /** Minimum height for the right top pane (the map control panel)*/
    public static final double RIGHT_TOP_PANE_MIN_HEIGHT = getHeight() * 0.2;



    /**
     * Initialize the map editor scene
     */
    @FXML
    public void initialize() {
        leftPane.setMinWidth(LEFT_PANE_MIN_WIDTH);
        rightPane.setMinWidth(RIGHT_PANE_MIN_WIDTH);
        rightTopPane.setMinHeight(RIGHT_TOP_PANE_MIN_HEIGHT);

        rightSplitPane.setDividerPositions(0.8);
        splitPane.setDividerPositions(0.6);


    }



}
