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
@Table(name = "t_mst_village")
@Data
public class Village extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -4103610485238395024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "village_id")
	private Long villageId;

	@Column(name = "village_name_en")
	@Sortable(lang = "en")
	private String villageNameEn;

	@Column(name = "village_name_hi")
	@Sortable(lang = "hi")
	private String villageNameHi;

	@Column(name = "village_code")
	private String villageCode;

	@ManyToOne
	@JoinColumn(name = "gp_id")
	private Grampanchayat gpId;

	@Column(name = "village_remarks")
	private String villageRemarks;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "village_lgd_code")
	@Sortable(lang = "lgd")
	private String villageLgdCode;

	@Column(name = "village_census_code")
	@Sortable(lang = "census")
	private String villageCensusCode;
	
	@Column(name = "village_tribal")
	private Boolean villageTribal;

}
