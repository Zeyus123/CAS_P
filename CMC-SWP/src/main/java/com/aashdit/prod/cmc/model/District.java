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
@Table(name = "t_mst_district")
@Data
public class District implements Serializable {

	private static final long serialVersionUID = 8185420005548101716L;

	@Id
	@Column(name = "district_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long districtId;
	
	@NotNull
	@Column(name = "district_name")
	private String districtName;
	
	@NotNull
	@Column(name = "district_code")
	private String districtCode;

//	@Column(name = "remarks")
//	private String remarks;
	
	@Column(name = "is_active")
	private Boolean isActive;

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
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private State stateId;
	
}
	