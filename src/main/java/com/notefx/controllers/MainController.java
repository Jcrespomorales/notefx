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

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	// Servicio de aplicacion para crear y gestionar notas.
	private final NoteService noteService = new NoteService();
	// Lista observable enlazada a la vista de notas.
	private final ObservableList<Note> notes = FXCollections.observableArrayList();

	@FXML
	// Lista visual donde se muestran las notas disponibles.
	private ListView<Note> notesList;

	@FXML
	// Area de texto para editar el contenido de la nota seleccionada.
	private TextArea noteInput;
	
	@FXML
	// Área de texto para nombrar la nota creada
	private TextField titleInput;

	@FXML
	// Vista previa para renderizar contenido enriquecido de la nota.
	private WebView preview;

	// ==== INICIALIZACION DEL CONTROLADOR ====
	// ================================
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		// Al seleccionar una nota, su contenido pasa al editor.
		notesList.getSelectionModel().selectedItemProperty().addListener((obs, oldNote, newNote) -> {
			noteInput.setText(newNote == null || newNote.getContenido() == null ? "" : newNote.getContenido());
		});
	}

	
	// ===== CREAR NOTE =======
	// =====================
	
	@FXML
	private void onNuevaNota(ActionEvent event) {
		String titulo = titleInput.getText();

		if (titulo == null || titulo.isBlank()) {
			focusTitleInput();
			return;
		}

		try {
			Note nueva = noteService.crearNote(titulo);
			notes.add(0, nueva);
			notesList.getSelectionModel().select(nueva);
			titleInput.clear();
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

	// ===== EDITAR NOTE =====
	// ====================
	// Al pulsar el botón guardar se edita la base de datos
	
	@FXML
	// Guarda el contenido actual de la nota seleccionada en la base de datos.
	private void onGuardarNota(ActionEvent event) {
			Note seleccionada = notesList.getSelectionModel().getSelectedItem();
			if (seleccionada == null) {
				focusNoteInput();
				return;
			}
			try {
				Note actualizada = noteService.editarContenido(seleccionada.getId(), noteInput.getText());
				seleccionada.setContenido(actualizada.getContenido());
			} catch (RuntimeException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No se pudo guardar la nota");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			focusNoteInput();
		}
	
	
	

	
	// ==== ELIMINAR NOTE ====
	// ====================
	
	@FXML
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
				noteInput.clear();
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
	
	// Centraliza el movimiento del cursor al TextFiel del titulo.
	private void focusTitleInput() {
		if(titleInput != null) {
			titleInput.requestFocus();
			titleInput.positionCaret(titleInput.getText().length());
		}
	}





}