module it.unicam.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.logging;
    requires jdk.jfr;

    opens it.unicam.cs to javafx.fxml;
    opens it.unicam.cs.gui.controller to javafx.fxml;
    exports it.unicam.cs;
    exports it.unicam.cs.gui.controller;
}