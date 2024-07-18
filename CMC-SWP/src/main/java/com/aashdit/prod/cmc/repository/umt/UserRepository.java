package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import com.aashdit.prod.cmc.VO.CurrentUserVo;
import com.aashdit.prod.cmc.model.umt.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User>,CrudRepository<User, Long>  {

	User findByEmail(String email);

	/**
	 * get user details by name
	 * @param searchKey
	 */
	@Query("FROM User WHERE LOWER(userName) LIKE %:name% ")
	List<User> getUserDetailsByName(@Param("name")String name);
	
	
	@Query("FROM User WHERE primaryRole.roleCode = 'ROLE_ADMIN'")
	List<User> findAllAdmins();
	
	User findByUserName(String username);
	
	@Query("FROM User WHERE isLoggedIn = true")
	List<User> getLoggedInUsers();
	
	User findByUserId(Long userId);
	
	@Query("FROM User WHERE LOWER(userName) LIKE %:name% and isActive = true")
	List<User> searchForAutocomplete(@Param("name")String name);

	@Query("FROM User WHERE primaryRole.roleCode = :roleCode")
	List<User> findUserByRoleCode(@Param("roleCode")String roleCode);
	
	@Query(value="select e.* " + 
			"from t_umt_user e " + 
			"join t_umt_user_role_map urm " + 
			"    on urm.user_id = e.user_id  " + 
			"join t_umt_user_role_right_level urrl  " + 
			"    on urrl.user_role_id  = urm.user_role_map_id  " + 
			"join t_umt_role_level_map rlm  " + 
			"    on rlm.role_level_map_id = urrl.role_level_id  " + 
			"    and rlm.role_level_map_id = :roleLevelId " + 
			"where urrl.object_id = :entityId ; ", nativeQuery = true)
	List<User> findUserByLevelAndId(@Param("roleLevelId")Long roleLevelId, @Param("entityId")Long entityId);

    CurrentUserVo getUserObjectByUserId(Long userId);

	User findByBeneficiaryCode(String benfCode);
	
	@Query(value="select tuu.* from t_heads_role_app_mapping thram\r\n"
			+ "join t_heads_application_master tham on tham.app_id = thram.application_id \r\n"
			+ "join t_umt_role tur on thram.role_id = tur.role_id\r\n"
			+ "join t_umt_user_role_map tuurm on tuurm.role_id = tur.role_id and tuurm.object_type_id=:userLevelId\r\n"
			+ "join t_umt_user tuu on tuu.user_id =tuurm.user_id\r\n"
			+ "where tur.role_id=:roleId and thram.object_type=:userLevel \r\n"
			+ "and thram.object_type_id =:userLevelId and tham.app_code =:appCode", nativeQuery = true)
	List<User> findByUserListByRoleIdAndObjIdAndLevel(@Param("roleId")Long roleId,@Param("userLevelId") Long userLevelId, 
			@Param("userLevel")String userLevel,@Param("appCode") String appCode);

	//List<User> findUserByRoleAndLevelAndId(@Param("roleLevelId")Long roleLevelId, @Param("entityId")Long entityId);
}
