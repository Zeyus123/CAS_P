package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;

@Entity
@Table(name="t_umt_user_role_right_level",  schema = "public")
public class UserRoleLevelAccess extends Auditable<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4331702413271572199L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_right_level_id")
	private Long userRoleRightLevelId;
	
	@Column(name = "role_level_id")
	private Long roleLevelId;
	
	@Column(name = "user_role_id")
	private Long userRoleId;
	
	@Column(name = "object_id")
	private Long objectId;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	

	public Long getUserRoleRightLevelId() {
		return userRoleRightLevelId;
	}

	public void setUserRoleRightLevelId(Long userRoleRightLevelId) {
		this.userRoleRightLevelId = userRoleRightLevelId;
	}

	public Long getRoleLevelId() {
		return roleLevelId;
	}

	public void setRoleLevelId(Long roleLevelId) {
		this.roleLevelId = roleLevelId;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	
	

}
