package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;


@Repository
public interface RoleRightLevelMasterRepository extends JpaRepository<RoleRightLevelMaster, Long> {

	RoleRightLevelMaster findByLevelCode(String levelCode);
	
	List<RoleRightLevelMaster> findByIsActive(Boolean isActive);
}
