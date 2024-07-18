package com.aashdit.prod.cmc.utils;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlDataDecoder {

	public static String decoder(String data, String request) {
		String decodeData = null;
		try {
			String encData = new String(Base64.getDecoder().decode(data));
			String formPsk = request;
			formPsk = request.substring(0, 16);
			AesUtil formAesUtil = new AesUtil(128, 1000);
			if(encData != null && encData.split("::").length ==3) {
				decodeData = formAesUtil.decrypt(encData.split("::")[1], encData.split("::")[0], formPsk, encData.split("::")[2]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while decoding data" ,e);
		}
		return decodeData;
	}
}
