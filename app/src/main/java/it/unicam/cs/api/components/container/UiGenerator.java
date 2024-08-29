package it.unicam.cs.api.components.container;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating the UI components
 * @author Younes Rabeh
 * @version 1.0
 */
public final class UiGenerator {
    private UiGenerator() {}  // Prevent instantiation

    public static HBox createSegmentEndpointEntry(int num, int x, int y) {
        HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER_LEFT);

        // Create labels and text fields
        Label numberLabel = new Label(num + "°");
        Label xLabel = new Label("x =");
        TextField xField = new TextField(String.valueOf(x));
        xField.setPrefWidth(120);

        Label yLabel = new Label("y =");
        TextField yField = new TextField(String.valueOf(y));
        yField.setPrefWidth(120);

        List<Node> nodes = List.of(numberLabel, xLabel, xField, yLabel, yField);
        for (Node node : nodes) {
            node.setStyle("-fx-font-size: 14px;");
        }

        // Set specific margins to adjust padding between labels and text fields
        HBox.setMargin(numberLabel, new Insets(0, 15, 0, 0)); // 5 pixels padding to the left of numberLabel
        HBox.setMargin(xField, new Insets(0, 10, 0, 0)); // 5 pixels padding to the left of xField

        // Add elements to the HBox
        hbox.getChildren().addAll(nodes);

        return hbox;
    }

    public static void addToVBOX(VBox vbox, List<HBox> HBoxes) {
        vbox.getChildren().addAll(HBoxes);
        double totalHeight = HBoxes.stream().mapToDouble(HBox -> HBox.getBoundsInParent().getHeight()).sum();
        vbox.setPrefHeight(totalHeight);
    }

}
