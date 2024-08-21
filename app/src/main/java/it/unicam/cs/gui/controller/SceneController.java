package it.unicam.cs.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class SceneController {
    private static Stage stage;

    private SceneController() {}

    public static void setStage(Stage stage) {
        SceneController.stage = stage;
        SceneController.stage.setMaximized(true);
        SceneController.stage.setResizable(false);
    }

    public static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        Scene scene = new Scene(loader.load());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}