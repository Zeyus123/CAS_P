package com.aashdit.prod.cmc.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name="t_adm_mst_bank")	
public class BankMaster extends Auditable<User> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4611086012829679225L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
    @Column(name = "bank_id", nullable = false)
    private Long bankId;
    
	@NotNull
    @Column(name = "bank_name")
    private String bankName;
    
	@NotNull
    @Column(name = "short_name")
    private String shortName;
    
    @Column(name = "is_active")
    private Boolean isActive=true;
    
    
}
