package com.aashdit.prod.cmc.service.umt;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;

public interface RoleRightLevelService {

	ServiceOutcome<List<RoleRightLevelMaster>> getRoleRightLevelsByRoleId(Long roleId);
	
	ServiceOutcome<List<RoleRightLevelMaster>> getRoleRightLevels(Boolean includeInActive);

	ServiceOutcome<List<RoleRightLevelMaster>> getLevelPagedList();
	
	ServiceOutcome<RoleRightLevelMaster> fndByLevelId(Long levelId);
}
