package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.umt.RoleMenuMap;

@Repository
public interface RoleMenuMapRepository extends JpaRepository<RoleMenuMap, Long>{


	RoleMenuMap findByRoleIdAndMenuId(Long roleid, Long menuId);
	
	List<RoleMenuMap> findByRoleId(Long rleId);
}
