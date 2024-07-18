package com.aashdit.prod.cmc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;
@Entity
@Data
@Table(name = "t_mst_ward")
public class Ward implements Serializable{

	private static final long serialVersionUID = -1509707394914520208L;

	@Id
	@Column(name = "ward_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long wardId;
	
	@NotNull
	@Column(name = "ward_code")
	private String wardCode;
	
	@NotNull
	@Column(name = "ward_name")
	private String wardName;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name="municipality_id")
	private Municipality municipality;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@Column(name = "last_updated_on")
	private Date lastUpdatedOn;
	
	@ManyToOne
	@JoinColumn(name = "last_updated_by")
	private User lastUpdatedBy;
	
}
