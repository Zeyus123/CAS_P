package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.VO.BankBranchDTO;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.BankMaster;
import com.aashdit.prod.cmc.model.DepartmentMaster;

public interface AdministrationService {
	

	List<DepartmentMaster> getAllDeptList(Boolean dependStatus);
	
	ServiceOutcome<DepartmentMaster> saveDepartment(DepartmentMaster dept);
	
	ServiceOutcome<DepartmentMaster> editDepartment(Long deptId);
	
	ServiceOutcome<String> updateDepartment(Long deptId, Boolean status);
	
	ServiceOutcome<DepartmentMaster> manageDepartment();

	ServiceOutcome<BankBranchDTO> editBranchMaster(BankBranchDTO bankBranchDTO);

	ServiceOutcome<BankBranchDTO> getBranchMasterData();

	List<BankMaster> getAllBankNames();

	ServiceOutcome<BankBranchDTO> saveBankBranchData(BankBranchDTO bankBranchDTO, String status);

	List<BankMaster> getAllBankMasterList();
	
	ServiceOutcome<String> saveBankMaster (BankMaster bm);
	
	ServiceOutcome<BankMaster> editBankMaster (Long bankId);
	
	ServiceOutcome<String> updateBankMasterStatus(Long bankId, Boolean status);

	
	
	
	ServiceOutcome<String> updateStatusDept(Long deptId, Boolean status);

	ServiceOutcome<String> checkStatusOfBranch(Long bankBranchId, Boolean status);

}
