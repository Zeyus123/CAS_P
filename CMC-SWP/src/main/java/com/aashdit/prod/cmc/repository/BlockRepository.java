package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {


	List<Block> findAllByIsActiveTrueOrderByBlockNameEN();

	Block findByBlockId(Long blockId);

	List<Block> findAllByDistrictDistrictIdAndIsActiveTrue(Long demoId);

	@Query("FROM Block where district.districtId =:districtId and isActive = :isActive ORDER BY blockNameEN")
	List<Block> findAllByDistrictId(@Param("districtId") Long districtId, @Param("isActive") Boolean isActive);

	List<Block> findAllByBlockIdAndIsActiveTrue(Long adminStrativeId);

}
