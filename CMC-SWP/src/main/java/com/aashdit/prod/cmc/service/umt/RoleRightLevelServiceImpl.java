package com.aashdit.prod.cmc.service.umt;

import java.util.List;

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
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.repository.umt.RoleRightLevelMasterRepository;

@Service
public class RoleRightLevelServiceImpl implements RoleRightLevelService, MessageSourceAware {

private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private RoleRightLevelMasterRepository roleRightLevelMasterRepository;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;

	}

	@Override
	public ServiceOutcome<List<RoleRightLevelMaster>> getRoleRightLevelsByRoleId(Long roleId) {
		ServiceOutcome<List<RoleRightLevelMaster>> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			List<RoleRightLevelMaster> lstLevels = null;
			
			StringBuilder query = new StringBuilder();
			query.append("select rrl.* ");
			query.append("from t_umt_mst_role_right_level rrl ");
			query.append("join t_umt_role_level_map rlm ");
			query.append("  on rrl.mst_role_right_level_id = rlm.role_right_level_id  ");
			query.append("  and rlm.role_id = :roleId ");
			query.append("  and rlm.is_active = true ");


			
			Query rs = entityManager.createNativeQuery(query.toString(), RoleRightLevelMaster.class);
			rs.setParameter("roleId", roleId);
			lstLevels =  rs.getResultList();
			lstLevels = new LocaleSpecificSorter<RoleRightLevelMaster>(RoleRightLevelMaster.class).sort(lstLevels);
			
			svcOutcome.setData(lstLevels);
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
	public ServiceOutcome<List<RoleRightLevelMaster>> getRoleRightLevels(Boolean includeInActive) {
		ServiceOutcome<List<RoleRightLevelMaster>> svcOutcome = new ServiceOutcome<>();
		
		try
		{
			List<RoleRightLevelMaster> lstLevels = null;
			if (includeInActive != true)
			{
				lstLevels = roleRightLevelMasterRepository.findByIsActive(true);
			}
			else
			{
				lstLevels =  roleRightLevelMasterRepository.findAll();
			}

			lstLevels = new LocaleSpecificSorter<RoleRightLevelMaster>(RoleRightLevelMaster.class).sort(lstLevels);
			
			svcOutcome.setData(lstLevels);
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
	public ServiceOutcome<List<RoleRightLevelMaster>> getLevelPagedList() {
		ServiceOutcome<List<RoleRightLevelMaster>> svcOutcome = new ServiceOutcome<>();

		try {
			List<RoleRightLevelMaster> lstLevels = null;

			lstLevels = roleRightLevelMasterRepository.findAll();

			lstLevels = new LocaleSpecificSorter<RoleRightLevelMaster>(RoleRightLevelMaster.class).sort(lstLevels);

			svcOutcome.setData(lstLevels);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return svcOutcome;
	}

	@Override
	public ServiceOutcome<RoleRightLevelMaster> fndByLevelId(Long levelId) {
		ServiceOutcome<RoleRightLevelMaster> svcOutcome = new ServiceOutcome<RoleRightLevelMaster>();
		try {
			RoleRightLevelMaster rrlm = roleRightLevelMasterRepository.findById(levelId).orElse(null);
			svcOutcome.setData(rrlm);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return svcOutcome;
	}

}
