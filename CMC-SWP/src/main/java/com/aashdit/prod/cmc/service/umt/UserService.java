package com.aashdit.prod.cmc.service.umt;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aashdit.prod.cmc.VO.CurrentUserVo;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	ServiceOutcome<User> save(User user);
	
	ServiceOutcome<User> findByUsername(String userName);
	
	ServiceOutcome<User> findByUserId(Long userId);

	ServiceOutcome<Page<User>> findUserList(String searchTerm, PageRequest pageRequest);

	ServiceOutcome<User> lockNUnlockUserById(Long id, Boolean status);
	
	ServiceOutcome<List<User>> searchUser(String name);

	List<Role> findActiveRole();

	//ServiceOutcome<User> updateUser(Long userId, User user);

	ServiceOutcome<User> updateUser(Long userId, String userName, String firstName, String lastName, Date dateOfBirth,
			String mobile, String email, Long[] userRoleHcMapId, Long[] roleName, Long[] isPrimary, String[] status, String designation);


	List<UserRoleMap> findUserRoleMapByUserId(Long userId);

	ServiceOutcome<User> addUser(String username, String firstname, String lastname,
			Date dateOfbirth, String userMobile, String userEmail,  Long[] roleId, 	Long[] isPrimary, String designation, String userType, String level, Long levelId, Long[] objectTypeId, String staffCode);

	ServiceOutcome<User> userRegistration(User user);
	
	ServiceOutcome<List<User>> getUsersByLevelAndId(Long roleLevelId, Long entityId);
	
	//ServiceOutcome<List<User>> getUsersByRoleAndLevelAndId(Long roleId, Long roleLevelId, Long entityId);
	
	ServiceOutcome<List<User>> getAllUsers();
	
	ServiceOutcome<User> updateProfile(User user, MultipartFile userProfileImage);

	Boolean saveResetPassword(Long userId, String txtRePass);
	
	ServiceOutcome<Boolean> createLoginHistory(User user , HttpServletRequest request);
	
	ServiceOutcome<Boolean> createLogoutHistory(User user , HttpServletRequest request);


	ServiceOutcome<CurrentUserVo> getUserObjectUserListRoleObject(Long userId,String userName,String benfCode);

	ServiceOutcome<Role> getRoleObjectByRoleCode(String roleCode);

	ServiceOutcome<Role> getRoleObjectByRoleId(Long roleId);

	ServiceOutcome<List<CurrentUserVo>> getUserListByRoleIdAndObjIdAndLevel(Long roleId, Long userLevelId,
			String userLevel, String appCode);
}
