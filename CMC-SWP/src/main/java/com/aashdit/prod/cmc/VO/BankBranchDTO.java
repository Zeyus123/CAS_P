package com.aashdit.prod.cmc.VO;

import java.util.List;

import com.aashdit.prod.cmc.model.BankBranchMaster;

import lombok.Data;
@Data
public class BankBranchDTO {

	   private BankBranchMaster bankBranchMaster;
	   private List<BankBranchMaster> bankBranchMasterList;

}
