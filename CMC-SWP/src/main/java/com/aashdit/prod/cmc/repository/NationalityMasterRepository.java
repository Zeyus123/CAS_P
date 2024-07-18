package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.NationalityMaster;

@Repository
public interface NationalityMasterRepository extends JpaRepository<NationalityMaster, Long>{

	List<NationalityMaster> findAllByIsActiveTrue();

}
