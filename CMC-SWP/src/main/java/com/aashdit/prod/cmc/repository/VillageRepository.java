package com.aashdit.prod.cmc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Village;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

	List<Village> findAllByIsActiveTrueOrderByVillageNameEn();

	Village findByVillageId(Long villageId);
	
	List<Village> findAllByGpIdGpIdAndIsActiveTrue(Long demoId);

	@Query("FROM Village where gpId.gpId=:gpId and isActive=:isActive")
	List<Village> findAllByBlockId(@Param("gpId") Long gpId, @Param("isActive") Boolean isActive);

}
