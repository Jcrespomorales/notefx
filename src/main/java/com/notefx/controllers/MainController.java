package com.notefx.controllers;

import com.notefx.models.Note;
import com.notefx.services.NoteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	// Servicio de aplicacion para crear y gestionar notas.
	private final NoteService noteService = new NoteService();
	// Lista observable enlazada a la vista de notas.
	private final ObservableList<Note> notes = FXCollections.observableArrayList();

	private static final String TITLE_HINT = "Ingresa un tÍtulo para la Nota";

	@FXML
	// Lista visual donde se muestran las notas disponibles.
	private ListView<Note> notesList;

	@FXML
	// Area de texto para editar el contenido de la nota seleccionada.
	private TextArea noteInput;

	@FXML
	// Vista previa para renderizar contenido enriquecido de la nota.
	private WebView preview;

	// ==== INICIALIZACION DEL CONTROLADOR ====
	// ================================
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Si esta visible el mensaje guia, se limpia al escribir el primer caracter.
		noteInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			if (TITLE_HINT.equals(noteInput.getText()) && !event.getCharacter().isEmpty()) {
				noteInput.clear();
			}
		});

		// Enter confirma el titulo y evita insertar una nueva linea en el TextArea.
		noteInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				onNuevaNota(new ActionEvent(noteInput, noteInput));
			}
		});

		// Vincula la lista observable al ListView.
		// Carga inicial: mantiene una sola lista observable enlazada a la vista.
		notesList.setItems(notes);
		notes.setAll(noteService.verLista());
		
		// Muestra en la lista solo el atributo titulo del objeto
		notesList.setCellFactory(lv -> new ListCell<>() {
			@Override
			protected void updateItem(Note item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getTitulo());
			}
		});
	}

	@FXML
	// Crea una nueva nota con el titulo ingresado y la agrega al inicio de la
	// lista.
	private void onNuevaNota(ActionEvent event) {
		String titulo = noteInput.getText();

		if (titulo != null && titulo.equals("")) {
			noteInput.setText(TITLE_HINT);
			focusNoteInput();
			return;
		}

		try {
			Note nueva = noteService.crearNote(titulo);
			notes.add(0, nueva);
			notesList.getSelectionModel().select(nueva);
			noteInput.clear();
			focusNoteInput();
		} catch (RuntimeException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo crear la nota");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			focusNoteInput();
		}
	}

	@FXML
	// Devuelve el foco al area de edicion tras pulsar Guardar.
	private void onGuardarNota(ActionEvent event) {
		focusNoteInput();
	}

	@FXML
	// Devuelve el foco al area de edicion tras pulsar Editar.
	private void onEditarNota(ActionEvent event) {
		focusNoteInput();
	}

	@FXML
	// Devuelve el foco al area de edicion tras pulsar Eliminar.
	private void onEliminarNota(ActionEvent event) {
		Note seleccionada = notesList.getSelectionModel().getSelectedItem();
		if (seleccionada == null) {
			focusNoteInput();
			return;
		}

		try {
			boolean eliminada = noteService.eliminarPorId(seleccionada.getId());
			if (eliminada) {
				notes.remove(seleccionada);
			}
		} catch (RuntimeException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo eliminar la nota");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

		focusNoteInput();
	}

	// Centraliza el movimiento del cursor al TextArea principal.
	private void focusNoteInput() {
		if (noteInput != null) {
			noteInput.requestFocus();
			noteInput.positionCaret(noteInput.getText().length());
		}
	}
}