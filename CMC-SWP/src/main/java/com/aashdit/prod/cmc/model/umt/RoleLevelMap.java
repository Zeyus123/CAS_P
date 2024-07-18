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
@Table(name="t_umt_role_level_map", schema = "public")
public class RoleLevelMap extends Auditable<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225796378784615709L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_level_map_id")
	private Long roleLevelId;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "role_right_level_id")
	private Long levelId;
		
	
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	
	@Column(name = "max_allocations")
	private Long maxAllocations;

	public Long getRoleLevelId() {
		return roleLevelId;
	}

	public void setRoleLevelId(Long roleLevelId) {
		this.roleLevelId = roleLevelId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public Long getMaxAllocations() {
		return maxAllocations;
	}

	public void setMaxAllocations(Long maxAllocations) {
		this.maxAllocations = maxAllocations;
	}

}
