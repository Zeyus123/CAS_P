package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Village;

public interface VillageService {

	ServiceOutcome<List<Village>> getAllActiveVillageByGpId(Long gpId, Boolean byPassCheck);

	Village getAllActiveVillageId(Long villageId);


}
