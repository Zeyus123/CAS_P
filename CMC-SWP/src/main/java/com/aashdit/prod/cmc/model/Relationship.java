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

@Entity
@Data
@Table(name= "t_mst_relationship")
public class Relationship extends  Auditable<User> implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "relationship_id")
	private Long relationshipId;

	@NotNull
	@Column(name = "relationship_name")
	private String relationshipName;
	
	@NotNull
	@Column(name = "relationship_description")
	private String relationshipDescription;

	
	@Column(name = "is_active")
	private Boolean isActive;

}
