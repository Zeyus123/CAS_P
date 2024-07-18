package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.VO.BankBranchMasterVo;
import com.aashdit.prod.cmc.VO.BankMasterVO;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.BankBranchMaster;
import com.aashdit.prod.cmc.model.Block;
import com.aashdit.prod.cmc.model.DepartmentMaster;
import com.aashdit.prod.cmc.model.District;
import com.aashdit.prod.cmc.model.EmploymentStatus;
import com.aashdit.prod.cmc.model.FinancialYear;
import com.aashdit.prod.cmc.model.umt.Role;

public interface CommonService {

	ServiceOutcome<List<District>> findDistrictListByStateId(Long stateId);

	List<Block> getAllBlockList(Long distId);

	List<District> getAllDistrictNameList();

	ServiceOutcome<List<BankBranchMaster>> findByBankId(Long bankId);

	ServiceOutcome<String> getIfscByBranchId(Long branchId);

	ServiceOutcome<String> getConfigParameterValueFromParamCode(String paramCode);

	List<FinancialYear> getAllFinancialYearList();


	ServiceOutcome<List<Role>> getUpperLevelRoleListByRoleId(Long roleId);

	ServiceOutcome<List<Role>> getLowerLevelRoleListByRoleId(Long roleId);


	ServiceOutcome<List<BankMasterVO>> getAllBankList();

	ServiceOutcome<List<BankBranchMasterVo>> getAllBranchByBankId(Long bankId);


;

}
