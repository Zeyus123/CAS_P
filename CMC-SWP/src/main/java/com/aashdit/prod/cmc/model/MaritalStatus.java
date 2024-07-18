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
@Table(name = "t_mst_marital_status")
public class MaritalStatus extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -717126664705573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "marital_status_id")
	private Long maritalStatusId;

	@NotNull
	@Column(name = "is_active")
	private Boolean isActive;

	@NotNull
	@Column(name = "marital_status_name_en")
	private String maritalStatusName;

	@Column(name = "marital_status_code")
	private String maritalStatusCode;

}
