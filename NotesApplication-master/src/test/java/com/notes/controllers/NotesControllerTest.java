package com.notes.controllers;

import static com.notes.util.NotesConstants.NOTE1_TITLE;
import static com.notes.util.NotesConstants.NOTE2_TITLE;
import static com.notes.util.NotesConstants.NOTE3_TITLE;
import static com.notes.util.NotesMockHelper.NOTE1;
import static com.notes.util.NotesMockHelper.USER;
import static com.notes.util.NotesMockHelper.getNoteBase;
import static com.notes.util.NotesMockHelper.getNotes;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.notes.controller.NotesController;
import com.notes.exception.NotesException;
import com.notes.model.Note;
import com.notes.service.NotesService;
import com.notes.util.ApplicationConstants;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ComponentScan(basePackageClasses = { NotesController.class })
@Profile("ControllerTest")
@ActiveProfiles("ControllerTest")
public class NotesControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private NotesService notesService;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void shouldReturnAllNotes() throws Exception {
		when(notesService.getAllNotesByUserId(USER.getUserId())).thenReturn(getNotes());
		mockMvc.perform(get("/api/notes/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].title", is(NOTE1_TITLE)))
				.andExpect(jsonPath("$[1].title", is(NOTE2_TITLE)))
				.andExpect(jsonPath("$[2].title", is(NOTE3_TITLE)));
		verify(notesService, VerificationModeFactory.times(1)).getAllNotesByUserId(USER.getUserId());
		reset(notesService);
	}

	@Test
	public void shouldReturnEmptyWhenNoNotes() throws Exception {
		when(notesService.getAllNotesByUserId(2l)).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/api/notes/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
		verify(notesService, VerificationModeFactory.times(1)).getAllNotesByUserId(2l);
		reset(notesService);
	}

	@Test
	public void shouldAddNote() throws Exception {
		Note note = NOTE1;
		when(notesService.addNote(null, note)).thenThrow(new NotesException("Provide user Id"));
		MockHttpServletResponse result = mockMvc
				.perform(post("/api/notes/ ").contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Alex Note\", \"message\":\"Alex Note Detail\"}"))
				.andReturn().getResponse();
		assertThat(result.getStatus(), is(400));
	}

	@Test
	public void shouldRemoveNote() throws Exception {
		when(notesService.deleteNote(USER.getUserId(), 1l))
				.thenReturn(String.format(ApplicationConstants.DELETE_ID, "1"));
		mockMvc.perform(delete("/api/notes/1/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(notesService, VerificationModeFactory.times(1)).deleteNote(1l, 1l);
		reset(notesService);
	}

	@Test
	public void shouldNotRemoveNoteForInvalidNoteOwner() throws Exception {
		when(notesService.deleteNote(3l, 1l)).thenThrow(new NotesException(ApplicationConstants.NOTE_ID_NOTEXIST));
		mockMvc.perform(delete("/api/notes/3/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		verify(notesService, VerificationModeFactory.times(1)).deleteNote(3l, 1l);
		reset(notesService);
	}

	@Test
	public void shouldUpdateNote() throws Exception {

		when(notesService.updateNote(1l, 1l, getNoteBase())).thenReturn(NOTE1);

		MockHttpServletResponse result = mockMvc
				.perform(put("/api/notes/1/1").contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Alex Note\", \"message\":\"Alex Note Detail\"}"))
				.andReturn().getResponse();
		assertThat(result.getStatus(), is(200));
		verify(notesService, VerificationModeFactory.times(1)).updateNote(1l, 1l, getNoteBase());
		reset(notesService);
	}

	@Test
	public void shouldNotUpdateNoteForNullUserId() throws Exception {

		when(notesService.updateNote(null, 1l, getNoteBase()))
				.thenThrow(new NotesException("Provide required Id"));

		MockHttpServletResponse result = mockMvc
				.perform(put("/api/notes/ /1").contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Alex Note\", \"message\":\"Alex Note Detail\"}"))
				.andReturn().getResponse();
		assertThat(result.getStatus(), is(400));
	}	
}
