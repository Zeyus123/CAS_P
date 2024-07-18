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
@Table(name = "t_mst_blood_group")
public class BloodGroup extends Auditable<User> implements Serializable {
	
	private static final long serialVersionUID = 832436664155432699L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blood_group_id")
	private Long bloodGroupId;

	@NotNull
	@Column(name = "blood_group_name")
	private String bloodGroupName;
	
	@NotNull
	@Column(name = "blood_group_description")
	private String bloodGroupDescription;

	
	@Column(name = "is_active")
	private Boolean isActive;

}
