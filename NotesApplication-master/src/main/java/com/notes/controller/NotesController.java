package com.notes.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.exception.NotesException;
import com.notes.exception.NotesExceptionHandler;
import com.notes.model.Note;
import com.notes.model.NoteBase;
import com.notes.service.NotesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/api")
@Api("Notes Crud App")
@Log4j2
public class NotesController extends NotesExceptionHandler {

	@Autowired
	private NotesService notesService;

	/**
	 * Method to add note Validate Note Request object format/values in case of
	 * null/empty
	 * 
	 * @param userId
	 * @param note
	 * @param errors
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Add New Note")
	@PostMapping("/notes/{userId}")
	@ApiModelProperty(hidden = true)
	public ResponseEntity<String> addNote(@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody NoteBase note, Errors errors) {
		log.info("Inside Add Note controller.");
		userIdNullCheck(userId);
		validateRequest(errors);
		String response = notesService.addNote(userId, note);
		log.info("Response of Add Note : " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Method to get all Notes details
	 * 
	 * @param userId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "View list of Notes by User Id")
	@GetMapping("/notes/{userId}")
	public ResponseEntity<List<Note>> getAllNotesByUser(@PathVariable(value = "userId") Long userId) {
		log.info("Inside getAllNotesByUser controller.");
		userIdNullCheck(userId);
		List<Note> notes = notesService.getAllNotesByUserId(userId);
		log.info("Response of getAllNotesByUser : " + notes);
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}

	/**
	 * Method to delete Note
	 * 
	 * @param userId
	 * @param noteId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Note")
	@DeleteMapping("/notes/{userId}/{noteId}")
	public ResponseEntity<String> deleteNote(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "noteId") Long noteId) {
		log.info("Inside deleteNote controller.");
		userIdNullCheck(userId);
		String response = notesService.deleteNote(userId, noteId);
		log.info("Response of deleteNote : " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Method to update employee details
	 * 
	 * @param employee
	 * @param id
	 * @param errors
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Note")
	@PutMapping("/notes/{userId}/{noteId}")
	public ResponseEntity<Note> updateNote(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "noteId") Long noteId, @Valid @RequestBody NoteBase note, Errors errors) {

		validateRequest(errors);
		log.info("Inside  controller:: updateNote.");
		Note updatedNote = notesService.updateNote(userId, noteId, note);
		log.info("Response of updateNote : " + updatedNote);
		return new ResponseEntity<>(updatedNote, HttpStatus.OK);
	}

	/**
	 * @param userId
	 */
	private void userIdNullCheck(Long userId) {
		if (userId == null)
			throw new NotesException("Provide user Id");
	}

	/**
	 * Validate Request
	 * 
	 * @param errors
	 */
	private void validateRequest(Errors errors) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(errorMsg -> {
				log.debug("Error Message : " + errorMsg);
				throw new NotesException(errorMsg.getDefaultMessage());
			});
		}
	}
}
