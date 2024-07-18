package com.aashdit.prod.cmc.framework.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import org.jboss.logging.Logger;

public class SmsServiceUtil {

	private final static Logger logger = Logger.getLogger(SmsServiceUtil.class);

	public static void sendTransactionalMessage(String mobileno, String msg) {

		new Thread(new Runnable() {
			public void run() {
				ResourceBundle rb = ResourceBundle.getBundle("application");
				// Your authentication key.
				String authkey = rb.getString("MSG91.KEY");
				// Multiple mobile numbers separated by comma.
				String mobiles = mobileno;
				// Sender ID, While using route4 sender id should be 6
				// characters long.
				String senderId = rb.getString("MSG91.SENDER");
				// Your message to send, Add URL encoding here.
				String message = msg;
				// Define Route
				String route = "4";

				// Prepare Url
				URLConnection myURLConnection = null;
				URL myURL = null;
				BufferedReader reader = null;

				// Encoding Message
				String encoded_message = URLEncoder.encode(message);

				// Send SMS API
				String mainUrl = "http://api.msg91.com/api/sendhttp.php?";

				// Prepare Parameter string
				StringBuilder sbPostData = new StringBuilder(mainUrl);
				sbPostData.append("authkey=" + authkey);
				sbPostData.append("&mobiles=" + mobiles);
				sbPostData.append("&message=" + encoded_message);
				sbPostData.append("&route=" + route);
				sbPostData.append("&sender=" + senderId);

				// Final String
				mainUrl = sbPostData.toString();
				try {
					// Prepare Connection
					myURL = new URL(mainUrl);
					myURLConnection = myURL.openConnection();
					myURLConnection.connect();
					reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
					// Reading Response
					String response;
					while ((response = reader.readLine()) != null)
						// Print Response
						logger.info("Response From SMS Server: " + reader.readLine());
						logger.info("SMS sent successfully..........");
					// Finally close connection.
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}).start();
	}

}
	