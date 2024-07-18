package com.aashdit.prod.cmc.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Block;
import com.aashdit.prod.cmc.model.Municipality;
import com.aashdit.prod.cmc.repository.MunicipalityRepository;

@Service
public class MunicipalityServiceImpl implements MunicipalityService{
	
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MunicipalityRepository MunicipalityRepository;
	
	@Autowired
	private MessageSource messageSource;

	
	@Override
	public ServiceOutcome<List<Municipality>> getAllActiveMunicipalityByDistrictId(Long districtId) {
		  ServiceOutcome<List<Municipality>> svcOutcome = new ServiceOutcome<>();

		    try {
		        List<Municipality> lstMunicipalities = MunicipalityRepository.findAllByDistrictDistrictId(districtId);
		        
		        if (lstMunicipalities != null) {
		           // lstBlocks = new LocaleSpecificSorter<>(Block.class).sort(lstBlocks);
		            svcOutcome.setData(lstMunicipalities);
		            svcOutcome.setOutcome(true);
		            svcOutcome.setMessage("SUCCESS");
		        } else {
		            svcOutcome.setData(null);
		            svcOutcome.setOutcome(false);
		            svcOutcome.setMessage("No municipalities found for the given district ID");
		        }
		    } catch (Exception ex) {
		        log.error(ex);

		        svcOutcome.setData(null);
		        svcOutcome.setOutcome(false);
		        svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		    }
		return svcOutcome;
	}




}
