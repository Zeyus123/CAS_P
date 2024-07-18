package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;

import lombok.Data;


@Entity
@Data
@Table(name="t_umt_user_role_map", schema = "public")
public class UserRoleMap extends Auditable<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7929712527386164424L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_map_id")
	private Long userRoleId;
	
	@Column(name ="user_id")
	private Long userId;
	
	@Column(name ="role_id")
	private Long roleId;
	
	@Column(name ="object_type")
	private String objectType;
	
	@Column(name ="object_type_id")
	private Long objectTypeId;
	
	@Column(name="is_active")
	private Boolean isActive;

	
	

}
