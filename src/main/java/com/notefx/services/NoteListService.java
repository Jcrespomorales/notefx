package com.notefx.services;

import java.util.List;

import com.notefx.models.Note;
import com.notefx.repository.NoteRepository;

public class NoteListService {

	private final NoteRepository repository;

	public NoteListService(NoteRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Note>mostrarTodo(){
		return repository.verLista();
	}
}
