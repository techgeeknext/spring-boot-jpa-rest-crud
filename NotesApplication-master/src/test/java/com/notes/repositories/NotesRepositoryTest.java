package com.notes.repositories;

import static com.notes.util.NotesConstants.NOTE1_MSG;
import static com.notes.util.NotesConstants.NOTE1_TITLE;
import static com.notes.util.NotesConstants.NOTE2_MSG;
import static com.notes.util.NotesConstants.NOTE2_TITLE;
import static com.notes.util.NotesConstants.NOTE3_MSG;
import static com.notes.util.NotesConstants.NOTE3_TITLE;
import static com.notes.util.NotesMockHelper.USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.model.Note;
import com.notes.repository.NotesRepository;

//@RunWith(SpringRunner.class)
//@Profile("RepositoryTest")
//@ActiveProfiles("RepositoryTest")
//@DataJpaTest
public class NotesRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NotesRepository notesRepository;


	//@Test
	public void whenInvalidUser_thenReturnNull() {
		List<Note> fromDb = notesRepository.findNotesByUserId(111l);
		assertThat(fromDb).isEmpty();
	}
	
	//@Test
	public void whenFindById_thenReturnNote() {
		
		Note note = Note.builder().title("My Note 2").message("The summary of todays meeting is ...").userDetails(USER).build();
		entityManager.persistAndFlush(note);

		Note fromDb = notesRepository.findById(note.getNoteId()).orElse(null);
		assertThat(fromDb.getTitle()).isEqualTo(note.getTitle());
	}

	//@Test
	public void whenInvalidId_thenReturnNull() {
		Note fromDb = notesRepository.findById(-11l).orElse(null);
		assertThat(fromDb).isNull();
	}

	//@Test
	public void givenSetOfNotes_whenFindAll_thenReturnAllNotes() {
		entityManager.persist(Note.builder().title(NOTE1_TITLE).message(NOTE1_MSG).userDetails(USER).build());
		entityManager.persist(Note.builder().title(NOTE2_TITLE).message(NOTE2_MSG).userDetails(USER).build());
		entityManager.persist(Note.builder().title(NOTE3_TITLE).message(NOTE3_MSG).userDetails(USER).build());
		entityManager.flush();

		List<Note> allNotes = notesRepository.findNotesByUserId(USER.getUserId());

		assertThat(allNotes).hasSize(3).extracting(Note::getTitle).containsOnly(NOTE1_TITLE, NOTE2_TITLE,
				NOTE3_TITLE);
	}
	
	

}
