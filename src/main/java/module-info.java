module notefx {
    // JavaFX modules used by the application
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    // If you later use other JVM libraries that are modular, add them here (e.g. requires com.vladsch.flexmark;)

    // Open the packages that FXMLLoader needs reflective access to
    opens com.notefx.controllers to javafx.fxml;
    opens com.notefx.app to javafx.fxml;

    // Export the application package if other modules need to access it (not strictly necessary for JavaFX runtime)
    exports com.notefx.app;
}