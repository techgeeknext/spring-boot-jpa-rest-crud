package com.notes.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.notes.exception.NotesException;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class NotesExceptionHandler {

	public NotesExceptionHandler() {
		super();
	}

	/**
	 * Method to return Notes specific exception.
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(NotesException.class)
	private final ResponseEntity<Object> handleUserNotFoundException(NotesException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Generic Exception
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	private final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}