package com.aashdit.prod.cmc.service.umt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.LocaleSpecificSorter;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleLevelMap;
import com.aashdit.prod.cmc.model.umt.RoleMenuMap;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.RoleLevelMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleMenuMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRightLevelMasterRepository;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Service
public class RoleServiceImpl implements RoleService, MessageSourceAware{

	private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleLevelMapRepository roleLevelMapRepository;
	
	@Autowired
	private RoleRightLevelMasterRepository roleRightLevelMasterRepository;
	
	@Autowired
	private RoleMenuMapRepository  roleMenuMapRepository;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	@Override
	public ServiceOutcome<List<Role>> getAllRoles(Boolean includeInactive) {
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			List<Role> lstRoles = null;
			if (!includeInactive)
			{
				lstRoles = roleRepository.findByIsActiveOrderByDisplayNameAsc(true);
			}
			else
			{
				lstRoles = roleRepository.findAll();
			}
			lstRoles = lstRoles.stream().sorted((first, second) -> {
	            	return first.getDisplayName().compareTo(second.getDisplayName());
			})
			.collect(Collectors.toList());
	        
			
			svcOutcome.setData(lstRoles);
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<List<Role>> getRoleForUser(Long userId) {
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			List<Role> lstRoles = null;
			
			StringBuilder query = new StringBuilder();
			query.append("select r.*  ");
			query.append("from t_umt_role r ");
			query.append("join t_umt_user_role_map urm ");
			query.append("  on urm.role_id = r.role_id ");
			query.append("  and urm.user_id = :userId ");

			
			Query rs = entityManager.createNativeQuery(query.toString(), Role.class);
			rs.setParameter("userId", userId);
			lstRoles =  rs.getResultList();
			lstRoles = new LocaleSpecificSorter<Role>(Role.class).sort(lstRoles);
			
			svcOutcome.setData(lstRoles);
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<Role> getRoleByCode(String roleCode) {
		ServiceOutcome<Role> svcOutcome = new ServiceOutcome<Role>();
		try
		{
			Role role = roleRepository.findByRoleCode(roleCode);
			svcOutcome.setData(role);
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<Role> findByRoleId(Long roleId) {
			ServiceOutcome<Role> svcOutcome = new ServiceOutcome<Role>();
			try
			{
				Role role = roleRepository.findById(roleId).get();
				svcOutcome.setData(role);
			}
			catch(Exception ex)
			{
				log.error(ex);
				
				svcOutcome.setData(null);
				svcOutcome.setOutcome(false);
				svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			}
		
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<Role> lockNUnlockRoleById(Long roleId, Boolean isActive) {
		ServiceOutcome<Role> serviceOutcome=new ServiceOutcome<Role>();
		try {
			if(roleId!=null) {
				Role role = roleRepository.findById(roleId).get();
				 if(isActive==true) {
					 role.setIsActive(isActive);
					 serviceOutcome.setMessage(messageSource.getMessage("msg.success",null, "role unlocked successfully", LocaleContextHolder.getLocale()));
				 }
				
                 if(isActive==false) {
                	 role.setIsActive(isActive);
                	 serviceOutcome.setMessage(messageSource.getMessage("msg.success",null, "role locked successfully", LocaleContextHolder.getLocale())); 
				 }
                 role=roleRepository.save(role);
                 serviceOutcome.setData(role);
			}else {
				 serviceOutcome.setMessage(messageSource.getMessage("msg.error",null, LocaleContextHolder.getLocale()));	
				 serviceOutcome.setData(null);
	             serviceOutcome.setOutcome(false);
			}
			
		} catch (Exception e) {
			 log.error(e);
             serviceOutcome.setData(null);
             serviceOutcome.setOutcome(false);
             serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<Role> addNupdateRole(Role role) {
		ServiceOutcome<Role> outcome=new ServiceOutcome<Role>();
		try {
			User user=SecurityHelper.getCurrentUser();
			if(role.getRoleId()!=null) {
			Role prvRole = roleRepository.findById(role.getRoleId()).get();	
			prvRole.setLastUpdatedOn(new Date());	
			prvRole.setLastUpdatedBy(user);
			prvRole.setDescription(role.getDescription());
			prvRole.setRoleCode(role.getRoleCode());
			prvRole.setDisplayName(role.getDisplayName());
			prvRole.setMaxAssignments(role.getMaxAssignments());
			
			prvRole=roleRepository.save(prvRole);
			 outcome.setData(prvRole);	
			 outcome.setMessage(messageSource.getMessage("msg.success",null, "Role updated successfully", LocaleContextHolder.getLocale()));
			}else {
			 //role=new Role();
			 role.setCreatedBy(user);	
			 role.setCreatedOn(new Date());	
			 role.setIsActive(true);
			 role.setDisplayOnPage(true);
			 role=roleRepository.save(role);
			 outcome.setData(role);	
			 outcome.setMessage(messageSource.getMessage("msg.success",null, "Role saved successfully", LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			 log.error(e);
			 outcome.setData(null);
			 outcome.setOutcome(false);
			 outcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return outcome;
	}


	@Override
	public ServiceOutcome<List<RoleLevelMap>> findRoleLevelList() {
		ServiceOutcome<List<RoleLevelMap>> serviceOutcome=new ServiceOutcome<List<RoleLevelMap>>();
		try {
			List<RoleLevelMap> roleLevelMaps=roleLevelMapRepository.findAll();
			serviceOutcome.setData(roleLevelMaps);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<List<RoleLevelMap>> findRoleLevelListByRoleId(Long roleId) {
		ServiceOutcome<List<RoleLevelMap>> serviceOutcome=new ServiceOutcome<List<RoleLevelMap>>();
		try {
			List<RoleLevelMap> roleLevelMaps=roleLevelMapRepository.findByRoleId(roleId);
			serviceOutcome.setData(roleLevelMaps);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<List<RoleRightLevelMaster>> roleRightLevelList() {
		ServiceOutcome<List<RoleRightLevelMaster>> serviceOutcome=new ServiceOutcome<List<RoleRightLevelMaster>>();
		try {
			List<RoleRightLevelMaster> roleLevelMaps=roleRightLevelMasterRepository.findByIsActive(true);
			serviceOutcome.setData(roleLevelMaps);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<RoleLevelMap> addNupdateRoleLevelMap(Long[] maxAllocations, Long[] roleLevelId, Boolean[] status,	Long roleId) {
		ServiceOutcome<RoleLevelMap> roleServiceOutcome=new ServiceOutcome<RoleLevelMap>();
		User user=SecurityHelper.getCurrentUser();
		try {
			for (int i = 0; i < roleLevelId.length; i++) {
				log.debug("roleLevelId is : " + roleLevelId[i]);
				RoleLevelMap roleLevelMap = roleLevelMapRepository.findByRoleIdAndLevelId(roleId, roleLevelId[i]);
				if (roleLevelMap != null)
				{
					log.debug("Found link for role : " + roleId + " and levelId : " + roleLevelId[i]);
					roleLevelMap.setMaxAllocations(maxAllocations[i]);
					roleLevelMap.setIsActive(status[i]);
					
					roleLevelMap.setLastUpdatedOn(new Date());
					roleLevelMap.setLastUpdatedBy(user);
					
					log.debug("saving entry for above " );
					roleLevelMap=roleLevelMapRepository.save(roleLevelMap);
				}
				else
				{
					log.debug("No link found for role : " + roleId + " and levelId : " + roleLevelId[i]);
					if (status[i])
					{
						roleLevelMap=new RoleLevelMap();
						roleLevelMap.setRoleId(roleId);
						roleLevelMap.setLevelId(roleLevelId[i]);
						roleLevelMap.setMaxAllocations(maxAllocations[i]);
						roleLevelMap.setIsActive(status[i]);
						
						roleLevelMap.setCreatedOn(new Date());
						roleLevelMap.setCreatedBy(user);
						
						log.debug("creating entry for above " );
						roleLevelMap=roleLevelMapRepository.save(roleLevelMap);
					}
				}
				
			}
			 roleServiceOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
		} catch (Exception e) {
			 log.error(e);
			 roleServiceOutcome.setData(null);
			 roleServiceOutcome.setOutcome(false);
			 roleServiceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return roleServiceOutcome;
	}

	@Override
	public ServiceOutcome<RoleRightLevelMaster> getRoleRightLevelMasterById(Long levelId) {
		ServiceOutcome<RoleRightLevelMaster> serviceOutcome=new ServiceOutcome<RoleRightLevelMaster>();
		try {
		RoleRightLevelMaster roleRightLevelMaster = roleRightLevelMasterRepository.getOne(levelId);
		serviceOutcome.setData(roleRightLevelMaster);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<RoleLevelMap> findRoleLevelMapByRoleIdAndLevelId(Long roleId, Long levelId) {
		ServiceOutcome<RoleLevelMap> serviceOutcome=new ServiceOutcome<RoleLevelMap>();
		try {
			RoleLevelMap roleLevelMap = roleLevelMapRepository.findByRoleIdAndLevelId(roleId,levelId);
			serviceOutcome.setData(roleLevelMap);
			serviceOutcome.setOutcome(true);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<List<Role>> findRoleList() {
		ServiceOutcome<List<Role>> serviceOutcome=new ServiceOutcome<List<Role>>();
		try {
			List<Role> roles=roleRepository.findAll();
			serviceOutcome.setData(roles);
		} catch (Exception e) {
			 log.error(e);
			 serviceOutcome.setData(null);
			 serviceOutcome.setOutcome(false);
			 serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<List<Role>> getRolePagedList() {
		ServiceOutcome<List<Role>> serviceOutcome = new ServiceOutcome<List<Role>>();
		try {
			List<Role> userList = roleRepository.findAll();
			serviceOutcome.setData(userList);
		} catch (Exception e) {
			log.error(e.getMessage());
			serviceOutcome.setData(null);
			serviceOutcome.setOutcome(false);
			serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return serviceOutcome;
	}


	@Override
	public ServiceOutcome<Role> save(Role role) {
		return this.addNupdateRole(role);
	}


	@Override
	public ServiceOutcome<Role> delete(Long roleId) {
		return this.lockNUnlockRoleById(roleId, true);
	}


	@Override
	public ServiceOutcome<Boolean> allocateMenu(RoleMenuMap roleMenuMap) {
		ServiceOutcome<Boolean> svcOutcome = new ServiceOutcome<Boolean>();
		try
		{
			User currentUser = SecurityHelper.getCurrentUser();
			RoleMenuMap rmDBMap = roleMenuMapRepository.findByRoleIdAndMenuId(roleMenuMap.getRoleId(), roleMenuMap.getMenuId());
			if (rmDBMap != null)
			{
				
				rmDBMap.setIsActive(true);
				rmDBMap.setLastUpdatedBy(currentUser);
				rmDBMap.setLastUpdatedOn(new Date());
				
				roleMenuMapRepository.save(rmDBMap);
				
				svcOutcome.setData(true);
				svcOutcome.setOutcome(true);
				svcOutcome.setMessage(messageSource.getMessage("msg.success",null,LocaleContextHolder.getLocale()));
			}
			else
			{
				roleMenuMap.setIsActive(true);
				roleMenuMap.setCreatedBy(currentUser);
				roleMenuMap.setCreatedOn(new Date());
				
				roleMenuMapRepository.save(roleMenuMap);
				
				svcOutcome.setData(true);
				svcOutcome.setOutcome(true);
				svcOutcome.setMessage(messageSource.getMessage("msg.success",null,LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			
		}
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<Boolean> deAllocateMenu(RoleMenuMap roleMenuMap) {
		ServiceOutcome<Boolean> svcOutcome = new ServiceOutcome<Boolean>();
		try
		{
			User currentUser = SecurityHelper.getCurrentUser();
			RoleMenuMap rmDBMap = roleMenuMapRepository.findByRoleIdAndMenuId(roleMenuMap.getRoleId(), roleMenuMap.getMenuId());
			if (rmDBMap != null)
			{
				
				rmDBMap.setIsActive(false);
				rmDBMap.setLastUpdatedBy(currentUser);
				rmDBMap.setLastUpdatedOn(new Date());
				
				roleMenuMapRepository.save(rmDBMap);
				
				svcOutcome.setData(true);
				svcOutcome.setOutcome(true);
				svcOutcome.setMessage(messageSource.getMessage("msg.success",null,LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			
		}
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<RoleLevelMap> allocateLevel(RoleLevelMap roleLevelMap) {
		
		Long[] maxAllocations = new Long[] {roleLevelMap.getMaxAllocations()};
		Long[] roleLevelId = new Long[] {roleLevelMap.getLevelId()};
		Boolean[] status = new Boolean[] {true}; 
		Long roleId = roleLevelMap.getRoleId();
		
		return addNupdateRoleLevelMap(maxAllocations,roleLevelId,status,roleId );

	}


	@Override
	public ServiceOutcome<RoleLevelMap> deAllocateLevel(RoleLevelMap roleLevelMap) {
		Long[] maxAllocations = new Long[] {roleLevelMap.getMaxAllocations()};
		Long[] roleLevelId = new Long[] {roleLevelMap.getLevelId()};
		Boolean[] status = new Boolean[] {false}; 
		Long roleId = roleLevelMap.getRoleId();
		
		return addNupdateRoleLevelMap(maxAllocations,roleLevelId,status,roleId );
	}
}
