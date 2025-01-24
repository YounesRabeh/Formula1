package it.unicam.cs.api.components.container;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import it.unicam.cs.gui.controller.GameSetupSceneController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


import static it.unicam.cs.api.parser.types.PropertiesParser.CONFIG_PROPERTIES_PATH;
import static it.unicam.cs.api.parser.types.PropertiesParser.getProperty;


/**
 * Utility class for generating the UI components
 * @author Younes Rabeh
 * @version 1.0
 */
public final class UiGenerator {
    private UiGenerator() {}  // Prevent instantiation
    /** The segment box entry FXML file. */
    private static final String SEGMENT_BOX_ENTRY = getProperty(
            CONFIG_PROPERTIES_PATH, "SEGMENT_BOX_ENTRY"
    );
    /** The images folder. */
    private static final String IMAGES_FOLDER = getProperty(
            CONFIG_PROPERTIES_PATH, "IMAGES_FOLDER"
    );


    /** The segment box entry style. */
    private static final String SEGMENT_ENDPOINT_ENTRY_STYLE = Resources.getResourceURL(SEGMENT_BOX_ENTRY).toString();

    /**
     * Create a segment endpoint entry.
     * @param num the number of the segment
     * @param x the x coordinate of the segment
     * @param y the y coordinate of the segment
     * @return a segment endpoint entry
     */
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

    /**
     * Create a driver entry.
     * @param driver the driver
     * @return a driver entry
     */
    public static HBox createDriverEntry(Driver driver) {
        HBox hbox = new HBox(10); // Spacing between elements
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER_LEFT);

        // Player or bot image
        ImageView driverImageView;
        if (driver instanceof Player) {
            driverImageView = new ImageView(Resources.getImage(IMAGES_FOLDER + "player.png"));
        } else {
            driverImageView = new ImageView(String.valueOf(Resources.getResourceURL(IMAGES_FOLDER + "bot.png")));
        }
        driverImageView.setFitWidth(65);
        driverImageView.setFitHeight(65);
        driverImageView.setPreserveRatio(true);

        Label nameLabel = new Label(driver.getName() + "°");
        nameLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-weight: bold; -fx-font-size: 25px;");

        // Spacer to push buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Edit button
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        editButton.setMinWidth(80); // Ensure consistent button width
        editButton.setOnAction(event -> {
            System.out.println("Edit clicked for driver: " + driver.getName());
        });

        // Cancel button
        Button cancelButton = new Button("X");
        cancelButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: red;" +
                " -fx-text-fill: white; -fx-font-weight: bold;"
        );
        cancelButton.setMinWidth(40); // Ensure consistent button width
        cancelButton.getStyleClass().add("cancel-button");

        // Add elements to the HBox
        hbox.getChildren().addAll(driverImageView, nameLabel, spacer, editButton, cancelButton);
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

    /**
     * Add a single HBox to a VBox
     * @param vbox the VBox
     * @param hbox the HBox
     */
    public static void addToVBOX(VBox vbox, HBox hbox) {
        addToVBOX(vbox, List.of(hbox));
    }
}
