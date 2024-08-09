package org.example;

import org.example.controller.NoteController;
import org.example.model.Note;
import org.example.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    private Note note;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setContent("This is a test note.");
    }

    @Test
    public void testGetAllNotes() {
        when(noteService.findAll()).thenReturn(Arrays.asList(note));

        List<Note> notes = noteController.getAllNotes();

        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).getTitle());
        verify(noteService, times(1)).findAll();
    }

    @Test
    public void testGetNoteById() {
        when(noteService.findById(anyLong())).thenReturn(Optional.of(note));

        ResponseEntity<Note> response = noteController.getNoteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Note", response.getBody().getTitle());
        verify(noteService, times(1)).findById(1L);
    }

    @Test
    public void testGetNoteById_NotFound() {
        when(noteService.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Note> response = noteController.getNoteById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
    }

    @Test
    public void testCreateNote() {
        when(noteService.save(any(Note.class))).thenReturn(note);

        ResponseEntity<Note> response = noteController.createNote(note);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Note", response.getBody().getTitle());
        verify(noteService, times(1)).save(any(Note.class));
    }

    @Test
    public void testUpdateNote() {
        when(noteService.findById(anyLong())).thenReturn(Optional.of(note));
        when(noteService.save(any(Note.class))).thenReturn(note);

        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Note");
        updatedNote.setContent("This is the updated content.");

        ResponseEntity<Note> response = noteController.updateNote(1L, updatedNote);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Note", response.getBody().getTitle());
        verify(noteService, times(1)).findById(1L);
        verify(noteService, times(1)).save(any(Note.class));
    }

    @Test
    public void testUpdateNote_NotFound() {
        when(noteService.findById(anyLong())).thenReturn(Optional.empty());

        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Note");

        ResponseEntity<Note> response = noteController.updateNote(1L, updatedNote);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
        verify(noteService, never()).save(any(Note.class));
    }

    @Test
    public void testDeleteNoteById() {
        doNothing().when(noteService).deleteById(anyLong());

        ResponseEntity<Void> response = noteController.deleteNoteById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noteService, times(1)).deleteById(1L);
    }
}
