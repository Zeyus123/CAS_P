package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aashdit.prod.cmc.model.umt.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByRoleCode(String roleCode);
	
	List<Role> findByIsActiveOrderByDisplayNameAsc(Boolean isActive);
	
	@Query("FROM Role where isActive=true order by displayName")
	List<Role> findRoleListActiveOrderByDisplayName();
	

	@Query("FROM Role WHERE displayOnPage=false order by displayName")
	List<Role> findNonSystemRolesOrderByDisplayName();

	@Query("FROM Role where  roleId=:id")
	Role findByRoleID(@Param("id")Long id);
	
	//List<Role> findAllByOrderByRoleCode();
	
	List<Role> findByDisplayOnPage(Boolean display);
	
	@Query("FROM Role where isActive=true and displayOnPage=true order by displayName")
	List<Role> findUIRolesOrderByDisplayName();
	
	Role findByRoleLevel(Long roleLevel);

	@Query(value="select * from t_umt_role r\r\n"
			+ "where r.role_level < (select role_level from t_umt_role where role_id = :roleId)\r\n"
			+ "and r.is_active = true and r.is_designation = true and r.display_on_page =true" , nativeQuery=true)
	List<Role> getUpperLevelRoleListByRoleId(Long roleId);

	@Query(value="select * from t_umt_role r\r\n"
			+ "where r.role_level > (select role_level from t_umt_role where role_id = :roleId)\r\n"
			+ "and r.is_active = true and r.is_designation = true and r.display_on_page =true" , nativeQuery=true)
	List<Role> getLowerLevelRoleListByRoleId(Long roleId);

	@Query("FROM Role where isDesignation=true")
	List<Role> findByIsDesignationTrue();


	List<Role> findAllByIsActiveTrueAndIsDesignationTrueOrderByDisplayName();

	List<Role> findByIsActiveTrueAndIsDesignationTrueOrderByDisplayNameAsc();

}
