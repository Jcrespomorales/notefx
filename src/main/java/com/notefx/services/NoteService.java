package com.notefx.services;

import com.notefx.models.Note;
import com.notefx.repository.NoteRepository;
import com.notefx.repository.NoteDAO;

public class NoteService {

    // Dependencia para persistir y recuperar notas.
    private final NoteRepository repository;

    // Constructor por defecto que usa la implementacion DAO.
    public NoteService() {
        this(new NoteDAO());
    }

    // Permite inyectar un repositorio alternativo (por ejemplo, en pruebas).
    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // Crea una nota validando que el titulo no este vacio.
    public Note crearNote(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }
        return repository.crearNote(titulo.trim());
    }

}