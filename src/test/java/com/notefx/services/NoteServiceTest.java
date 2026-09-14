package com.notefx.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.notefx.models.Note;
import com.notefx.repository.NoteRepository;

class NoteServiceTest {

    @Test
    void crearNoteRecortaTituloYDelegaAlRepositorio() {
        FakeNoteRepository repository = new FakeNoteRepository();
        NoteService service = new NoteService(repository);

        Note note = service.crearNote("  Titulo  ");

        assertEquals("Titulo", note.getTitulo());
        assertEquals("Titulo", repository.lastCreatedTitle);
    }

    @Test
    void crearNoteLanzaExcepcionSiTituloVacio() {
        NoteService service = new NoteService(new FakeNoteRepository());

        assertThrows(IllegalArgumentException.class, () -> service.crearNote("   "));
    }

    @Test
    void eliminarPorIdDevuelveFalseSiIdEsNulo() {
        FakeNoteRepository repository = new FakeNoteRepository();
        NoteService service = new NoteService(repository);

        boolean eliminado = service.eliminarPorId(null);

        assertFalse(eliminado);
        assertEquals(0, repository.deleteCalls);
    }

    @Test
    void eliminarPorIdDelegadoAlRepositorio() {
        FakeNoteRepository repository = new FakeNoteRepository();
        repository.deleteResult = true;
        NoteService service = new NoteService(repository);

        boolean eliminado = service.eliminarPorId(1L);

        assertTrue(eliminado);
        assertEquals(1, repository.deleteCalls);
    }

    private static class FakeNoteRepository implements NoteRepository {
        private String lastCreatedTitle;
        private int deleteCalls;
        private boolean deleteResult;

        @Override
        public Note crearNote(String titulo) {
            this.lastCreatedTitle = titulo;
            return new Note(titulo);
        }

        @Override
        public Note editarNote(Long id, String contenido) {
            Note note = new Note("tmp");
            note.setId(id);
            note.setContenido(contenido);
            return note;
        }

        @Override
        public boolean eliminarPorId(Long id) {
            deleteCalls++;
            return deleteResult;
        }

        @Override
        public List<Note> verLista() {
            return new ArrayList<>();
        }

        @Override
        public Optional<Note> obtenerPorTitulo(Note note) {
            return Optional.empty();
        }
    }
}
