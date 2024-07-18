package com.aashdit.prod.cmc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

	List<Gender> findAllByIsActiveTrue();

	Gender findByGenderCode(String gender);

	Gender findByGenderId(Long genderId);

}
