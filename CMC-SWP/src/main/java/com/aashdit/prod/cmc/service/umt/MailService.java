package com.aashdit.prod.cmc.service.umt;

import com.aashdit.prod.cmc.model.umt.User;

public interface MailService {

	String getMessage(String string, String string2);
	
	Boolean mailPasswordDetails(String message, String subject, User user);
	
}
