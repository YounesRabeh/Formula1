package it.unicam.cs.gui.util;

import javafx.stage.Stage;

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
}
