package com.aashdit.prod.cmc.framework.core.exception;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonMappingExceptionHandlerImpl implements JsonMappingExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	private ObjectMapper mapper = null;
	
	private Locale locale;
	
	public Object checkRequestData(Object dto,String data) throws CommonException, JsonParseException, JsonMappingException, IOException {
		
		try {
			mapper = new ObjectMapper();
			dto = mapper.readValue(data, dto.getClass());
		} catch (JsonParseException jpe) {jpe.printStackTrace();
			throw new CommonException(messageSource.getMessage("valid.jsonparse", null, locale));
		} catch (JsonMappingException jme) {jme.printStackTrace();
			throw new CommonException(messageSource.getMessage("valid.jsonmapper", null, locale));
		} catch (IOException io) {io.printStackTrace();
			throw new CommonException(messageSource.getMessage("valid.jsonioexc", null, locale));
		}
	return dto;
	}
}