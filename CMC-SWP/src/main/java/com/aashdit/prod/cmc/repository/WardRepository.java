package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashdit.prod.cmc.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Long> {

	Ward findByWardId(Long wardId);

	List<Ward> findAllByMunicipalityMunicipalityId(Long municipalityId);

}
