package com.notes.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Notes")
public class Note extends NoteBase implements Serializable   {	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiParam(hidden = true)
	@Column(name="note_Id")
	private Long noteId;
	
	
	/*In case of we need to maintain many notes to one User relation through User Entity*/
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userDetails;

	@Builder
	public Note(String title, String message, User userDetails) {
		super(title, message);
		this.userDetails = userDetails;
	}	
}


