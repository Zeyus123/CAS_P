package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Municipality;

public interface MunicipalityService {

	ServiceOutcome<List<Municipality>> getAllActiveMunicipalityByDistrictId(Long districtId);




}
