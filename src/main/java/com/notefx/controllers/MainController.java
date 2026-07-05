package com.notefx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextArea noteArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicialización básica: placeholder vacío o texto de prueba
        if (noteArea != null) {
            noteArea.setText("");
        }
    }

    // Ejemplo de método para obtener el texto de la nota desde código
    public String getNoteText() {
        return noteArea != null ? noteArea.getText() : "";
    }
}
