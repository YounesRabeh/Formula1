module it.unicam.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens it.unicam.cs to javafx.fxml;
    opens it.unicam.cs.gui.controller to javafx.fxml;
    exports it.unicam.cs;
    exports it.unicam.cs.gui.controller;
}