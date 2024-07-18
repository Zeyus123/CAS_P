package com.aashdit.prod.cmc.service.umt;

import java.util.List;

import com.aashdit.prod.cmc.VO.CommonNameIdDto;
import com.aashdit.prod.cmc.VO.umt.AccesslevelConfigMetaInfo;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.misc.umt.AccessLevelType;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;

public interface AccessService {

	<T> ServiceOutcome<List<T>> getByRoleLevel(Long userId, Long roleId, AccessLevelType accessLevel, Class<T> returnType);
	
	ServiceOutcome<AccesslevelConfigMetaInfo> getACLConfigData(Long userId, Long roleId, Long roleRightLevelId, String searchTerm , Integer page,  Integer size);
	
	ServiceOutcome<String> saveConfig(Long userId, Long roleId, Long roleRightLevelId, Long objectId);

	<T> ServiceOutcome<List<T>> getByRoleLevel(Long userId, Long roleId, String accessLevelCode, Class<T> returnType);
	
	/* API Methods */
	ServiceOutcome<String> allocate(Long userId, Long roleId, Long roleRightLevelId, Long objectId);
	
	ServiceOutcome<String> deAllocate(Long userId, Long roleId, Long roleRightLevelId, Long objectId);
	
	ServiceOutcome<User> getUsersWithAccess(Long entityId, String roleLevelCode, Long roleId);
	
	ServiceOutcome<List<Role>> findRolesFromUserRoleMap(Long userId);

	ServiceOutcome<UserRoleMap> getUserRoleMapByMapId(Long userRoleMapId);

//	ServiceOutcome<List<CommonNameIdDto>> getAccessListToShowInDashboard(User user);

	ServiceOutcome<List<Object[]>> getCoreDataByQuery(String encValue);
}
