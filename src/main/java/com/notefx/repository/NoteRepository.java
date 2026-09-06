package com.notefx.repository;

import java.util.List;
import java.util.Optional;

import com.notefx.models.Note;

public interface NoteRepository {
	
	// === Botones ===

    Note crearNote(String titulo);
    
    Note editarNote(Long id, String contenido);
    
    boolean eliminarPorId(Long id);


// === Lista de Notas ===

    List<Note> verLista();
    
    Optional<Note>obtenerPorTitulo(Note note);



}