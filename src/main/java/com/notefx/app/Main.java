package com.notefx.app;

import com.notefx.database.DatabaseConnection;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Open/close once at startup so SQLite creates note.db and prints the file path.
		try (Connection ignored = DatabaseConnection.connect()) {
			// No-op: startup connectivity check only.
		}

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
		Scene scene = new Scene(root, 800, 600);
		// Referencia la hoja de estilos en resources
		scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
		stage.setTitle("NoteFX");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}