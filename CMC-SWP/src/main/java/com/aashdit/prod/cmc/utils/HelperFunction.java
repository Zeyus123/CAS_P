package com.aashdit.prod.cmc.utils;

import java.io.File;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.ResourceBundle;

import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Timestamp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelperFunction {
	
	@Value("${UPLOAD.FILE.PATH}")
    private String FILE_PATH;

	private ResourceBundle rb =ResourceBundle.getBundle("application");
	private ResourceBundle rb1 =ResourceBundle.getBundle("application-"+rb.getString("spring.profiles.active"));

    public static HashMap<String, Object> onSuccess(Object data){
        HashMap<String, Object> eJsonObject = new HashMap<>();
        eJsonObject.put("status", true);
        eJsonObject.put("data", data);
        return  eJsonObject;
    }
    public static HashMap<String, Object> onError(Exception e){
        e.printStackTrace();
        HashMap<String, Object> eJsonObject = new HashMap<>();
        eJsonObject.put("status", false);
        eJsonObject.put("errorMessage", e.getMessage());
        log.error("Error: {}", e.getMessage());
        return  eJsonObject;
    }

    public static JSONObject onGsonArraySuccess(JsonArray data) throws Exception{
        JSONObject eJsonObject = new JSONObject();
        // parse data
        JSONParser parser = new JSONParser();
        JSONArray parsedData = (JSONArray) parser.parse(data.toString());
        eJsonObject.put("status", true);
        eJsonObject.put("data", parsedData);
        return  eJsonObject;
    }

    public static JSONObject onGsonObjectSuccess(JsonObject data) throws Exception{
        JSONObject eJsonObject = new JSONObject();
        // parse data
        JSONParser parser = new JSONParser();
        JSONObject parsedData = (JSONObject) parser.parse(data.toString());
        eJsonObject.put("status", true);
        eJsonObject.put("data", parsedData);
        return  eJsonObject;
    }

    //Calculate the distance between two Date in days
    public static int daysBetween(java.util.Date one, java.util.Date two) {
        return (int)( (one.getTime() - two.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    public String uploadDocumentEF(MultipartFile multipartFile) throws Exception {
        if (multipartFile == null) {
            throw new Exception("MultipartFile is null");
        }
        String originalFilenameForGst = "";
        String batchid = "";
        String filePath = null;
        Timestamp fileIdTimestamp = new Timestamp(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer(fileIdTimestamp.toString());
        sb.replace(0, 2, "");

        if (multipartFile.getOriginalFilename().isEmpty()) {
            throw new Exception("please send both document");
        }
        originalFilenameForGst = multipartFile.getOriginalFilename();

        batchid = "FILE" + fileIdTimestamp;
        batchid = batchid.replaceAll("[^A-Z0-9]", "");
        filePath = FILE_PATH + File.separator + File.separator + batchid;
        File destinationFile = new File(filePath);
        File dirTemp = new File(destinationFile.getAbsolutePath());
        if (!dirTemp.exists()) {
            dirTemp.mkdirs();
        }
        File destinationForGst = new File(filePath + File.separator + originalFilenameForGst);
        try {
        	multipartFile.transferTo(destinationForGst);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String gstDocPath = (filePath + File.separator + originalFilenameForGst);
        return gstDocPath;	
		
	}

    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String uploadFile(MultipartFile file, String applicationName) throws Exception {
        String filePath;
        if (file.isEmpty()) {
            throw new Exception("please send document");
        }
        filePath = rb1.getString("UPLOAD.FILE.PATH") + File.separator + applicationName;
        File destinationFile = new File(filePath);
        File dirTemp = new File(destinationFile.getAbsolutePath());
        if (dirTemp.exists()) {
            dirTemp.delete();
            dirTemp.mkdirs();
        }else {
            dirTemp.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        File destinationForGst = new File(filePath + File.separator + originalFilename);
        try {
            file.transferTo(destinationForGst);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return (filePath + File.separator + originalFilename);
    }
    
 // Base64 encode a String
    public static String base64Encode(String data) {
        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
        return new String(encodedBytes);
    }

    // Base64 decode a String
    public static String base64Decode(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data.getBytes());
        return new String(decodedBytes);
    }

    // Base64 encode a Long
    public static String base64EncodeLong(Long data) {
        byte[] longBytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            longBytes[i] = (byte) (data & 0xFF);
            data >>= 8;
        }
        byte[] encodedBytes = Base64.getEncoder().encode(longBytes);
        return new String(encodedBytes);
    }

    // Base64 decode a Long
    public static Long base64DecodeLong(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data.getBytes());
        long result = 0;
        for (byte b : decodedBytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return result;
    }

    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars/**",
            "/actuator/health",
            "/swagger-ui/**",
            "/erp",
            // other public endpoints of your API may be appended to this array
            "/api/login",
            "/api/allow",
            "/api/allowAll",
            "/api/core",
            "/allow/get-role-by-roleCode"

    };

    public static boolean isSkipUrl(@NonNull String url) {
        for (String skipUrl : AUTH_WHITELIST) {
            if (url.contains(skipUrl)) {
                return true;
            }
        }
        return false;
    }




}
