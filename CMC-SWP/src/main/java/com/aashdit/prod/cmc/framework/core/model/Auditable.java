package com.aashdit.prod.cmc.framework.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_by")
	@JsonIgnore
	private U createdBy;

	@CreatedDate
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createdOn;

	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "last_updated_by")
	@JsonIgnore
	private U lastUpdatedBy;

	@LastModifiedDate
	@Column(name = "last_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date lastUpdatedOn;

	public U getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public U getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(U lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

}