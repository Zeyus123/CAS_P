package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "t_util_mail_queue")
public class MailQueued implements Serializable {

	private static final long serialVersionUID = 5034477311179983988L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entry_id")
	private Long entryId;

	@Column(name = "mail_to")
	private String mailTo;

	@Column(name = "mail_from")
	private String mailFrom;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body")
	private String body;

	@Column(name = "body_type")
	private String bodyType;

	@Column(name = "status")
	private String status;

	@Column(name = "failure_reason")
	private String failureReason;

	@CreatedDate
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createdOn;
	
	
	@Column(name = "updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date updatedOn;

}
