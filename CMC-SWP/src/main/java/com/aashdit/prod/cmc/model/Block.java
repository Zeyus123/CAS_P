package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "t_mst_block")
@Data
public class Block extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -4103610485238395024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "block_id")
	private Long blockId;

	@Column(name = "block_code")
	private String blockCode;

	@Column(name = "block_name_en")
	@Sortable(lang = "en")
	private String blockNameEN;

	@Column(name = "block_name_hi")
	@Sortable(lang = "hi")
	private String blockNameHI;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private District district;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "block_lgd_code")
	@Sortable(lang = "lgd")
	private String blockLgdCode;

	@Column(name = "block_census_code")
	@Sortable(lang = "census")
	private String blockCensusCode;
	
	@Column(name = "block_tribal")
	private Boolean blockTribal;
	


}
