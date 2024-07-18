package com.aashdit.prod.cmc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Caste;

@Repository
public interface CasteRepository extends JpaRepository<Caste, Long> {

    List<Caste> findAllByIsActiveTrue();

	Caste findByCasteCode(String caste);

	Caste findByCasteId(Long casteId);
}
