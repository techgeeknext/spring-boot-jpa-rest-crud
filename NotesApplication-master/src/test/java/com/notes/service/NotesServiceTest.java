package com.notes.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.notes.model.Note;
import com.notes.model.User;
import com.notes.repository.NotesRepository;
import com.notes.repository.UserRepository;
import com.notes.util.ApplicationConstants;
import com.notes.util.NotesConstants;
import com.notes.util.NotesMockHelper;

@RunWith(MockitoJUnitRunner.class)
public class NotesServiceTest {
	@Mock
	NotesRepository noteRepo;

	@Mock
	private UserRepository userRepo;

	@InjectMocks
	private NotesServiceImpl noteService;

	@Test
	public void getAllNotesByUserId() {
		User user = NotesMockHelper.USER;
		when(userRepo.findById(user.getUserId())).thenReturn(Optional.of(user));
		when(noteRepo.findNotesByUserId(user.getUserId())).thenReturn(NotesMockHelper.getNotes());
		List<Note> result = noteService.getAllNotesByUserId(user.getUserId());

		assertThat(result.get(0).getTitle(), is(NotesConstants.NOTE1_TITLE));
		assertThat(result.get(1).getTitle(), is(NotesConstants.NOTE2_TITLE));
		assertThat(result.get(2).getTitle(), is(NotesConstants.NOTE3_TITLE));

		assertThat(result.size(), is(3));
	}

	@Test
	public void addNote() {
		User user = NotesMockHelper.USER;
		when(userRepo.findById(user.getUserId())).thenReturn(Optional.of(user));
		String result = noteService.addNote(1l, NotesMockHelper.getNoteBase());
		assertThat(result, is(ApplicationConstants.ADDED_NOTE_DESC));
	}

	@Test
	public void updateNote() {
		User user = NotesMockHelper.USER;
		when(noteRepo.findByNoteIdAndUserId(user.getUserId(), 1l)).thenReturn(Optional.of(NotesMockHelper.NOTE1));
		noteService.updateNote(user.getUserId(), 1l, NotesMockHelper.NOTE1);
	}

	@Test
	public void deleteNote() {
		User user = NotesMockHelper.USER;
		Note note = Note.builder().title("Alex Note").message("Alex Note Detail").userDetails(user).build();
		when(noteRepo.findByNoteIdAndUserId(user.getUserId(), 1l)).thenReturn(Optional.of(note));
		String result = noteService.deleteNote(user.getUserId(), 1l);
		assertThat(result, is(String.format(ApplicationConstants.DELETE_ID, "1")));
	}
}
