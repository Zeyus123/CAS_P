package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Year;

@Repository
public interface YearRepository extends JpaRepository<Year, Long>{

	List<Year> findAllByIsActiveTrue();

	Year findByYearId(Long manufacturingYear);

	List<Year> findAllByIsActiveTrueOrderByYearName();

	Year findByYearName(String yearName);
	
	

}
