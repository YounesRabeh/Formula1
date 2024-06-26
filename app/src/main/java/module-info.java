module it.unicam.cs.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens it.unicam.cs.app to javafx.fxml;
    exports it.unicam.cs.app;
}