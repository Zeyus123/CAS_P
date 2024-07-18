package com.aashdit.prod.cmc.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Municipality;
import com.aashdit.prod.cmc.model.Ward;
import com.aashdit.prod.cmc.repository.WardRepository;

@Service
public class WardServiceImpl implements WardService{

	private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	public ServiceOutcome<List<Ward>> getAllActiveWardByMunicipalityId(Long municipalityId) {
		  ServiceOutcome<List<Ward>> svcOutcome = new ServiceOutcome<>();

		    try {
		        List<Ward> lstwards = wardRepository.findAllByMunicipalityMunicipalityId(municipalityId);
		        
		        if (lstwards != null) {
		            svcOutcome.setData(lstwards);
		            svcOutcome.setOutcome(true);
		            svcOutcome.setMessage("SUCCESS");
		        } else {
		            svcOutcome.setData(null);
		            svcOutcome.setOutcome(false);
		            svcOutcome.setMessage("No ward found for the given district ID");
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
