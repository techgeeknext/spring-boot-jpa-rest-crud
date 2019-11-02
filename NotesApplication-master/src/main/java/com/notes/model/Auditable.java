package com.notes.model;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationDate", "lastModifiedDate"}, allowGetters = true)
public abstract class Auditable<U> {
		
	@Column(name = "created_date", updatable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	@CreatedDate
    protected Date creationDate;
	
	
	@Column(name = "lastMod_date")
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	@JsonIgnore
	protected Date lastModifiedDate;

}
