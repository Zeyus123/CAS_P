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
@Table(name = "t_hr_pr_employment_status")
public class EmploymentStatus extends Auditable<User> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4543863772407221533L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="employment_id")
	private Long employmentId;
	
	@Column(name="employment_name")
	private String employmentName;
	
	@Column(name="employment_code")
	private String employmentCode;
	
	@Column(name = "display_order")
    private Integer displayOrder;

	@Column(name = "is_active")
    private Boolean isActive;
	
	@Column(name = "show_in_payroll")
    private Boolean showInPayroll;
	
	@Column(name = "show_in_staff_mst")
    private Boolean showInStaffMst;
	
}
