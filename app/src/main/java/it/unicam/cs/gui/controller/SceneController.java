package it.unicam.cs.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class SceneController {
    private static Stage stage;

    private SceneController() {}

    public static void setStage(Stage stage) {
        SceneController.stage = stage;
        //SceneController.stage.setFullScreen(true);
        SceneController.stage.setMaximized(true);
        SceneController.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void setScene(URL fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}