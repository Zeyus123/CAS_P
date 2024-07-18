package com.aashdit.prod.cmc.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Block;
import com.aashdit.prod.cmc.repository.BlockRepository;





@Service
public class BlockServiceImpl implements BlockService
{
	private final Logger log = Logger.getLogger(this.getClass());
	
	private MessageSource messageSource;

	@Autowired
	private BlockRepository blockRepository;


	
	@Override
	public ServiceOutcome<List<Block>> getAllActiveBlockByDistrictId(Long districtId, boolean b) {
	    ServiceOutcome<List<Block>> svcOutcome = new ServiceOutcome<>();

	    try {
	        List<Block> lstBlocks = blockRepository.findAllByDistrictId(districtId, true);
	        
	        if (lstBlocks != null) {
	           // lstBlocks = new LocaleSpecificSorter<>(Block.class).sort(lstBlocks);
	            svcOutcome.setData(lstBlocks);
	            svcOutcome.setOutcome(true);
	            svcOutcome.setMessage("SUCCESS");
	        } else {
	            svcOutcome.setData(null);
	            svcOutcome.setOutcome(false);
	            svcOutcome.setMessage("No blocks found for the given district ID");
	        }
	    } catch (Exception ex) {
	        log.error(ex);

	        svcOutcome.setData(null);
	        svcOutcome.setOutcome(false);
	        svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
	    }

	    return svcOutcome;
	}

	@Override
	public List<Block> getBlockList() {
		return blockRepository.findAll();
	}

	@Override
	public Block getAllActiveBlockId(Long blockId) {
		return blockRepository.findById(blockId).get();
	}

}
