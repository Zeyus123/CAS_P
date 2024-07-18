package com.aashdit.prod.cmc.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleLevelMap;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.model.umt.UserRoleRightLevel;
import com.aashdit.prod.cmc.repository.umt.RoleLevelMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRightLevelMasterRepository;
import com.aashdit.prod.cmc.repository.umt.UserRoleMapRepository;
import com.aashdit.prod.cmc.repository.umt.UserRoleRightLevelRepository;
	

	@Component
	public class UserDemographyUtil {
		
		@Autowired
		private UserRoleMapRepository userRoleMapRepository; 
		
		@Autowired
		private UserRoleRightLevelRepository userRoleRightLevelRepository; 
		
		@Autowired
		private RoleLevelMapRepository roleLevelMapRepository; 
		
		@Autowired
		private RoleRightLevelMasterRepository roleRightLevelMasterRepository; 
		
		public Long getDemographyId(User user) {
			
			Long demographyId = null;
			Role primaryRole = user.getPrimaryRole(); 
			if(primaryRole != null) {
				Long roleId = primaryRole.getRoleId(); 
				UserRoleMap userRoleMap = userRoleMapRepository.findByUserIdAndRoleId(user.getUserId(), roleId); 
				if(userRoleMap != null) {
					Long userRoleId = userRoleMap.getUserRoleId();
					List<UserRoleRightLevel> userRoleRightLevelList = new ArrayList<UserRoleRightLevel>(); 
					List<UserRoleRightLevel> allUserRoleRightLevelList = userRoleRightLevelRepository.findAll(); 
					if(allUserRoleRightLevelList != null && allUserRoleRightLevelList.size() > 0) { 
						for(UserRoleRightLevel userRoleRightLevel:allUserRoleRightLevelList) {
							if(userRoleRightLevel.getUserRoleId().equals(userRoleId) && userRoleRightLevel.getIsActive() == true) { 
								userRoleRightLevelList.add(userRoleRightLevel); 
							}
						}
					}
					if(userRoleRightLevelList != null && userRoleRightLevelList.size() > 0) { 
						UserRoleRightLevel userRoleRightLevel = userRoleRightLevelList.get(0); 
						Long roleLevelId = userRoleRightLevel.getRoleLevelId(); 
						RoleLevelMap roleLevelMap = roleLevelMapRepository.findById(roleLevelId).get(); 
						if(roleLevelMap != null) {
							Long roleRightLevelId = roleLevelMap.getLevelId();
							RoleRightLevelMaster roleRightLevelMaster = roleRightLevelMasterRepository.findById(roleRightLevelId).get();
							if(roleRightLevelMaster != null) {
								demographyId = userRoleRightLevel.getObjectId(); 
							}
						}
					}
				}
			}
			
			return demographyId; 
		}
		
		public String getRoleLevelCode(User user) {
			
			String roleLevelCode = null;
			Role primaryRole = user.getPrimaryRole(); 
			if(primaryRole != null) {
				Long roleId = primaryRole.getRoleId(); 
				UserRoleMap userRoleMap = userRoleMapRepository.findByUserIdAndRoleId(user.getUserId(), roleId); 
				if(userRoleMap != null) {
					Long userRoleId = userRoleMap.getUserRoleId();
					List<UserRoleRightLevel> userRoleRightLevelList = new ArrayList<UserRoleRightLevel>(); 
					List<UserRoleRightLevel> allUserRoleRightLevelList = userRoleRightLevelRepository.findAll(); 
					if(allUserRoleRightLevelList != null && allUserRoleRightLevelList.size() > 0) { 
						for(UserRoleRightLevel userRoleRightLevel:allUserRoleRightLevelList) {
							if(userRoleRightLevel.getUserRoleId().equals(userRoleId) && userRoleRightLevel.getIsActive() == true) { 
								userRoleRightLevelList.add(userRoleRightLevel); 
							}
						}
					}
					if(userRoleRightLevelList != null && userRoleRightLevelList.size() > 0) { 
						UserRoleRightLevel userRoleRightLevel = userRoleRightLevelList.get(0); 
						Long roleLevelId = userRoleRightLevel.getRoleLevelId(); 
						RoleLevelMap roleLevelMap = roleLevelMapRepository.findById(roleLevelId).get(); 
						if(roleLevelMap != null) {
							Long roleRightLevelId = roleLevelMap.getLevelId();
							RoleRightLevelMaster roleRightLevelMaster = roleRightLevelMasterRepository.findById(roleRightLevelId).get();
							if(roleRightLevelMaster != null) {
								roleLevelCode = roleRightLevelMaster.getLevelCode(); 
							}
						}
					}
				}
			}
			
			return roleLevelCode; 
		}
	}
