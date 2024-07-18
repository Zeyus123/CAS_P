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

import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name = "t_mst_municipality")
public class Municipality implements Serializable {

	private static final long serialVersionUID = 3564519581695874915L;

	@Id
	@Column(name = "municipality_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long municipalityId;

	@Column(name = "municipality_name")
	private String municipalityName;
	
	@Column(name = "municipality_code")
	private String municipalityCode;

	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;
	
	
	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "remarks")
	private String remarks;
	

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
	