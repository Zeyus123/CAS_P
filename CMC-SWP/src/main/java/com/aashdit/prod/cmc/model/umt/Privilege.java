package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.misc.umt.PrivilegeType;

@Entity
@Table(name = "t_umt_mst_privilege")
public class Privilege extends Auditable<User> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7469840183397935224L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="mst_privilege_id")
	private Long id;

	@Column(name="privilege_code")
	@OrderBy
	private String code;
	
	@Column(name="privilege_descr")
	private String desc;
	
	
	@Column(name="is_active")
	private Boolean isActive;
	
	@Column(name="priv_type")
	@Enumerated(EnumType.STRING)
	private PrivilegeType priviledgeType; // HEADER | ROW | FOOTER
	
	@Column(name="ui_label_code")
	private String uiLabelCode; 
	
	

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public PrivilegeType getPriviledgeType() {
		return priviledgeType;
	}

	public void setPriviledgeType(PrivilegeType priviledgeType) {
		this.priviledgeType = priviledgeType;
	}

	public String getUiLabelCode() {
		return uiLabelCode;
	}

	public void setUiLabelCode(String uiLabelCode) {
		this.uiLabelCode = uiLabelCode;
	}

	


	
	
	
}
