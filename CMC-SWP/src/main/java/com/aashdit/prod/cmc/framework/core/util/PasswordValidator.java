package com.aashdit.prod.cmc.framework.core.util;

public class PasswordValidator {
	
	@SuppressWarnings("unused")
	public static boolean checkString(String str) {
	    char ch;
	    boolean capitalFlag = false;
	    boolean lowerCaseFlag = false;
	    boolean numberFlag = false;
	    boolean spCharFlag = false;
	    boolean lengthFlag = false;
	    boolean allChkFlag = false;
	    
	    String specialCharacters=" !#$%&'()*+,-./:;<=>?@[]^_`{|}";
	   
	    for(int i=0;i < str.length();i++) {
	        ch = str.charAt(i);
	      
	        if( Character.isDigit(ch)) {
	            numberFlag = true;
	        }
	        else if (Character.isUpperCase(ch)) {
	            capitalFlag = true;
	        } else if (Character.isLowerCase(ch)) {
	            lowerCaseFlag = true;
	        }
	        else if (specialCharacters.contains(Character.toString(str.charAt(i)))) {
	        	spCharFlag = true;
            }
	        
	        if(numberFlag && capitalFlag && lowerCaseFlag){
	        	allChkFlag=true;
	       }
	    }

	    if(str.length() >= 8 && str.length() <= 15 && allChkFlag){
	    	 return true;
	    }
	    else{
	    return false;
	    }
	}
}
