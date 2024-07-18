package com.aashdit.prod.cmc.repository.umt;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashdit.prod.cmc.model.umt.RoleMenuPrivilegeMap;

public interface RoleMenuPrivilegeMapRepository extends JpaRepository<RoleMenuPrivilegeMap, Long> {

	RoleMenuPrivilegeMap findByRoleMenuIdAndPrivilegeId(Long roleMenuId, Long privilegeId);
}
