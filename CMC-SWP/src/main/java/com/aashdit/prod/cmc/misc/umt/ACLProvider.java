package com.aashdit.prod.cmc.misc.umt;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.repository.umt.RoleRightLevelMasterRepository;

public class ACLProvider<T> implements MessageSourceAware {

	private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;
	
	private Class<T> typeParameterClass;
	
	@Autowired
	private RoleRightLevelMasterRepository levelMasterRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}
	
	public ACLProvider(Class<T> typeParameterClass)
	{
		this.typeParameterClass = typeParameterClass;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceOutcome<List<T>> getByRoleLevel(Long userId, Long roleId, AccessLevelType accessLevel){
		
		ServiceOutcome<List<T>> svcOutcome = new ServiceOutcome<>();
		try
		{
			log.debug("Validating Access Level : " + accessLevel.getAccesslevel());
			RoleRightLevelMaster roleRightLevel = levelMasterRepository.findByLevelCode(accessLevel.getAccesslevel());
			if (roleRightLevel != null)
			{
				String sql = buildSQL(roleRightLevel);
				if (sql != null)
				{
					//TODO : Try to make this dynamic and refactor into a FactoryBuilder
					Query query = null ;
					query = entityManager.createNativeQuery(sql, this.typeParameterClass.getClass());
				
					
					query.setParameter("userId", userId);
					query.setParameter("roleId", roleId);
					query.setParameter("roleLevelId", roleRightLevel.getRoleRightLevelId());
					
					svcOutcome.setData(query.getResultList());
				}
			}
			else
			{
				log.warn("Role Right Level Master not defined for Access level : " + accessLevel.getAccesslevel());
				svcOutcome.setOutcome(false);
				svcOutcome.setData(null);
				svcOutcome.setMessage("Role Right Level Master not defined for Access level : " + accessLevel.getAccesslevel());
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
	
	private String buildSQL(RoleRightLevelMaster rrlm)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append("SELECT e.")
				.append(rrlm.getDisplayColumns())
				.append(" FROM ")
				.append(rrlm.getDisplayViewName()).append(" e ")
					.append(" JOIN t_umt_user_role_right_level urrl ")
					.append(" ON ")
					.append(" e.").append(rrlm.getPrimaryKeyName()).append(" = urrl.object_id")
				.append(" join t_umt_user_role_map urm ")
					.append(" on urm.user_role_id = urrl.user_role_id ")
					.append(" and urm.user_id = :userId ")
					.append(" and urm.role_id = :roleId ")
					.append(" 	and urm.is_active = true ")
				.append(" join t_umt_role_level_map rlm ")
					.append(" on rlm.role_id = :roleId ")
					.append(" and rlm.role_right_level_id = :roleLevelId ")
					.append("	and rlm.is_active = true ")
				.append(" where e.is_active = true");

		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		
		return sb.toString();
	}
}
