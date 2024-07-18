package com.aashdit.prod.cmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.AppConfigParameters;

@Repository
public interface AppConfigParametersRepository extends JpaRepository<AppConfigParameters, Long>{

	AppConfigParameters findByParamCode(String paramCode);

}
