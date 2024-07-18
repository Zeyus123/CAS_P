package com.aashdit.prod.cmc.framework.core.util;

import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenParser {

	public static String getUserName(String token) {
		String[] split_string = token.split("\\.");
		String base64EncodedBody = split_string[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));
		
		JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
		String userName = jsonObject.get("sub").getAsString();
		return userName;
	}
}
