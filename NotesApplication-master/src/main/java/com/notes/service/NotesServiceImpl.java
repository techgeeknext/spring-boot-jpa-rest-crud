package com.notes.service;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.exception.NotesException;
import com.notes.model.Note;
import com.notes.model.NoteBase;
import com.notes.model.User;
import com.notes.repository.NotesRepository;
import com.notes.repository.UserRepository;
import com.notes.util.ApplicationConstants;

import lombok.extern.log4j.Log4j2;
/**
 *
 * Service Implementation class for note crud operations.
 */
@Service
@Log4j2
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Get all notes associated by user id. check for valid user.
	 *
	 * @param userId
	 * @return object
	 */
	@Override
	public List<Note> getAllNotesByUserId(Long userId) {
		log.info("Inside get all notes by user id service");
		validateUser(userId);
		return noteRepository.findNotesByUserId(userId);
	}

	/**
	 * Adding note by validating user id
	 *
	 * @param note
	 * @param userId
	 * @return
	 */
	@Override
	public String addNote(Long userId, NoteBase note) {
		log.info("Inside add note service");
		User user = validateUser(userId);
		return saveUser(user, note);
	}

	@Override
	public Note updateNote(Long userId, Long noteId, @Valid NoteBase note) {
		Note dbNote = validateNoteByUser(userId, noteId);
		dbNote.setTitle(note.getTitle());
		dbNote.setMessage(note.getMessage());
		return noteRepository.save(dbNote);
	}

	/**
	 * Delete Note
	 *
	 * @param noteId
	 * @return
	 */
	public String deleteNote(Long userId, Long noteId) {
		Note dbNote = validateNoteByUser(userId, noteId);
		noteRepository.deleteById(noteId);
		return String.format(ApplicationConstants.DELETE_ID, noteId);
	}

	/**
	 * Method to save Note
	 *
	 * @param user
	 * @param note
	 * @return
	 */
	private String saveUser(User user, NoteBase note) {
		Note dbNote = Note.builder().title(note.getTitle()).message(note.getMessage()).userDetails(user).build();
		noteRepository.save(dbNote);
		log.info("Successfully added new Note.");
		return ApplicationConstants.ADDED_NOTE_DESC;
	}

	private User validateUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NotesException(ApplicationConstants.VALIDATION_USER_ID));
	}

	private Note validateNoteByUser(Long userId, Long noteId) {
		return noteRepository.findByNoteIdAndUserId(noteId, userId)
				.orElseThrow(() -> new NotesException(ApplicationConstants.NOTE_ID_NOTEXIST));
	}

}
