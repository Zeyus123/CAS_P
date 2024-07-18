package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Grampanchayat;

public interface GrampanchayatService {

	ServiceOutcome<List<Grampanchayat>> getAllActiveGpByBlockId(Long blockId, Boolean byPassCheck);

	Grampanchayat getAllActiveGpId(Long gpId);

	List<Grampanchayat> getAllActiveGpIds(Long gpId);
	
	Grampanchayat findById(Long gpId);




}
