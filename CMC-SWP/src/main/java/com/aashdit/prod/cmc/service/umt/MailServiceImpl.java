package com.aashdit.prod.cmc.service.umt;

import java.io.StringWriter;
import java.util.Optional;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aashdit.prod.cmc.model.umt.MailQueued;
import com.aashdit.prod.cmc.model.umt.MailTemplatesMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.MailQueuedRepository;
import com.aashdit.prod.cmc.repository.umt.MailTemplateRepository;
import com.aashdit.prod.cmc.utils.CMCAppProperties;

@Service
@Component
public class MailServiceImpl implements MailService {

	private static final Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	@Autowired
	private  CMCAppProperties appProperties;
	
	@Autowired
	private MailQueuedRepository mailQueuedRepository;
	
	@Autowired
	private MailTemplateRepository mailTemplateRepository;
	
	
	@Autowired
	private VelocityEngine velocityEngine;
	

	@Override
	public String getMessage(String templateType, String templateCode) {
		String message="";
		try {
			MailTemplatesMaster	geTemplate = mailTemplateRepository.findByTemplateTypeAndTemplateCode(templateType, templateCode);
			if(Optional.ofNullable(geTemplate).isPresent()) {
				message = geTemplate.getTemplateName();
			}
			
		} catch (Exception e) {
			logger.error("ERROR - MailServiceImpl -> generateInvitationMail : " + e.getMessage());
		}
		return message;
	}
	
	@Override
	@Transactional
	public Boolean mailPasswordDetails(String message, String subject, User user) {
		try {
//			String emailId = rb.getString("mail.username");
			Template template = velocityEngine.getTemplate("./templates/passwordResetTemplate.vm");

			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("userName", user.getFirstName()+" "+user.getLastName());
			velocityContext.put("message", message);
//			velocityContext.put("url", rb.getString("pswd.login.url"));
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			String body = stringWriter.toString();
			MailQueued mObj = new MailQueued();
			mObj.setMailFrom(appProperties.getUsername());
			mObj.setMailTo(user.getEmail());
			mObj.setSubject(subject);
			mObj.setBody(body);
			mObj.setBodyType("HTML");
			mObj.setStatus("QUEUED");
			mObj = mailQueuedRepository.save(mObj);
			if (mObj != null) {
				return true;
			} else {
				logger.error("Mail Object is null in mailPasswordDetails method in MailServiceImpl.......");
				return false;
			}
		} catch (Exception ex) {
			logger.error("Exception occured in mailPasswordDetails method in MailServiceImpl-->"+ex.getMessage());
			return false;
		}
	}
	
}
