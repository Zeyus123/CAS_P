package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="t_umt_role", schema = "public")
public class Role extends Auditable<User> implements Serializable {
	private static final long serialVersionUID = 285701719160134651L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "role_code")
	private String roleCode;
	
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "display_on_page")
	private Boolean displayOnPage;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	
	
	@Column(name = "max_assignments")
	private Long maxAssignments;

	
	
	@Column(name = "is_designation")
	private Boolean isDesignation;
	
	
	
	@Column(name = "role_level")
	private Long roleLevel;
	
	
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDisplayOnPage() {
		return displayOnPage;
	}

	public void setDisplayOnPage(Boolean displayOnPage) {
		this.displayOnPage = displayOnPage;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public Long getMaxAssignments() {
		return maxAssignments;
	}

	public void setMaxAssignments(Long maxAssignments) {
		this.maxAssignments = maxAssignments;
	}

	public Boolean getIsDesignation() {
		return isDesignation;
	}

	public void setIsDesignation(Boolean isDesignation) {
		this.isDesignation = isDesignation;
	}

	public Long getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Long roleLevel) {
		this.roleLevel = roleLevel;
	}
	
} 
  