package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Ward;

public interface WardService {

	ServiceOutcome<List<Ward>> getAllActiveWardByMunicipalityId(Long municipalityId);

}
