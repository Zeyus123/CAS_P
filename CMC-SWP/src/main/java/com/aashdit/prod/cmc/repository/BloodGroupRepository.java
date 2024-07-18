package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.BloodGroup;

@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup, Long>{

	List<BloodGroup> findAllByIsActiveTrue();

}
