package com.aashdit.prod.cmc.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.Grampanchayat;
import com.aashdit.prod.cmc.repository.BlockRepository;
import com.aashdit.prod.cmc.repository.GrampanchayatRepository;

@Service
public class GrampanchayatServiceImpl implements GrampanchayatService {

	private final Logger log = Logger.getLogger(this.getClass());

	private MessageSource messageSource;

	@Autowired
	private GrampanchayatRepository grampanchayatRpository;

	@Autowired
	private BlockRepository blockRepository;

	
	
	@Override
	public ServiceOutcome<List<Grampanchayat>> getAllActiveGpByBlockId(Long blockId,Boolean byPassCheck) {
		ServiceOutcome<List<Grampanchayat>> svcOutcome = new ServiceOutcome<>();

		try {
			List<Grampanchayat> lstGrampanchayats = grampanchayatRpository.findAllByBlockId(blockId, true);
			//lstGrampanchayats = new LocaleSpecificSorter<Grampanchayat>(Grampanchayat.class).sort(lstGrampanchayats);
			svcOutcome.setData(lstGrampanchayats);
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
	public Grampanchayat getAllActiveGpId(Long gpId) {
		return grampanchayatRpository.findByGpId(gpId);
	}



	@Override
	public List<Grampanchayat> getAllActiveGpIds(Long gpId) {
		return grampanchayatRpository.findByIsActiveTrueGpId(gpId);
	}

	@Override
	public Grampanchayat findById(Long gpId) {
	 return grampanchayatRpository.findByGpId(gpId);
	}

}
