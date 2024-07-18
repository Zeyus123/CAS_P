package com.aashdit.prod.cmc.service;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Block;

public interface BlockService 
{
	ServiceOutcome<List<Block>> getAllActiveBlockByDistrictId(Long districtId, boolean b);

	List<Block> getBlockList();

	Block getAllActiveBlockId(Long blockId);

}
