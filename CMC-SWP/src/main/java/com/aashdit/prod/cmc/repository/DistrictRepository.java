package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aashdit.prod.cmc.model.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
	
	
	List<District> findAllByIsActiveTrueOrderByDistrictName();
	
	District findByDistrictId(Long districtId);
	
	List<District> findAllByStateIdStateIdAndIsActiveTrue(Long demoId);

	@Query("FROM District where isActive = :isActive and stateId.stateId=:stateId")
	List<District> findAllByStateId(@Param("stateId") Long stateId, @Param("isActive") Boolean isActive);

	List<District> findAllByIsActiveTrue();
}

