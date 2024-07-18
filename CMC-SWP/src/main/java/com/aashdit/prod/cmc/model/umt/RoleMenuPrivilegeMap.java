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
@Table(name = "t_umt_role_menu_privilege_map", schema="public")
public class RoleMenuPrivilegeMap extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -4605255365242118589L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_menu_privilege_map_id")
	private Long roleMenuPrivilegeId;
	
	@Column(name="role_menu_id")
	private Long roleMenuId;
	
	@Column(name="privilege_id")
	private Long privilegeId;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	

	public Long getRoleMenuPrivilegeId() {
		return roleMenuPrivilegeId;
	}

	public void setRoleMenuPrivilegeId(Long roleMenuPrivilegeId) {
		this.roleMenuPrivilegeId = roleMenuPrivilegeId;
	}

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	
	

}
