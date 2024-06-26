module it.unicam.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens it.unicam.cs to javafx.fxml;
    exports it.unicam.cs;
}