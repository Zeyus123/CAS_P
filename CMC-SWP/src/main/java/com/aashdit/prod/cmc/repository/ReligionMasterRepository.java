package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.ReligionMaster;

@Repository
public interface ReligionMasterRepository extends JpaRepository<ReligionMaster, Long>{

	List<ReligionMaster> findAllByIsActiveTrue();

}
