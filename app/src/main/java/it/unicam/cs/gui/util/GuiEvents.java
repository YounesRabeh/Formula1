package it.unicam.cs.gui.util;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Utility class for listeners and event handling
 * @author Younes Rabeh
 * @version 1.0
 */
public final class GuiEvents {
    private GuiEvents() {}  // Prevent instantiation

    /**
     * Attach a listener to the stage to maximize/minimize the window.
     * @param stage the stage of the application
     * @param WIDTH the width of the window
     * @param HEIGHT the height of the window
     * @param MIN_WIDTH the minimum width of the window
     * @param MIN_HEIGHT the minimum height of the window
     */
    public static void attach_WindowMaximizedListener(
            Stage stage,
            double WIDTH, double HEIGHT, double MIN_WIDTH, double MIN_HEIGHT
    ){
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stage.setWidth(WIDTH);
                stage.setHeight(HEIGHT);
            } else {
                stage.setWidth(MIN_WIDTH);
                stage.setHeight(MIN_HEIGHT);
            }
        });
    }

    /**
     * Attach a listener to the stage to update the dividers positions of the split panes
     * @param splitPanes the split panes to update, with their respective divider positions
     * (0.0 to 1.0)
     */
    public static void attach_DividersResizeListener(
            HashMap<SplitPane, Double> splitPanes
    ){
        for (SplitPane splitPane : splitPanes.keySet()) {
            splitPane.setDividerPositions(splitPanes.get(splitPane));
        }
    }

    public static void attach_PaneResizeListener(
            HashMap<Pane, Double> panes
    ){
        for (Pane pane : panes.keySet()) {
            pane.setMinWidth(panes.get(pane));
        }
    }

    public static void attach_WindowResizeListener(
            Stage stage,
            AnchorPane root,
            double originalWidth, double originalHeight
    ){
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scaleX = newVal.doubleValue() / originalWidth;
            scaleAllChildren(root, scaleX, 1);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            double scaleY = newVal.doubleValue() / originalHeight;
            scaleAllChildren(root, 1, scaleY);
        });
    }

    private static void scaleAllChildren(AnchorPane root, double scaleX, double scaleY) {
        // Apply the scale transformation to each child
        root.getChildren().forEach(node -> {
            Scale scale = new Scale(scaleX, scaleY, 0, 0);
            node.getTransforms().clear(); // Clear previous transformations
            node.getTransforms().add(scale);
        });
    }
}
