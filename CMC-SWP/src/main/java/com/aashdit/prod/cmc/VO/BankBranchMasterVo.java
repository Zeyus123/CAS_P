package com.aashdit.prod.cmc.VO;

import com.aashdit.prod.cmc.model.BankBranchMaster;

import lombok.Data;

@Data
public class BankBranchMasterVo {

   


	private Long bankBranchId;
	
    private Long bankId;
    
    private String branchName;
    

    private String ifscCode;
    
    private String branchAddress;
    
    private String branchMobile;
    
  
    private String branchEmail;


	public BankBranchMasterVo(BankBranchMaster data) {
		this.bankBranchId = data.getBankBranchId();
		this.bankId = data.getBankId().getBankId();
		this.branchName = data.getBranchName();
		this.ifscCode = data.getIfscCode();
		this.branchAddress = data.getBranchAddress();
		this.branchMobile = data.getBranchMobile();
		this.branchEmail = data.getBranchEmail();
	}
    
    
    
    
}
