package com.notes.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.notes.model.Note;
public interface NotesRepository extends CrudRepository<Note, Long>{

	@Query("select note from Note note where note.userDetails.userId = :userId")
	List<Note> findNotesByUserId(@Param("userId") Long userId);
	
	@Query("select note from Note note where note.userDetails.userId = :userId and note.noteId = :noteId")
	Optional<Note> findByNoteIdAndUserId(@Param("noteId") Long noteId, @Param("userId") Long userId);
}