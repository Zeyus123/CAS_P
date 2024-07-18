package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name = "t_mst_category")
public class Caste extends Auditable<User> implements Serializable {
	private static final long serialVersionUID = -717126664705573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "caste_id")
	private Long casteId;

	@NotNull
	@Column(name = "is_active")
	private Boolean isActive;

	@NotNull
	@Column(name = "caste_code")
	private String casteCode;

	@NotNull
	@Column(name = "caste_name")
	private String casteName;

	@NotNull
	@Column(name = "caste_description")
	private String casteDescription;



}