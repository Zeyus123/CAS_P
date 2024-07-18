package com.aashdit.prod.cmc.VO.umt;

public class AuthRequest {
	private String userName;
	private String password;
	private String captcha;
	private String appCode;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public AuthRequest(String userName, String password, String captcha, String appCode) {
		super();
		this.userName = userName;
		this.password = password;
		this.captcha = captcha;
		this.appCode = appCode;
	}
	public AuthRequest() {
		super();
	}
	
	
}
