package com.aashdit.prod.cmc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashdit.prod.cmc.model.Month;

public interface MonthRepository extends JpaRepository<Month, Long> {

	List<Month> findAllByOrderByMonthOrderNo();

	Month findByMonthOrderNo(Long monthId);

	


}