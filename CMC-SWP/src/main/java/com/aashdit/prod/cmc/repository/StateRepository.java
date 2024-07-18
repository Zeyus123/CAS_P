package com.aashdit.prod.cmc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Query("From State Where isActive=:isActive")
	List<State> findAll(@Param("isActive") boolean isActive);

	@Query("From State Where stateCode=:stateCode and isActive=true")
	State findStatebyStateCode(@Param("stateCode") String stateCode);

	List<State> findAllByIsActiveTrue();

	State findByStateCode(String stateCode);

	State findByStateId(Long stateId);
}
