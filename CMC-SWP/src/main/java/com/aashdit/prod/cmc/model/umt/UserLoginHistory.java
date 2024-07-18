package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;

@Entity
@Table(name = "t_umt_user_login_history", schema = "public")
public class UserLoginHistory extends Auditable<User> implements Serializable{

	private static final long serialVersionUID = 3649339921550049779L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_login_history_id")
	private Long userLoginHistoryId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "logged_in_date")
	private Date loggedInDate;
	
	@Column(name = "logged_out_date")
	private Date loggedOutDate;
	
	@Column(name = "browser_details")
	private String browserDetails;
	
	@Column(name = "os_details")
	private String osDetails;
	
	@Column(name = "login_type")
	private String loginType;
	
	@Column(name = "login_status")
	private String loginStatus;
	
	/* ==============GETTERS & SETTERS====================  */

	public Long getUserLoginHistoryId() {
		return userLoginHistoryId;
	}

	public void setUserLoginHistoryId(Long userLoginHistoryId) {
		this.userLoginHistoryId = userLoginHistoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoggedInDate() {
		return loggedInDate;
	}

	public void setLoggedInDate(Date loggedInDate) {
		this.loggedInDate = loggedInDate;
	}

	public Date getLoggedOutDate() {
		return loggedOutDate;
	}

	public void setLoggedOutDate(Date loggedOutDate) {
		this.loggedOutDate = loggedOutDate;
	}

	public String getBrowserDetails() {
		return browserDetails;
	}

	public void setBrowserDetails(String browserDetails) {
		this.browserDetails = browserDetails;
	}

	public String getOsDetails() {
		return osDetails;
	}

	public void setOsDetails(String osDetails) {
		this.osDetails = osDetails;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	

}
