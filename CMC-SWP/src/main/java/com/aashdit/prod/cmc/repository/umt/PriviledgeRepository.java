package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aashdit.prod.cmc.model.umt.Privilege;

public interface PriviledgeRepository extends JpaRepository<Privilege, Long> {

	List<Privilege> findByIsActive(Boolean isActive);
	
	List<Privilege> findAll();
	
	@Query(value="select p.* " + 
			"from t_umt_mst_privilege p " + 
			"join t_umt_role_menu_privilege_map rmpm " + 
			"    on p.privilege_id = rmpm.privilege_id " + 
			"    and rmpm.is_active = true " + 
			"join t_umt_role_menu_map rmm " + 
			"    on rmm.role_menu_id = rmpm.role_menu_id " + 
			"    and rmm.role_id = :roleId " + 
			"    and rmm.menu_id = :menuId ;" , nativeQuery=true)
	List<Privilege> findByMenuAndRole(@Param("menuId")Long menuId, @Param("roleId")Long roleId);
}
