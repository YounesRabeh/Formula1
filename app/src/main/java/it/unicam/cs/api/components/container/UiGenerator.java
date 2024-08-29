package it.unicam.cs.api.components.container;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static it.unicam.cs.DebugData.SEGMENT_BOX_ENTRY;

/**
 * Utility class for generating the UI components
 * @author Younes Rabeh
 * @version 1.0
 */
public final class UiGenerator {
    private UiGenerator() {}  // Prevent instantiation
    private static final String SEGMENT_ENDPOINT_ENTRY_STYLE = Resources.getResourceURL(SEGMENT_BOX_ENTRY).toString();

    public static HBox createSegmentEndpointEntry(int num, int x, int y) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getStylesheets().add(SEGMENT_ENDPOINT_ENTRY_STYLE);
        hbox.getStyleClass().add("HBox-segment-entry");

        // Content of the HBox
        Label numberLabel = new Label(num + "°");
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        Label xLabel = new Label("x =");
        TextField xField = new TextField(String.valueOf(x));
        xField.setPrefWidth(120);

        Label yLabel = new Label("y =");
        TextField yField = new TextField(String.valueOf(y));
        yField.setPrefWidth(120);

        Button cancelButton = new Button();
        cancelButton.setText("X");
        cancelButton.getStyleClass().add("cancel-button");

        // Add a click event handler (optional)
        cancelButton.setOnAction(event -> {
            Platform.runLater(() -> hbox.setVisible(false));
        });


        List<Node> nodes = List.of(numberLabel, separator, xLabel, xField, yLabel, yField, cancelButton);

        // Set specific margins to adjust padding between labels and text fields
        HBox.setMargin(numberLabel, new Insets(0, 0, 0, 0));
        HBox.setMargin(separator, new Insets(0, 10, 0, 5));
        HBox.setMargin(xField, new Insets(0, 10, 0, 0));
        HBox.setMargin(yField, new Insets(0, 10, 0, 0));


        hbox.getChildren().addAll(nodes);
        return hbox;
    }


    //FIXME: Add a method to create a segment entry to make the upper one private
    //TODO: save the pairs of x and y in a list and °num is it size
    public static void addToVBOX(VBox vbox, List<HBox> HBoxes) {
        // Add 5px margin to each HBox before adding to VBox
        for (HBox hbox : HBoxes) {
            VBox.setMargin(hbox, new Insets(5));
        }
        vbox.getChildren().addAll(HBoxes);

        double totalHeight = HBoxes.stream()
                .mapToDouble(hbox -> hbox.getBoundsInParent().getHeight() + 10)
                .sum();
        vbox.setPrefHeight(totalHeight);
    }

}
