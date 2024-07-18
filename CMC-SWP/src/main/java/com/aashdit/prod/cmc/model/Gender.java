package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.annotation.Sortable;
import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name = "t_mst_gender")
public class Gender extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = 832436664155432699L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gender_id")
	private Long genderId;

	@Column(name = "gender_code")
	private String genderCode;

	@Column(name = "gender_name_en")
	@Sortable(lang = "en")
	private String genderNameEN;

	@Column(name = "gender_name_hi")
	@Sortable(lang = "hi")
	private String genderNameHI;

	@Column(name = "is_active")
	private Boolean isActive;


}
