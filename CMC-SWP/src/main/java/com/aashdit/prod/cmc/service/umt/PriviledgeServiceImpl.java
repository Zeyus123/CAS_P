package com.aashdit.prod.cmc.service.umt;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.RoleMenuMap;
import com.aashdit.prod.cmc.model.umt.RoleMenuPrivilegeMap;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.PriviledgeRepository;
import com.aashdit.prod.cmc.repository.umt.RoleMenuMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleMenuPrivilegeMapRepository;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Service
public class PriviledgeServiceImpl implements PriviledgeService, MessageSourceAware {

	private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;
	
	@Autowired
	private PriviledgeRepository priviledgeRepository;
	
	@Autowired
	private RoleMenuMapRepository rmmRepository;
	
	@Autowired
	private RoleMenuPrivilegeMapRepository rmpMapRepository;
	
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}


	@Override
	public ServiceOutcome<List<Privilege>> findAllPriviledges(Boolean includeInActive) {
		ServiceOutcome<List<Privilege>> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			List<Privilege> lstPriviledges = null;
			if (includeInActive)
			{
				lstPriviledges = priviledgeRepository.findAll();
			}
			else
			{
				lstPriviledges = priviledgeRepository.findByIsActive(true);
			}
			
			svcOutcome.setData(lstPriviledges);
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
	public ServiceOutcome<Privilege> findById(Long privilegeId) {
		ServiceOutcome<Privilege> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			Privilege priviledge = priviledgeRepository.getOne(privilegeId);
			
			svcOutcome.setData(priviledge);
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
	public ServiceOutcome<Privilege> savePrivilege(Privilege privilege) {
		ServiceOutcome<Privilege> svcOutcome = new ServiceOutcome<Privilege>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			
			if (privilege.getId() == null) //INSERT
			{
				privilege.setIsActive(privilege.getIsActive() == null? false : true);
				
				privilege.setCreatedBy(user);
				privilege.setCreatedOn(new Date());
				
				privilege = priviledgeRepository.save(privilege);
				svcOutcome.setData(privilege);
			}
			else //UPDATE
			{
				Privilege dbPrivilege = priviledgeRepository.getOne(privilege.getId());
				dbPrivilege.setCode(privilege.getCode());
				dbPrivilege.setIsActive(privilege.getIsActive() == null? false : true);
				dbPrivilege.setDesc(privilege.getDesc());
				dbPrivilege.setPriviledgeType(privilege.getPriviledgeType());
				dbPrivilege.setUiLabelCode(privilege.getUiLabelCode());
				
				dbPrivilege.setLastUpdatedBy(user);
				dbPrivilege.setLastUpdatedOn(new Date());
				
				dbPrivilege = priviledgeRepository.save(dbPrivilege);
				svcOutcome.setData(dbPrivilege);
			}
						
			svcOutcome.setMessage(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));

		}catch(Exception ex)
		{
			log.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<String> assignPrivilege(Long roleId, Long menuId, Long privId) {
		ServiceOutcome<String> svcOutcome = new ServiceOutcome<String>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			RoleMenuMap rmm = rmmRepository.findByRoleIdAndMenuId(roleId, menuId);
			if (rmm != null)
			{
				RoleMenuPrivilegeMap rmpMap = rmpMapRepository.findByRoleMenuIdAndPrivilegeId(rmm.getRoleMenuId(), privId);
				if (rmpMap == null)
				{
					rmpMap = new RoleMenuPrivilegeMap();
					rmpMap.setCreatedBy(user);
					rmpMap.setCreatedOn(new Date());
					rmpMap.setRoleMenuId(rmm.getRoleMenuId());
					rmpMap.setPrivilegeId(privId);
					rmpMap.setIsActive(true);
				}
				else
				{
					rmpMap.setIsActive(true);
					rmpMap.setLastUpdatedBy(user);
					rmpMap.setLastUpdatedOn(new Date());
				}
				rmpMapRepository.save(rmpMap);
				svcOutcome.setMessage(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));
				svcOutcome.setData(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));
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
	public ServiceOutcome<String> revokePrivilege(Long roleId, Long menuId, Long privId) {
		ServiceOutcome<String> svcOutcome = new ServiceOutcome<String>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			RoleMenuMap rmm = rmmRepository.findByRoleIdAndMenuId(roleId, menuId);
			if (rmm != null)
			{
				RoleMenuPrivilegeMap rmpMap = rmpMapRepository.findByRoleMenuIdAndPrivilegeId(rmm.getRoleMenuId(), privId);
				if (rmpMap != null)
				{
					rmpMap.setIsActive(false);
					rmpMap.setLastUpdatedBy(user);
					rmpMap.setLastUpdatedOn(new Date());
				}
				rmpMapRepository.save(rmpMap);
				svcOutcome.setMessage(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));
				svcOutcome.setData(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));
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
}
