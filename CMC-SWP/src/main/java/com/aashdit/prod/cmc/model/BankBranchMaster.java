package com.aashdit.prod.cmc.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name="t_adm_mst_bank_branch")	
public class BankBranchMaster extends Auditable<User>{
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    private Long bankBranchId;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankMaster bankId;
    
	@NotNull
    @Column(name = "branch_name")
    private String branchName;
    
	@NotNull
    @Column(name = "ifsc_code")
    private String ifscCode;
    
    @Column(name = "branch_address")
    private String branchAddress;
    
    @Column(name = "contact_no")
    private String branchMobile;
    
    @Column(name = "email_id")
    private String branchEmail;
    
    @Column(name = "is_active")
    private Boolean isActive=true;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_on")
    private Date createdOn;
    
    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    private User lastUpdatedBy;
    
    @Column(name = "last_updated_on")
    private Date lastUpdatedOn;
}
