package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Entity
@Table(name="app_config_parameters")
@Data
public class AppConfigParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3734971300484592659L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_id")
	private Long paramId;

	@Column(name="param_description")
	private String paramDesc;

	@Column(name="param_code")
	private String paramCode;

	@Column(name="param_value")
	private String paramValue;
}
