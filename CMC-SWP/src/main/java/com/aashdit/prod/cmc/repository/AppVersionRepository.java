  package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashdit.prod.cmc.model.AppVersion;

  public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
	  
      List<AppVersion> findByIsActiveIsTrueOrderByVersionIdDesc();
  }