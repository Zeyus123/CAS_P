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

@Data
@Entity
@Table(name="t_adm_mst_department")
public class DepartmentMaster extends Auditable<User> implements Serializable {
	private static final long serialVersionUID = 285701719160134651L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;
	
	@Column(name="department_name")
	private String departmentName;
	
	@Column(name="short_name")
	private String departmentCode;
	
	@Column(name = "is_active")
    private Boolean isActive=true;
}
