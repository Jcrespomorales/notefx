package com.notefx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ListView<?> notesList;

    @FXML
    private TextArea noteInput;

    @FXML
    private WebView preview;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Controlador inicializado");

		
	}
    
   


}
