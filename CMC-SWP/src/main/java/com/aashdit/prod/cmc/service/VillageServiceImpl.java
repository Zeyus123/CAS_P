package com.aashdit.prod.cmc.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Village;
import com.aashdit.prod.cmc.repository.GrampanchayatRepository;
import com.aashdit.prod.cmc.repository.VillageRepository;

@Service
public class VillageServiceImpl implements VillageService {

	private final Logger log = Logger.getLogger(this.getClass());

	private MessageSource messageSource;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private GrampanchayatRepository grampanchayatRpository;
	
	@Override
	public ServiceOutcome<List<Village>> getAllActiveVillageByGpId(Long gpId,Boolean byPassCheck) {
		ServiceOutcome<List<Village>> svcOutcome = new ServiceOutcome<>();

		try {
			List<Village> lstVillages = villageRepository.findAllByBlockId(gpId,true);
			//lstVillages = new LocaleSpecificSorter<Village>(Village.class).sort(lstVillages);
			svcOutcome.setData(lstVillages);	
			svcOutcome.setOutcome(true);
			svcOutcome.setMessage("SUCCESS");
		} catch (Exception ex) {
			log.error(ex);
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return svcOutcome;
	}

	@Override
	public Village getAllActiveVillageId(Long villageId) {
		return villageRepository.findByVillageId(villageId);
	}
}
