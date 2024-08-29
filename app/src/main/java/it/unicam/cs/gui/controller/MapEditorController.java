package it.unicam.cs.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Random;

import static it.unicam.cs.api.components.container.UiGenerator.addToVBOX;
import static it.unicam.cs.api.components.container.UiGenerator.createSegmentEndpointEntry;


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
    private ScrollPane rightBottomPane;
    @FXML
    private Pane rightTopPane;

    @FXML
    private VBox segmentsEndpointsVBox;

    /** Minimum width for the left pane (the map preview)*/
    private static final double LEFT_PANE_MIN_WIDTH = getWidth() * 0.5;
    /** Minimum width for the right pane (the control panel)*/
    private static final double RIGHT_PANE_MIN_WIDTH = getWidth() * 0.2;
    /** Minimum height for the right top pane (the map control panel)*/
    private static final double RIGHT_TOP_PANE_MIN_HEIGHT = getHeight() * 0.2;
    /** Minimum height for the right bottom pane (the segments endpoints preview)*/
    private static final double RIGHT_BOTTOM_PANE_MIN_HEIGHT = getHeight() * 0.1;

    private int segmentsEndpointsCounter;




    /**
     * Initialize the map editor scene
     */
    @FXML
    public void initialize() {
        leftPane.setMinWidth(LEFT_PANE_MIN_WIDTH);
        rightPane.setMinWidth(RIGHT_PANE_MIN_WIDTH);
        rightTopPane.setMinHeight(RIGHT_TOP_PANE_MIN_HEIGHT);
        rightBottomPane.setMinHeight(RIGHT_BOTTOM_PANE_MIN_HEIGHT);

        rightSplitPane.setDividerPositions(0.8);
        splitPane.setDividerPositions(0.6);
        //TEST:
        Random random = new Random();
        ArrayList<HBox> segmentEndpointsEntries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            segmentEndpointsEntries.add(
                    createSegmentEndpointEntry(
                            i, random.nextInt(0, 900), random.nextInt(0, 900)
                    )
            );
        }

        addToVBOX(segmentsEndpointsVBox, segmentEndpointsEntries);
    }



}
