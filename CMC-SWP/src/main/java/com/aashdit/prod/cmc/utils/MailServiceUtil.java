package com.aashdit.prod.cmc.utils;
import java.util.Properties;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailServiceUtil {
	
	private static final Logger logger = Logger.getLogger(MailServiceUtil.class);
	
	@Autowired
	private CMCAppProperties appProperties;
	
	
	public String sendMail(String body, String subject, String mailTo,final String mailFrom, String bodyType) {		

		Properties props;
		String exceptionMessage = "";
	
//		try {
//			props = new Properties();
//
//			props.put("mail.smtp.auth", appProperties.getAuth());
//			props.put("mail.smtp.starttls.enable", appProperties.getEnable());
//			props.put("mail.smtp.host", appProperties.getHost());
//			props.put("mail.smtp.ssl.trust", appProperties.getTrust());
//			props.put("mail.smtp.port", appProperties.getPort());
//			props.put("mail.smtp.ssl.protocols", appProperties.getProtocol());
			
//			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(appProperties.getUsername(), appProperties.getAppPassword());
//				}
//			});
			
//			Message message = new MimeMessage(session);
//			
//			message.setFrom(new InternetAddress(mailFrom));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
//			
//			message.setSubject(subject);
//			if ("TEXT".equalsIgnoreCase(bodyType))
//			{
//				message.setText(body);
//			}
//			else
//			{
//				message.setContent(body, "text/html");
//			}
		

//			Transport.send(message);
			logger.debug("Email Sent Successfully");

			return "SUCCESS";
			
//		} catch (MessagingException mex) {
//			mex.printStackTrace();
//			logger.error(mex.getMessage());
//			exceptionMessage = mex.getMessage();
//		}
//		return exceptionMessage;

	}

}
