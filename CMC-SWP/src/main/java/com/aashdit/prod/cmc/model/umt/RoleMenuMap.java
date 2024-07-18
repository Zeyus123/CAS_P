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
@Table(name = "t_umt_role_menu_map")
public class RoleMenuMap extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -2468774430138794505L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_menu_map_id")
	private Long roleMenuId;
	
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name="menu_id")
	private Long menuId;
	
	
	
	@Column(name="isactive")
	private Boolean isActive;

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	

}
