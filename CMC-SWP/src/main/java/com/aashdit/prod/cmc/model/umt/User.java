package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import com.aashdit.prod.cmc.VO.CurrentUserVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;

@Entity
@Table(name = "t_umt_user", schema = "public")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class User implements Serializable {
	private static final long serialVersionUID = 285701719160134651L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "dob")
	private Date dateOfBirth;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "enabled")
	private Boolean isEnabled;

	@Column(name = "designation")
	private String designation;

	@Column(name = "allow_multiple_session")
	private Boolean allowMultipleSession;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_locked")
	private Boolean isLocked;

	@Column(name = "is_logged_in")
	private Boolean isLoggedIn;

	@Column(name = "wrong_login_cnt")
	private Integer wrongLoginCount;

	@ManyToOne
	@JoinColumn(name = "primary_role_id")
	private Role primaryRole;

	@Column(name = "profile_photo")
	private String profilePhoto;
	
	@Type(type = "hstore")
	@Column(columnDefinition = "hstore", name ="extra_props")
	private Map<String, String> extraProperties ;

	@Transient
	private List<Role> roles;

	@Column(name = "last_request_time")
	private Date lastRequestTime;

	@Column(name = "beneficiary_code")
	private String beneficiaryCode;
	@ManyToOne
	@JoinColumn(name = "created_by")
	@JsonIgnore
	private User createdBy;

	@CreatedDate
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "last_updated_by")
	@JsonIgnore
	private User lastUpdatedBy;

	@Column(name = "last_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date lastUpdatedOn;
	
	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "user_level")
	private String userLevel;
	
	@Column(name = "user_type_id")
	private Long userTypeId;
	
	@Transient
	private CurrentUserVo currentUserVo;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getAllowMultipleSession() {
		return allowMultipleSession;
	}

	public void setAllowMultipleSession(Boolean allowMultipleSession) {
		this.allowMultipleSession = allowMultipleSession;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public Integer getWrongLoginCount() {
		return wrongLoginCount;
	}

	public void setWrongLoginCount(Integer wrongLoginCount) {
		this.wrongLoginCount = wrongLoginCount;
	}

	public Role getPrimaryRole() {
		return primaryRole;
	}

	public void setPrimaryRole(Role primaryRole) {
		this.primaryRole = primaryRole;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Date getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(Date lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Map<String, String> getExtraProperties() {
		if (this.extraProperties == null)
		{
			this.extraProperties =  new HashMap<>();
		}
		return extraProperties;
	}

	public void setExtraProperties(Map<String, String> extraProperties) {
		this.extraProperties = extraProperties;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getBeneficiaryCode() {
		return beneficiaryCode;
	}

	public void setBeneficiaryCode(String beneficiaryCode) {
		this.beneficiaryCode = beneficiaryCode;
	}

	public CurrentUserVo getCurrentUserVo() {
		return currentUserVo;
	}

	public void setCurrentUserVo(CurrentUserVo currentUserVo) {
		this.currentUserVo = currentUserVo;
	}

}
