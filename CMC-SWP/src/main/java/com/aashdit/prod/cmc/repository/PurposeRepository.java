package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashdit.prod.cmc.model.Purpose;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
	
	List<Purpose> findAllByIsActiveTrue(); 
	
	Purpose findByPurposeId(Long purposeId);

	List<Purpose> findAllByIsActiveTrueOrderByPurposeName();

}
