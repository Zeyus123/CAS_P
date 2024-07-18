package com.aashdit.prod.cmc.repository.umt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.umt.RoleLevelMap;

@Repository
public interface RoleLevelMapRepository extends JpaRepository<RoleLevelMap, Long> {

	@Query("From RoleLevelMap Where roleId=:roleId and levelId=:levelId")
	RoleLevelMap findByRoleIdAndLevelId(@Param("roleId")Long roleId,@Param("levelId") Long levelId);

	List<RoleLevelMap> findByRoleId(Long roleId);

}
