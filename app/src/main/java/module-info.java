module it.unicam.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.rmi;
    requires java.logging;

    opens it.unicam.cs to javafx.fxml;
    opens it.unicam.cs.gui.controller to javafx.fxml;
    exports it.unicam.cs;
    exports it.unicam.cs.gui.controller;
}