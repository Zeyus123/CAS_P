package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, Long> {
	
	UserRoleMap findByUserIdAndRoleId(Long userId, Long roleId);

	List<UserRoleMap> findByUserId(Long userId);

	List<UserRoleMap> findByUserIdAndIsActiveTrue(Long userId);

	@Query("From UserRoleMap Where roleId=:roleId")
	List<UserRoleMap> findUserRoleMapByRoleId(@Param("roleId")Long roleId);
	
	List<UserRoleMap> findByRoleId(Long roleId);

	@Query(value="select * from public.t_umt_user_role_map rm\r\n"
			+ "where rm.user_id =:userId \r\n"
			+ "and rm.role_id =:roleId \r\n"
			+ "and rm.object_type =:staffPerfType \r\n"
			+ "and rm.object_type_id =:collegeId",nativeQuery =true)
	List<UserRoleMap> findAllRecordsInObjectType(@Param("userId")Long userId, @Param("roleId")Long roleId,@Param("staffPerfType") String staffPerfType, @Param("collegeId")Long collegeId);

	
}
