package com.aashdit.prod.cmc.framework.core.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonMappingExceptionHandler {
	
	public Object checkRequestData(Object dto,String data) throws CommonException, JsonParseException, JsonMappingException, IOException;

}
