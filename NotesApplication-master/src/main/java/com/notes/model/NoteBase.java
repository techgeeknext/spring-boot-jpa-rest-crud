package com.notes.model;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.notes.util.ApplicationConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "title", "noteMsg" })
@MappedSuperclass
public class NoteBase extends Auditable<String> {

	@NotEmpty(message = ApplicationConstants.VALIDATION_TITLE_EMPTY)
	@NotBlank(message = ApplicationConstants.VALIDATION_TITLE_EMPTY)
	@Size(max = 50)
	private String title;

	@Size(max = 1000)
	private String message;

}
