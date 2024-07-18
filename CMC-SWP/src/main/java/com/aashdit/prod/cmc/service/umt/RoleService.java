package com.aashdit.prod.cmc.service.umt;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleLevelMap;
import com.aashdit.prod.cmc.model.umt.RoleMenuMap;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;

public interface RoleService {

	ServiceOutcome<List<Role>> getAllRoles(Boolean includeInactive);
	
	ServiceOutcome<List<Role>> getRoleForUser(Long userId);
	
	ServiceOutcome<Role> getRoleByCode(String roleCode);

	ServiceOutcome<Role> findByRoleId(Long roleId);

	ServiceOutcome<Role> lockNUnlockRoleById(Long roleId, Boolean isActive);

	ServiceOutcome<Role> addNupdateRole(Role role);

	ServiceOutcome<List<RoleLevelMap>> findRoleLevelList();

	ServiceOutcome<List<RoleLevelMap>> findRoleLevelListByRoleId(Long roleId);

	ServiceOutcome<List<RoleRightLevelMaster>> roleRightLevelList();

	ServiceOutcome<RoleLevelMap> addNupdateRoleLevelMap(Long[] maxAllocations, Long[] roleLevelId, Boolean[] status,	Long roleId);

	ServiceOutcome<RoleRightLevelMaster> getRoleRightLevelMasterById(Long levelId);

	ServiceOutcome<RoleLevelMap> findRoleLevelMapByRoleIdAndLevelId(Long roleId, Long levelId);
	
	ServiceOutcome<List<Role>> findRoleList();

	/* Angular Methods Below */
	ServiceOutcome<List<Role>> getRolePagedList();
	
	ServiceOutcome<Role> save(Role role);
	
	ServiceOutcome<Role> delete(Long roleId);
	
	ServiceOutcome<Boolean> allocateMenu(RoleMenuMap roleMenuMap);
	
	ServiceOutcome<Boolean> deAllocateMenu(RoleMenuMap roleMenuMap);

	ServiceOutcome<RoleLevelMap> allocateLevel(RoleLevelMap roleLevelMap);

	ServiceOutcome<RoleLevelMap> deAllocateLevel(RoleLevelMap roleLevelMap);


	
}
