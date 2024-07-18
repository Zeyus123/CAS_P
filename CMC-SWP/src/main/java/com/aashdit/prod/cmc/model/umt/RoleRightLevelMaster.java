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
@Table(name="t_umt_mst_role_right_level",  schema = "public")
public class RoleRightLevelMaster extends Auditable<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8680189488723686172L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mst_role_right_level_id")
	private Long roleRightLevelId;
	
	@Column(name = "level_code")
	private String levelCode;
	
	@Column(name ="level_entity_name")
	private String levelEntityName;
	
	@Column(name = "display_view_name")
	private String displayViewName;
	
	@Column(name = "primary_key_name")
	private String primaryKeyName;
	
	@Column(name ="display_columns")
	private String displayColumns;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "search_columns")
	private String searchColumns;

	public Long getRoleRightLevelId() {
		return roleRightLevelId;
	}

	public void setRoleRightLevelId(Long roleRightLevelId) {
		this.roleRightLevelId = roleRightLevelId;
	}

	public String getLevelEntityName() {
		return levelEntityName;
	}

	public void setLevelEntityName(String levelEntityName) {
		this.levelEntityName = levelEntityName;
	}

	public String getDisplayViewName() {
		return displayViewName;
	}

	public void setDisplayViewName(String displayViewName) {
		this.displayViewName = displayViewName;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public String getDisplayColumns() {
		return displayColumns;
	}

	public void setDisplayColumns(String displayColumns) {
		this.displayColumns = displayColumns;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(String searchColumns) {
		this.searchColumns = searchColumns;
	}
	
	
	
}
