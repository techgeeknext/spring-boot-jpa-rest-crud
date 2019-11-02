package com.notes.util;

import java.util.ArrayList;
import java.util.List;

import com.notes.model.Note;
import com.notes.model.NoteBase;
import com.notes.model.User;

public class NotesMockHelper {

	public static final User USER = new User(1l,NotesConstants.NOTE_EMAIL,NotesConstants.NOTE_PASS);
	public static final Note NOTE1= Note.builder().title(NotesConstants.NOTE1_TITLE).message(NotesConstants.NOTE1_MSG).userDetails(USER).build();
	public static final Note NOTE2= Note.builder().title(NotesConstants.NOTE2_TITLE).message(NotesConstants.NOTE2_MSG).userDetails(USER).build();
	public static final Note NOTE3= Note.builder().title(NotesConstants.NOTE3_TITLE).message(NotesConstants.NOTE3_MSG).userDetails(USER).build();
	
	/**
	 * @return
	 * 
	 */
	public static List<Note> getNotes() {
		List<Note>notes = new ArrayList<>();
		notes.add(NOTE1);
		notes.add(NOTE2);
		notes.add(NOTE3);		
		return notes;
	}
	
	public static NoteBase getNoteBase(){
		NoteBase noteBase = new NoteBase();
		noteBase.setTitle(NotesConstants.NOTE1_TITLE);
		noteBase.setMessage(NotesConstants.NOTE1_MSG);
		return noteBase;
	}
	

}

