module notefx {
    // JavaFX modules used by the application
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
	requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.builder;
    requires flexmark.util.collection;
    requires flexmark.util.data;
    requires flexmark.util.dependency;
    requires flexmark.util.format;
    requires flexmark.util.html;
    requires flexmark.util.misc;
    requires flexmark.util.sequence;
    requires flexmark.util.visitor;
    requires flexmark.ext.tables;
    requires flexmark.ext.gfm.strikethrough;
    requires flexmark.ext.gfm.tasklist;
    requires flexmark.ext.autolink;
    requires flexmark.ext.emoji;
    requires flexmark.ext.footnotes;

    // If you later use other JVM libraries that are modular, add them here.

    // Open the packages that FXMLLoader needs reflective access to
    opens com.notefx.controllers to javafx.fxml;
    opens com.notefx.app to javafx.fxml;

    // Export the application package if other modules need to access it (not strictly necessary for JavaFX runtime)
    exports com.notefx.app;
}