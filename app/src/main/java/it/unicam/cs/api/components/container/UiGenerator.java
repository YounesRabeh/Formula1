package it.unicam.cs.api.components.container;

import it.unicam.cs.api.components.actors.Driver;
import it.unicam.cs.api.components.actors.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

import static it.unicam.cs.api.parser.types.AbstractParser.PARSER_SEPARATOR;
import static it.unicam.cs.api.parser.types.PropertiesParser.CONFIG_PROPERTIES_PATH;
import static it.unicam.cs.api.parser.types.PropertiesParser.getProperty;


/**
 * Utility class for generating the UI components
 * @author Younes Rabeh
 * @version 1.8
 */
public final class UiGenerator {
    private UiGenerator() {}
    /** The segment box entry FXML file. */
    private static final String SEGMENT_BOX_ENTRY = getProperty(
            CONFIG_PROPERTIES_PATH, "SEGMENT_BOX_ENTRY"
    );
    /** The images folder. */
    private static final String IMAGES_FOLDER = getProperty(
            CONFIG_PROPERTIES_PATH, "IMAGES_FOLDER"
    );

    /** The segment box entry style. */
    private static final String SEGMENT_ENDPOINT_ENTRY_STYLE =
            Resources.getResourceURL(SEGMENT_BOX_ENTRY).toString();

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

    public static HBox createDriverEntry(Driver driver) {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER_LEFT);

        // Player or bot image
        ImageView driverImageView;
        if (driver instanceof Player) {
            driverImageView = new ImageView(
                    Resources.getImage(IMAGES_FOLDER + "player.png")
            );
        } else {
            driverImageView = new ImageView(
                    Resources.getImage(IMAGES_FOLDER + "bot.png")
            );
        }
        driverImageView.setFitWidth(65);
        driverImageView.setFitHeight(65);
        driverImageView.setPreserveRatio(true);

        // Label with name
        Label nameLabel = new Label(driver.getName());
        nameLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-weight: bold; -fx-font-size: 25px;");

        // Spacer to push buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Edit button
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        editButton.setMinWidth(80);
        editButton.setOnAction(event -> {
            showEditPopup(driver, nameLabel, hbox);
        });


        hbox.setStyle("-fx-border-color: " + toHex(driver.getCarColor()) + "; -fx-border-width: 5px;");

        hbox.getChildren().addAll(driverImageView, nameLabel, spacer, editButton);
        return hbox;
    }

    // Method to show the edit popup
    private static void showEditPopup(Driver driver, Label nameLabel, HBox hbox) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit Driver");

        // Popup layout
        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(10));
        popupLayout.setAlignment(Pos.CENTER);

        // Name input
        TextField nameField = new TextField(driver.getName());
        nameField.setPromptText("Enter new name");

        // Color picker
        Label colorLabel = new Label("Choose car color:");
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(driver.getCarColor());

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        saveButton.setOnAction(saveEvent -> {
            String newName = nameField.getText().trim();
            assert PARSER_SEPARATOR != null;
            if (newName.contains(PARSER_SEPARATOR)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Name");
                alert.setHeaderText("Invalid Character in Name");
                alert.setContentText("The name cannot contain: \"" + PARSER_SEPARATOR + "\"");
                alert.showAndWait();
            } else if (!newName.isEmpty()) {
                if (newName.length() > driver.getMaxDriverNameLength()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Name");
                    alert.setHeaderText("Name is too long");
                    alert.setContentText("The name cannot exceed " + driver.getMaxDriverNameLength() + " characters");
                    alert.showAndWait();
                    return;
                }
                driver.setName(newName);
                nameLabel.setText(newName);
                driver.setCarColor(colorPicker.getValue());
                hbox.setStyle("-fx-border-color: " + toHex(colorPicker.getValue()) + "; -fx-border-width: 5px;");
                popup.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Name");
                alert.setHeaderText("Name cannot be empty");
                alert.setContentText("Please enter a valid name");
                alert.showAndWait();
            }
        });

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px;");
        cancelButton.setOnAction(cancelEvent -> popup.close());

        // Add components to popup layout
        popupLayout.getChildren().addAll(nameField, colorLabel, colorPicker, saveButton, cancelButton);

        // Show the popup
        Scene popupScene = new Scene(popupLayout, 300, 200);
        popup.setScene(popupScene);
        popup.showAndWait();
    }

    // Helper method to convert Color to hex
    private static String toHex(Color color) {
        return String.format(
                "#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255)
        );
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
