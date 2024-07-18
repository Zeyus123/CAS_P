package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aashdit.prod.cmc.model.Municipality;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

	List<Municipality> findAllByDistrictDistrictId(Long districtId);

	Municipality findByMunicipalityId(Long municipalityId);

}
