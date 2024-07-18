package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.annotation.Sortable;
import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
@Entity
@Table(name = "t_mst_gramapanchayat")
@Data
public class Grampanchayat extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = 3736657245464159277L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gp_id")
	private Long gpId;

	@Column(name = "gp_code")
	private String gpCode;

	@Column(name = "gp_name_en")
	@Sortable(lang = "en")
	private String gpNameEN;

	@Column(name = "gp_name_hi")
	@Sortable(lang = "hi")
	private String gpNameHI;

	@ManyToOne
	@JoinColumn(name = "block_id")
	private Block block;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "gp_lgd_code")
	@Sortable(lang = "lgd")
	private String gpLgdCode;

	@Column(name = "gp_census_code")
	@Sortable(lang = "census")
	private String gpCensusCode;
	
	@Column(name = "gp_tribal")
	private Boolean gpTribal;

}
