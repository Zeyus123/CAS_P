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
@Table(name = "t_mst_religion")
public class ReligionMaster extends Auditable<User> implements Serializable {
	
	private static final long serialVersionUID = 832436664155432699L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "religion_id")
	private Long religionId;

	@NotNull
	@Column(name = "religion_name")
	private String religionName;
	
	@NotNull
	@Column(name = "religion_description")
	private String religionDescription;

	
	@Column(name = "is_active")
	private Boolean isActive;

}
