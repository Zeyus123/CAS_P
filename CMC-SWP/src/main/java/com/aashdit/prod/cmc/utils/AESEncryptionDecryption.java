package com.aashdit.prod.cmc.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
	 * Java String Encryption Decryption Example
	 * @author Ajay Kumar
	 *
	 */
     

	public class AESEncryptionDecryption {
	    private static SecretKeySpec secretKey;
	    private static byte[] key;
	    private static final String ALGORITHM = "AES";
	    
	    private static String mamatasecretKey="Ahja+a9gBQ+qc0UArVg0Yg==";
	    private static String APP_MAMATA_CODE="MAMATA";
	   // private static String APP_SCHEME_CODE="MAMATA#SCHEMECODE#123456789";
	    
	    private static String manadeyasecretKey="W32ga+a9a9ggBQ+0Yg==";
	    private static String APP_MANEDEYA_CODE="MANEDEYA";
	    private static final String SECRET_KEY = "HAMRAMKAHAI";
	  //  private static String APP_SCHEME_CODE="MAMATA#SCHEMECODE#123456789";
	    

	    public void prepareSecreteKey(String myKey) {
	        MessageDigest sha = null;
	        try {
	            key = myKey.getBytes(StandardCharsets.UTF_8);
	            sha = MessageDigest.getInstance("SHA-1");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            secretKey = new SecretKeySpec(key, ALGORITHM);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	    }

	    public String encrypt(String strToEncrypt, String secret) {
	        try {
	            prepareSecreteKey(secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	        } catch (Exception e) {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }

	    public String decrypt(String strToDecrypt, String secret) {
	        try {
	            prepareSecreteKey(secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	        } catch (Exception e) {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }
	   
	    public static String decryptUserRequestedData(String encData, String projectCode) {
	    	String encryptedString = "";
	    	try {
	    		 AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
	    		 if (projectCode.equals(APP_MAMATA_CODE)) {
					 encryptedString = aesEncryptionDecryption.decrypt(encData, mamatasecretKey);
	    		 }else if(projectCode.equals(APP_MANEDEYA_CODE)) {
                	 encryptedString = aesEncryptionDecryption.decrypt(encData, manadeyasecretKey);
	    		 }
				
			} catch (Exception e) {
				return null;
			}
			return encryptedString;
	    	
	    }

	    public static void main(String[] args) {
	    	String originalString="";
					for (int i = 0; i < 150; i++) {
						originalString = originalString + "Sorry Sorry Sorry Sorry";	
					}
	        AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
	     //   String encryptedString = aesEncryptionDecryption.encrypt(originalString, wcdsecretKey);
	       // String decryptedString = aesEncryptionDecryption.decrypt(originalString, wcdsecretKey);

	        //System.out.println(originalString);
	       // System.out.println(encryptedString);
	       // System.out.println(decryptedString);
	    }

	    
	    public static String encryptString(String data) throws Exception {
	        DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes());
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey key = keyFactory.generateSecret(keySpec);

	        Cipher cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.ENCRYPT_MODE, key);

	        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
	        return Base64.getEncoder().encodeToString(encryptedBytes);
	    }

	    public static String decryptString(String encryptedData) throws Exception {
	        DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes());
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey key = keyFactory.generateSecret(keySpec);

	        Cipher cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.DECRYPT_MODE, key);

	        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decryptedBytes, "UTF-8");
	    }
	    
	    
		
	}
