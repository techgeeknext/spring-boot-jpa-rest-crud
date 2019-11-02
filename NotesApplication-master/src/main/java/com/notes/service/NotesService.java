package com.notes.service;
import java.util.List;

import javax.validation.Valid;

import com.notes.model.Note;
import com.notes.model.NoteBase;
public interface NotesService {

	public List<Note> getAllNotesByUserId(Long userId);
	
	public String addNote(Long userId, @Valid NoteBase note);
	
	public Note updateNote(Long userId, Long noteId, @Valid NoteBase note);
	
	public String deleteNote(Long userId, Long noteId);

	
}