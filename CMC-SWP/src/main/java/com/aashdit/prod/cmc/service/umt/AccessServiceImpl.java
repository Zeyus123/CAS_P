package com.aashdit.prod.cmc.service.umt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.VO.CommonNameIdDto;
import com.aashdit.prod.cmc.VO.umt.AccesslevelConfigMetaInfo;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.misc.umt.AccessLevelType;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleLevelMap;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.model.umt.UserRoleRightLevel;
import com.aashdit.prod.cmc.repository.umt.RoleLevelMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRightLevelMasterRepository;
import com.aashdit.prod.cmc.repository.umt.UserRoleMapRepository;
import com.aashdit.prod.cmc.repository.umt.UserRoleRightLevelRepository;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Service
public class AccessServiceImpl implements AccessService , MessageSourceAware {

	private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;
	
	@Autowired
	private RoleRightLevelMasterRepository levelMasterRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private RoleLevelMapRepository roleLevelMapRepository;
	
	@Autowired
	private UserRoleMapRepository userRoleMapRepository;
	
	@Autowired
	private UserRoleRightLevelRepository userRoleRightLevelRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}
	

	@Override
	public  <T> ServiceOutcome<List<T>> getByRoleLevel(Long userId, Long roleId, AccessLevelType accessLevel, Class<T> returnType) {
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
					query = entityManager.createNativeQuery(sql, returnType);

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

	@Override
	public  <T> ServiceOutcome<List<T>> getByRoleLevel(Long userId, Long roleId, String accessLevelCode, Class<T> returnType) {
		ServiceOutcome<List<T>> svcOutcome = new ServiceOutcome<>();
		try
		{
			log.debug("Validating Access Level : " + accessLevelCode);
			RoleRightLevelMaster roleRightLevel = levelMasterRepository.findByLevelCode(accessLevelCode);
			if (roleRightLevel != null)
			{
				String sql = buildSQL(roleRightLevel);
				if (sql != null)
				{
					//TODO : Try to make this dynamic and refactor into a FactoryBuilder
					Query query = null ;
					query = entityManager.createNativeQuery(sql, returnType);

					query.setParameter("userId", userId);
					query.setParameter("roleId", roleId);
					query.setParameter("roleLevelId", roleRightLevel.getRoleRightLevelId());
					
					svcOutcome.setData(query.getResultList());
				}
			}
			else
			{
				log.warn("Role Right Level Master not defined for Access level : " + accessLevelCode);
				svcOutcome.setOutcome(false);
				svcOutcome.setData(null);
				svcOutcome.setMessage("Role Right Level Master not defined for Access level : " + accessLevelCode);
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
				//.append(rrlm.getDisplayColumns())
				.append("*")
				.append(" FROM ")
				.append(rrlm.getDisplayViewName()).append(" e ")
					.append(" JOIN t_umt_user_role_right_level urrl ")
					.append(" ON ")
					//.append(" e.").append(rrlm.getPrimaryKeyName()).append(" = urrl.object_id")
					.append(" e.").append(rrlm.getPrimaryKeyName())
					  .append(" = case when urrl.object_id = -1 then e.")
					  .append(rrlm.getPrimaryKeyName())
					  .append(" else urrl.object_id end")
				.append(" join t_umt_user_role_map urm ")
					.append(" on urm.user_role_map_id = urrl.user_role_id ")
					.append(" and urm.user_id = :userId ")
					.append(" and urm.role_id = :roleId ")
				.append(" join t_umt_role_level_map rlm ")
					.append(" on rlm.role_id = :roleId ")
					.append(" and rlm.role_right_level_id = :roleLevelId ")
					.append(" and urrl.role_level_id = rlm.role_level_map_id  ")
				.append(" where e.is_active = true");

		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		
		return sb.toString();
	}
	
	private String buildSQLForPKs(RoleRightLevelMaster rrlm)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			/*
			 RAW SQL for District as an example
			 SELECT e.district_id  , e.district_id as dummy 
			FROM t_mst_district  e
			JOIN t_umt_user_role_right_level urrl 
			ON e.district_id = case when urrl.object_id = -1 then e.district_id else urrl.object_id end
			join t_umt_user_role_map urm 
			on urm.user_role_map_id = urrl.user_role_id 
				and urm.user_id = :userId 
				and urm.role_id = :roleId 
			join t_umt_role_level_map rlm 
				on rlm.role_id = :roleId 
				and rlm.role_right_level_id = :roleLevelId 
			where e.is_active = true;
			 */
			
			sb.append("SELECT e.")
				.append(rrlm.getPrimaryKeyName())
				.append(" , e.").append(rrlm.getPrimaryKeyName()).append(" as dummy ") // To force a RecordSet 
				.append(" FROM ")
				.append(rrlm.getDisplayViewName()).append(" e ")
					.append(" JOIN t_umt_user_role_right_level urrl ")
					.append(" ON ")
					//.append(" e.").append(rrlm.getPrimaryKeyName()).append(" = urrl.object_id")
					.append(" e.").append(rrlm.getPrimaryKeyName())
								  .append(" = case when urrl.object_id = -1 then e.")
								  .append(rrlm.getPrimaryKeyName())
								  .append(" else urrl.object_id end")
				.append(" join t_umt_user_role_map urm ")
					.append(" on urm.user_role_map_id = urrl.user_role_id ")
					.append(" and urm.user_id = :userId ")
					.append(" and urm.role_id = :roleId ")
				.append(" join t_umt_role_level_map rlm ")
					.append(" on rlm.role_id = :roleId ")
					.append(" and rlm.role_right_level_id = :roleLevelId ")
					.append(" and urrl.role_level_id = rlm.role_level_map_id  ")
				.append(" where e.is_active = true");

		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		
		return sb.toString();
	}


	@Override
	public ServiceOutcome<AccesslevelConfigMetaInfo> getACLConfigData(Long userId, Long roleId, Long roleRightLevelId,  String searchTerm , Integer page,  Integer size) {
		ServiceOutcome<AccesslevelConfigMetaInfo> svcOutcome = new ServiceOutcome<>();
		try
		{
			log.debug("Getting Access Level with id : " + roleRightLevelId);
			RoleRightLevelMaster roleRightLevel = levelMasterRepository.getOne(roleRightLevelId);
			if (roleRightLevel != null)
			{
				AccesslevelConfigMetaInfo alcConfig = new AccesslevelConfigMetaInfo();
				StringBuilder sb = getPagedSQL(searchTerm, page, size, roleRightLevel);
				StringBuilder recCount = getCountSQL(searchTerm, page, size, roleRightLevel);
				
				String sql = sb.toString();
				if (sql != null)
				{
					//TODO : Try to make this dynamic and refactor into a FactoryBuilder
					Query query = null ;
					query = entityManager.createNativeQuery(sql);
					List<Object[]> rows = query.getResultList();
					
					Query qCount = null;
					qCount = entityManager.createNativeQuery(recCount.toString());
					Integer lnRecCount = ((BigInteger)qCount.getSingleResult()).intValue();
					
					Integer totalPages = lnRecCount/size;
					log.debug("Total pages is " + totalPages);
					alcConfig.setTotalPages(totalPages);
					alcConfig.setCurrentPage(page);
					alcConfig.setPageSize(size);
					
					String sql2 = buildSQLForPKs(roleRightLevel);
					Query query2 = entityManager.createNativeQuery(sql2); 
					query2.setParameter("userId", userId);
					query2.setParameter("roleId", roleId);
					query2.setParameter("roleLevelId", roleRightLevel.getRoleRightLevelId());
					List<Object[]> pks = query2.getResultList();
					
					log.debug("Querying exisiting PKS");
					List<Long> arw = new ArrayList<Long>();
					for (Object[] r : pks)
					{
						arw.add(Long.parseLong(String.valueOf(r[0])));
					}
					alcConfig.setAllotedRowIds(arw);
					
					log.debug("Building Column Meta Info");
					StringBuilder infoSQL = new StringBuilder();
					infoSQL.append("select c.column_name , c.table_name ");
					infoSQL.append(" from ");
					infoSQL.append("    information_schema.\"columns\" c  ");
					infoSQL.append("join information_schema.\"tables\" t on ");
					infoSQL.append("    c.table_name = t.table_name  ");
					infoSQL.append("    and c.table_schema = t.table_schema  ");
					infoSQL.append("    and c.table_catalog = t.table_catalog  ");
					infoSQL.append("    and t.table_name = :objectName ");
					infoSQL.append("order by c.ordinal_position asc ");

					
					log.debug(infoSQL.toString());
					
					Query infoQuery = entityManager.createNativeQuery(infoSQL.toString());
					infoQuery.setParameter("objectName", roleRightLevel.getDisplayViewName());
					List<Object[]> colRows = infoQuery.getResultList();
					
					log.debug("Parsing Column Meta Info");
					LinkedHashMap<Integer, String> colMetaData = new LinkedHashMap<Integer, String>();
					Integer idx = 0;
					for (Object[] r : colRows)
					{
						String colName = String.valueOf(r[0]);
						colMetaData.put(idx, colName);
						if (roleRightLevel.getPrimaryKeyName().equals(colName))
						{
							alcConfig.setPrimaryKey(String.valueOf(r[0]));
						}
						idx++;
					}
					alcConfig.setColumnMetaData(colMetaData);
					
					log.debug("Building Row Data");
					JSONArray jArr = new JSONArray();
					for (Object[] r : rows)
					{
						//log.debug("Got a row...");
						JSONObject jobj = new JSONObject();
						for (int idx2 = 0; idx2 < colMetaData.size(); idx2++)
						{
							String columnName = colMetaData.get(idx2);
							jobj.put(columnName, String.valueOf(r[idx2]));
						}
						jArr.put(jobj);
					}
					alcConfig.setData(jArr.toString());
					svcOutcome.setData(alcConfig);
				}
			}
			else
			{
				log.warn("Role Right Level Master not defined for Access level : " + roleRightLevelId);
				svcOutcome.setOutcome(false);
				svcOutcome.setData(null);
				svcOutcome.setMessage("Role Right Level Master not defined for Access level : " + roleRightLevelId);
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


	private StringBuilder getPagedSQL(String searchTerm, Integer page, Integer size,
			RoleRightLevelMaster roleRightLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT e.*")
		.append(" FROM ")
		.append(roleRightLevel.getDisplayViewName())
		.append(" e ");
		
		if (searchTerm != null || searchTerm != "")
		{
			sb.append(" where ");
			String[] searchColumns = roleRightLevel.getSearchColumns().split(",");
			Boolean addOr = false;
			for(String searchColumn : searchColumns)
			{
				if (addOr)
				{
					sb.append(" or lower(e.").append(searchColumn).append(") like '%").append(searchTerm.toLowerCase()).append("%' ");
				}
				else
				{
					sb.append(" lower(e.").append(searchColumn).append(") like '%").append(searchTerm.toLowerCase()).append("%' ");
					addOr = true;
				}
				
			}
		}
		
		sb.append(" order by e.").append(roleRightLevel.getPrimaryKeyName()).append(" asc ");
		sb.append(" limit ").append(size);
		sb.append(" offset ").append(size * page);
		return sb;
	}
	
	private StringBuilder getCountSQL(String searchTerm, Integer page, Integer size,
			RoleRightLevelMaster roleRightLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(1)")
		.append(" FROM ")
		.append(roleRightLevel.getDisplayViewName())
		.append(" e ");
		
		if (searchTerm != null || searchTerm != "")
		{
			sb.append(" where ");
			String[] searchColumns = roleRightLevel.getSearchColumns().split(",");
			Boolean addOr = false;
			for(String searchColumn : searchColumns)
			{
				if (addOr)
				{
					sb.append(" or lower(e.").append(searchColumn).append(") like '%").append(searchTerm.toLowerCase()).append("%' ");
				}
				else
				{
					sb.append(" lower(e.").append(searchColumn).append(") like '%").append(searchTerm.toLowerCase()).append("%' ");
					addOr = true;
				}
				
			}
		}
		return sb;
	}


	@Override
	public ServiceOutcome<String> saveConfig(Long userId, Long roleId, Long roleRightLevelId, Long objectId) {
		ServiceOutcome<String> svcOutcome = new ServiceOutcome<String>();
		try
		{
			Boolean skipDelete = false;
			User user = SecurityHelper.getCurrentUser();
			
			//Get roleLevelId
			RoleLevelMap rlm = roleLevelMapRepository.findByRoleIdAndLevelId(roleId, roleRightLevelId);
			
			//Get userRoleId
			UserRoleMap urm = userRoleMapRepository.findByUserIdAndRoleId(userId, roleId);
			
			UserRoleRightLevel urrl = userRoleRightLevelRepository.find(urm.getUserRoleId(), rlm.getRoleLevelId(), objectId);
			List<UserRoleRightLevel> allocatedValues = userRoleRightLevelRepository.getAllocatedValues(urm.getUserRoleId(), rlm.getRoleLevelId());
			
			// ADDITION PART
			//If max Allocations is -1 that means there is no limit
			if (rlm.getMaxAllocations() < 0)
			{
				//If the entry does not exist - add the entry subject to above constraints
				if (urrl == null)
				{
					urrl = new UserRoleRightLevel();
					urrl.setObjectId(objectId);
					urrl.setRoleLevelId(rlm.getRoleLevelId());
					urrl.setUserRoleId(urm.getUserRoleId());
					urrl.setCreatedBy(user);
					urrl.setCreatedOn(new Date());
					urrl.setIsActive(true);
					
					userRoleRightLevelRepository.save(urrl);
					skipDelete = true;
					svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
				}
			}
			else
			{
				//Max allocation is 0 or greater. Check for limit of allocations
				if (allocatedValues != null && allocatedValues.size() < rlm.getMaxAllocations())
				{
					//If the entry does not exist - add the entry subject to above constraints
					if (urrl == null)
					{
						urrl = new UserRoleRightLevel();
						urrl.setObjectId(objectId);
						urrl.setRoleLevelId(rlm.getRoleLevelId());
						urrl.setUserRoleId(urm.getUserRoleId());
						urrl.setCreatedBy(user);
						urrl.setCreatedOn(new Date());
						urrl.setIsActive(true);
						
						userRoleRightLevelRepository.save(urrl);
						skipDelete = true;
						svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
					}
					
				}
				else
				{
					//We have met or exceeded the limit. Return an error message
					svcOutcome.setData(null);
					svcOutcome.setOutcome(false);
					svcOutcome.setMessage(messageSource.getMessage("max.allocations.exceeded", null, LocaleContextHolder.getLocale()));
				}
			}
			
			//DELETETION PART
			if (urrl != null && skipDelete == false)
			{
				userRoleRightLevelRepository.delete(urrl);
				svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
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
	public ServiceOutcome<String> allocate(Long userId, Long roleId, Long roleRightLevelId, Long objectId) {
		return this.saveConfig(userId, roleId, roleRightLevelId, objectId);
	}


	@Override
	public ServiceOutcome<String> deAllocate(Long userId, Long roleId, Long roleRightLevelId, Long objectId) {
		return this.saveConfig(userId, roleId, roleRightLevelId, objectId);
	}


	@Override
	public ServiceOutcome<User> getUsersWithAccess(Long entityId, String roleLevelCode, Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ServiceOutcome<List<Role>> findRolesFromUserRoleMap(Long userId) {
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		try
		{
			List<Role> roleList = new ArrayList<>();
			List<UserRoleMap> userRoleMapList = userRoleMapRepository.findByUserIdAndIsActiveTrue(userId);
			if (userRoleMapList != null && userRoleMapList.size()>0)
			{
				for(UserRoleMap userRoleMap : userRoleMapList) {
					Role role = roleRepository.findByRoleID(userRoleMap.getRoleId());
					roleList.add(role);
				}
			}
			else
			{
				log.warn("User Role Map not found against the user whose user id is--> " +userId);
				svcOutcome.setOutcome(false);
				svcOutcome.setData(roleList);
				svcOutcome.setMessage("User Role Map not found for user id-->" + userId);
			}
			
		}
		catch(Exception ex)
		{
			log.error("Exception occured in findRolesFromUserRoleMap method in AccessServiceImpl-->",ex);
			
			svcOutcome.setData(new ArrayList<>());
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return svcOutcome;
	}


//	@Override
//	public ServiceOutcome<List<CommonNameIdDto>> getAccessListToShowInDashboard(User user) {
//		ServiceOutcome<List<CommonNameIdDto>> svcOutcome = new ServiceOutcome<>();
//		try
//		{
//			List<CommonNameIdDto> accessList = new ArrayList<>();
//			List<UserRoleMap> userRoleMapList = userRoleMapRepository.findByUserIdAndIsActiveTrue(user.getUserId());
//			if (userRoleMapList != null && userRoleMapList.size()>0)
//			{
//				userRoleMapList.sort(Comparator.comparing(UserRoleMap::getUserRoleId));
//				for(UserRoleMap userRoleMap : userRoleMapList) {
//
//					try {
//						Role role = roleRepository.findByRoleID(userRoleMap.getRoleId());
//						CommonNameIdDto commonNameIdDto = new CommonNameIdDto();
//						commonNameIdDto.setId(userRoleMap.getUserRoleId());
//						if(userRoleMap.getObjectType().equals("COLG")) {
//							CollegeMaster collegeObj = collegeMasterRepository.findByCollegeId(userRoleMap.getObjectTypeId());
//							commonNameIdDto.setName(role.getDisplayName()+" ("+collegeObj.getShortName()+")");
//						}
//
//						if(userRoleMap.getObjectType().equals("UNV")) {
//							UniversityMaster universityObj = universityMasterRepository.findByUniversityId(userRoleMap.getObjectTypeId());
//							commonNameIdDto.setName(role.getDisplayName()+" ("+universityObj.getShortName()+")");
//						}
//
//						if(userRoleMap.getObjectType().equals("DEPT")) {
//							HigherEduDepartment hedObj = higherEduDepartmentRepository.findById(userRoleMap.getObjectTypeId()).get();
//							commonNameIdDto.setName(role.getDisplayName()+" ("+hedObj.getHedDptName()+")");
//						}
//						accessList.add(commonNameIdDto);
//					}catch (Exception e) {
//						log.error("Exception occurred in getAccessListToShowInDashboard method in AccessServiceImpl-->",e);
//						log.error("Error in getting access list for userRoleMapId-->"+userRoleMap.getUserRoleId());
//					}
//
//
//				}
//				svcOutcome.setData(accessList);
//				svcOutcome.setOutcome(true);
//				svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
//			}
//			else
//			{
//				log.error("User Role Map not found against the user whose user is--> " +user.getUserName());
//				svcOutcome.setOutcome(false);
//				svcOutcome.setData(new ArrayList<>());
//				svcOutcome.setMessage("User Role Map not found. Please check with admin.");
//			}
//			
//		}
//		catch(Exception ex)
//		{
//			log.error("Exception occured in getAccessListToShowInDashboard method in AccessServiceImpl-->",ex);
//			
//			svcOutcome.setData(new ArrayList<>());
//			svcOutcome.setOutcome(false);
//			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
//		}
//		return svcOutcome;
//	}


	@Override
	public ServiceOutcome<UserRoleMap> getUserRoleMapByMapId(Long userRoleMapId) {
		ServiceOutcome<UserRoleMap> svcOutcome = new ServiceOutcome<>();
		try
		{
				UserRoleMap userRoleMap = userRoleMapRepository.findById(userRoleMapId).get();
				if(userRoleMap==null) {
					svcOutcome.setData(null);
					svcOutcome.setOutcome(false);
					svcOutcome.setMessage("No roles found.");
				}else {
					svcOutcome.setData(userRoleMap);
					svcOutcome.setOutcome(true);
					svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
				}
		}
		catch(Exception ex)
		{
			log.error("Exception occured in getUserRoleMapByMapId method in AccessServiceImpl-->",ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return svcOutcome;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceOutcome<List<Object[]>> getCoreDataByQuery(String sql) {
		ServiceOutcome<List<Object[]>> svcOutcome = new ServiceOutcome<>();
		try {
			if(sql != null && !sql.equals("")) {
				String check = sql.substring(0,6);
				if(check.equalsIgnoreCase("select")) {
					Query query = entityManager.createNativeQuery(sql);
					List<Object[]> colRows = query.getResultList();
					svcOutcome.setData(colRows);
					svcOutcome.setOutcome(true);
					svcOutcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
				}else {			
					svcOutcome.setData(null);
					svcOutcome.setOutcome(false);
					svcOutcome.setMessage("Only select queries are allowed.");
				}
			}else {			
				svcOutcome.setData(null);
				svcOutcome.setOutcome(false);
				svcOutcome.setMessage("The given sql query is null or empty.");
			}
		} catch (Exception e) {
			log.error("Exception occured in getCoreDataByQuery method in AccessServiceImpl-->",e);
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage("Unable to execute query.");
		}
		
		
		return svcOutcome;
	}


}
