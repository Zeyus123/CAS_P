package com.aashdit.prod.cmc.service.umt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.aashdit.prod.cmc.VO.CurrentUserVo;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.framework.core.util.ClientInfo;
import com.aashdit.prod.cmc.framework.core.util.UploadFile;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserLoginHistory;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.repository.umt.RoleRepository;
import com.aashdit.prod.cmc.repository.umt.UserLoginHistoryRepository;
import com.aashdit.prod.cmc.repository.umt.UserRepository;
import com.aashdit.prod.cmc.repository.umt.UserRoleMapRepository;
import com.aashdit.prod.cmc.specs.umt.UserSpecification;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService, MessageSourceAware {

	private final Logger log = Logger.getLogger(this.getClass());

	private MessageSource messageSource;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleMapRepository userRoleMapRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserSpecification userSpecification;
	
	@Autowired
	private UserLoginHistoryRepository ulHistoryReporsitory;

	@Override
	public ServiceOutcome<User> save(User user) {
		ServiceOutcome<User> svcOutcome = new ServiceOutcome<User>();
		try
		{
			user = userRepository.saveAndFlush(user);
			svcOutcome.setData(user);
		}
		catch(Exception ex){
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return svcOutcome;
	}

	
	@Override
	public ServiceOutcome<User> findByUsername(String userName) {

		ServiceOutcome<User> svcOutcome = new ServiceOutcome<User>();
		try {
			User user = userRepository.findByUserName(userName);
			svcOutcome.setData(user);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));

		}
		return svcOutcome;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;

	}

	@Override
	public ServiceOutcome<User> findByUserId(Long userId) {
		ServiceOutcome<User> svcOutcome = new ServiceOutcome<User>();
		try {
			User user = userRepository.findByUserId(userId);
			svcOutcome.setData(user);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));

		}
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<Page<User>> findUserList(String searchTerm, PageRequest pageRequest) {
		ServiceOutcome<Page<User>> serviceOutcome = new ServiceOutcome<Page<User>>();
		try {
			Page<User> userList = null;
			if (searchTerm != null && !searchTerm.equals(""))
			{
				userList = userRepository.findAll(userSpecification.searchUser(searchTerm), pageRequest);
			}
			else
			{
				userList = userRepository.findAll(pageRequest);
			}
			
			serviceOutcome.setData(userList);
		} catch (Exception e) {
			log.error(e);
			serviceOutcome.setData(null);
			serviceOutcome.setOutcome(false);
			serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return serviceOutcome;
	}

	@Override
	public ServiceOutcome<User> lockNUnlockUserById(Long userId, Boolean isActive) {
		ServiceOutcome<User> serviceOutcome = new ServiceOutcome<User>();
		try {
			if (userId != null) {
				User user = userRepository.findByUserId(userId);
				if (isActive == true) {
					user.setIsActive(isActive);
					serviceOutcome.setMessage(messageSource.getMessage("msg.success", null,
							"User unlocked successfully", LocaleContextHolder.getLocale()));
				}

				if (isActive == false) {
					user.setIsActive(isActive);
					serviceOutcome.setMessage(messageSource.getMessage("msg.success", null, "User locked successfully",
							LocaleContextHolder.getLocale()));
				}

				user.setIsLocked(!isActive);

				user = userRepository.save(user);
				serviceOutcome.setData(user);
			} else {
				serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
				serviceOutcome.setData(null);
				serviceOutcome.setOutcome(false);
			}

		} catch (Exception e) {
			log.error(e);
			serviceOutcome.setData(null);
			serviceOutcome.setOutcome(false);
			serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}

		return serviceOutcome;
	}

	@Override
	public ServiceOutcome<List<User>> searchUser(String name) {
		ServiceOutcome<List<User>> svcOutcome = new ServiceOutcome<List<User>>();
		try {
			List<User> lstUsers = userRepository.searchForAutocomplete(name.toLowerCase());
			lstUsers.stream().forEach(u -> {
				u.setCreatedBy(null);
				u.setLastUpdatedBy(null);

				u.getPrimaryRole().setCreatedBy(null);
				u.getPrimaryRole().setLastUpdatedBy(null);
			});
			svcOutcome.setData(lstUsers);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));

		}
		return svcOutcome;
	}

	@Override
	public List<Role> findActiveRole() {
		List<Role> rolelist = null;
		try {
			rolelist = roleRepository.findUIRolesOrderByDisplayName();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return rolelist;
	}

	@Override
	public ServiceOutcome<User> updateUser(Long userId, String userName, String firstName, String lastName,
			Date dateOfBirth, String mobile, String email, Long[] userRoleHcMapId, Long[] roleId, Long[] isPrimary,
			String[] status, String designation) {
		ServiceOutcome<User> outcome = new ServiceOutcome<User>();
		try {
			User curUser = SecurityHelper.getCurrentUser();
			User prvUserDtls = userRepository.findById(userId).get();
			prvUserDtls.setFirstName(firstName);
			prvUserDtls.setLastName(lastName);
			prvUserDtls.setDateOfBirth(dateOfBirth);
			prvUserDtls.setUserName(userName);
			prvUserDtls.setMobile(mobile);
			prvUserDtls.setEmail(email);
			prvUserDtls.setLastUpdatedBy(curUser);
			prvUserDtls.setDesignation(designation);

			prvUserDtls = userRepository.save(prvUserDtls);

			for (int i = 0; i < roleId.length; i++) {
				UserRoleMap userRoleMap = null;
				
				if (userRoleHcMapId[i] != 0) {
					//Existing mapping
					
					userRoleMap = userRoleMapRepository.findById(userRoleHcMapId[i]).get();
					userRoleMap.setRoleId(roleId[i]);
					userRoleMap.setUserId(prvUserDtls.getUserId());
					userRoleMap.setLastUpdatedBy(curUser);
					userRoleMap.setLastUpdatedOn(new Date());
					if (status[i].equals("0")) {
						userRoleMap.setIsActive(false);
					} else {
						userRoleMap.setIsActive(true);
					}
					
					userRoleMapRepository.save(userRoleMap);
				}
				else
				{
					//New Mapping 
					
					StringBuffer sb = new StringBuffer();
					
					// 1. Get List of all Roles that can be assigned from the screen. This will
					// exclude SYSTEM_ADMIN and ADMIN Roles
					// as these are set from the back end
					List<Role> lstUIRoles = roleRepository.findByDisplayOnPage(true);
					log.debug("Pass Step 1");
					
					final Long currRoleId = roleId[i];
					log.debug("Current Role Id from UI is " + currRoleId);
					Integer currentAllocations = userRoleMapRepository.findByRoleId(roleId[i]).size();
					log.debug(currentAllocations);
					log.debug("-- 2.1");
					if (currentAllocations == null) {
						currentAllocations = 0;
					}
					
					Role theRole = lstUIRoles.stream().filter(p -> p.getRoleId() == currRoleId).findAny().orElse(null);
					if (theRole != null) {
						log.debug("-- 2.2");
						if (theRole.getMaxAssignments() == -1) {
							log.debug("-- No limit on assignments");
							userRoleMap = new UserRoleMap();
							userRoleMap.setUserId(prvUserDtls.getUserId());
							userRoleMap.setRoleId(roleId[i]);
							userRoleMap.setIsActive(true);
							log.debug("-- 2.4.a");
							userRoleMapRepository.save(userRoleMap);
							log.debug("-- 2.5.a");
							// 3. If the Role has been marked as the primary Role. Set it for the user
							if (isPrimary[i] == 1) {
								log.debug("-- 2.6.a");
								prvUserDtls.setPrimaryRole(theRole);
								prvUserDtls = userRepository.save(prvUserDtls);
								log.debug("-- 2.7.a");
							}
						} else {
							log.debug("-- Limit on assignments : " + theRole.getMaxAssignments());
							if (currentAllocations < theRole.getMaxAssignments()) {
								log.debug("-- 2.3");
								userRoleMap = new UserRoleMap();
								userRoleMap.setUserId(prvUserDtls.getUserId());
								userRoleMap.setRoleId(roleId[i]);
								userRoleMap.setIsActive(true);
								log.debug("-- 2.4");
								userRoleMapRepository.save(userRoleMap);
								log.debug("-- 2.5");
								// 3. If the Role has been marked as the primary Role. Set it for the user
								if (isPrimary[i] == 1) {
									log.debug("-- 2.6");
									prvUserDtls.setPrimaryRole(theRole);
									prvUserDtls = userRepository.save(prvUserDtls);
									log.debug("-- 2.7");
								}
							} else {
								log.debug("-- 3.1");
								sb.append("The Role ").append(theRole.getDisplayName())
										.append(" could not be allocated as the maximum allocations for this role ")
										.append(theRole.getMaxAssignments()).append(" have been met. <br/>");
								log.error(sb.toString());
							}
						}
					} else {
						log.debug("-- 4.1");
						sb.append("A Role could cannot be allocated from the UI. <br/> ");
						log.error(sb.toString());
					}
					
				}
			}
				
			outcome.setData(prvUserDtls);
			outcome.setMessage(messageSource.getMessage("msg.success", null, "User Data Updated Successfully",
					LocaleContextHolder.getLocale()));
		} catch (Exception e) {
			log.error(e);
			outcome.setData(null);
			outcome.setOutcome(false);
			outcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return outcome;
	}

	@Override
	public List<UserRoleMap> findUserRoleMapByUserId(Long userId) {

		return userRoleMapRepository.findByUserId(userId);
	}

	@Override
	@Transactional
	public ServiceOutcome<User> addUser(String username, String firstname, String lastname, Date dateOfbirth,
			String userMobile, String userEmail, Long[] roleId, Long[] isPrimary, String designation,String userType,String level,Long userTypeId,Long[] objectTypeId, String staffCode) {

		ServiceOutcome<User> outcome = new ServiceOutcome<User>();
		try {
			User currUser = SecurityHelper.getCurrentUser();
			String password = "123456";

			// 0. Save the user
			User userDtls = new User();
			userDtls.setFirstName(firstname);
			userDtls.setLastName(null);
			userDtls.setUserName(username);
			userDtls.setDateOfBirth(dateOfbirth);
			userDtls.setMobile(userMobile);
			userDtls.setEmail(userEmail);
			userDtls.setPassword(bCryptPasswordEncoder.encode(password));
			userDtls.setDesignation(designation);
			userDtls.setBeneficiaryCode(staffCode);

			userDtls.setIsActive(true);
			userDtls.setIsEnabled(true);
			userDtls.setIsLocked(false);
			userDtls.setIsLoggedIn(false);
			userDtls.setWrongLoginCount(0);
			userDtls.setAllowMultipleSession(true);

			userDtls.setCreatedOn(new Date());
			userDtls.setCreatedBy(currUser);
			userDtls.setLastUpdatedOn(new Date());
			userDtls.setLastUpdatedBy(currUser);
			
			userDtls.setUserType(userType);
			userDtls.setUserLevel(level);
			userDtls.setUserTypeId(userTypeId);

			userDtls = userRepository.save(userDtls);
			log.debug("Saved User in DB");

			StringBuffer sb = new StringBuffer();

			// 1. Get List of all Roles that can be assigned from the screen. This will
			// exclude SYSTEM_ADMIN and ADMIN Roles
			// as these are set from the back end
			List<Role> lstUIRoles = roleRepository.findByDisplayOnPage(true);
			log.debug("Pass Step 1");

			// 2. For each role in the Long[] roleId sent from the UI, filter out any roles
			// that match or exceed the
			// Maximum Allocations set against the Role
			for (int i = 0; i < roleId.length; i++) {
				final Long currRoleId = roleId[i];
				log.debug("Current Role Id from UI is " + currRoleId);
				Integer currentAllocations = userRoleMapRepository.findByRoleId(roleId[i]).size();
				log.debug(currentAllocations);
				log.debug("-- 2.1");
				if (currentAllocations == null) {
					currentAllocations = 0;
				}
				Role theRole = lstUIRoles.stream().filter(p -> p.getRoleId() == currRoleId).findAny().orElse(null);
				if (theRole != null) {
					log.debug("-- 2.2");
					if (theRole.getMaxAssignments() == -1) {
						log.debug("-- No limit on assignments");
						UserRoleMap userRoleMap = new UserRoleMap();
						userRoleMap.setUserId(userDtls.getUserId());
						userRoleMap.setRoleId(roleId[i]);
						userRoleMap.setIsActive(true);
						userRoleMap.setObjectType(level);
						userRoleMap.setObjectTypeId(objectTypeId[i]);
						log.debug("-- 2.4.a");
						userRoleMapRepository.save(userRoleMap);
						log.debug("-- 2.5.a");
						// 3. If the Role has been marked as the primary Role. Set it for the user
						if (isPrimary[i] == 1) {
							log.debug("-- 2.6.a");
							userDtls.setPrimaryRole(theRole);
							userDtls = userRepository.save(userDtls);
							log.debug("-- 2.7.a");
						}
					} else {
						log.debug("-- Limit on assignments : " + theRole.getMaxAssignments());
						if (currentAllocations < theRole.getMaxAssignments()) {
							log.debug("-- 2.3");
							UserRoleMap userRoleMap = new UserRoleMap();
							userRoleMap.setUserId(userDtls.getUserId());
							userRoleMap.setRoleId(roleId[i]);
							userRoleMap.setIsActive(true);
							log.debug("-- 2.4");
							userRoleMapRepository.save(userRoleMap);
							log.debug("-- 2.5");
							// 3. If the Role has been marked as the primary Role. Set it for the user
							if (isPrimary[i] == 1) {
								log.debug("-- 2.6");
								userDtls.setPrimaryRole(theRole);
								userDtls = userRepository.save(userDtls);
								log.debug("-- 2.7");
							}
						} else {
							log.debug("-- 3.1");
							sb.append("The Role ").append(theRole.getDisplayName())
									.append(" could not be allocated as the maximum allocations for this role ")
									.append(theRole.getMaxAssignments()).append(" have been met. <br/>");
							log.error(sb.toString());
						}
					}
				} else {
					log.debug("-- 4.1");
					sb.append("A Role could cannot be allocated from the UI. <br/> ");
					log.error(sb.toString());
				}
			}
			log.debug("-- 5.1");
			sb.append(messageSource.getMessage("msg.success", null, "User Data Saved Successfully",
					LocaleContextHolder.getLocale()));
			log.debug("-- 5.2");
			outcome.setMessage(sb.toString());
			outcome.setData(userDtls);

		} catch (Exception e) {
			log.error(e);
			outcome.setData(null);
			outcome.setOutcome(false);
			outcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return outcome;
	}

	@Override
	public ServiceOutcome<User> userRegistration(User user) {
		ServiceOutcome<User> outcome = new ServiceOutcome<User>();

		try {
			User currUser = SecurityHelper.getCurrentUser();

			User userDtls = new User();
			userDtls.setFirstName(user.getFirstName());
			userDtls.setLastName(user.getLastName());
			userDtls.setUserName(user.getUserName());
			userDtls.setMobile(user.getMobile());
			userDtls.setEmail(user.getEmail());
			userDtls.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			userDtls.setCreatedOn(new Date());
			userDtls.setCreatedBy(currUser);
			userDtls.setLastUpdatedOn(new Date());
			userDtls.setLastUpdatedBy(currUser);

			userDtls.setIsActive(true);
			userDtls.setPrimaryRole(roleRepository.findByRoleCode("ROLE_PUBLIC"));
			userDtls.setDesignation(user.getDesignation());

			userDtls = userRepository.save(userDtls);
			outcome.setData(userDtls);
			outcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
		} catch (Exception e) {
			log.error(e);
			outcome.setData(null);
			outcome.setOutcome(false);
			outcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return outcome;
	}

	@Override
	public ServiceOutcome<List<User>> getUsersByLevelAndId(Long roleLevelId, Long entityId) {
		ServiceOutcome<List<User>> svcOutcome = new ServiceOutcome<List<User>>();
		try {
			List<User> lstUsers = userRepository.findUserByLevelAndId(roleLevelId, entityId);
			// Remove this as this causes JSON error during deserialization due to circular
			// reference
			lstUsers.stream().forEach(u -> {
				u.setCreatedBy(null);
				u.setLastUpdatedBy(null);

				u.getPrimaryRole().setCreatedBy(null);
				u.getPrimaryRole().setLastUpdatedBy(null);
			});
			svcOutcome.setData(lstUsers);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));

		}
		return svcOutcome;
	}

	public ServiceOutcome<List<User>> getAllUsers() {
		ServiceOutcome<List<User>> svcOutcome = new ServiceOutcome<List<User>>();
		try {
			List<User> lstUsers = userRepository.findAll();
			svcOutcome.setData(lstUsers);
			svcOutcome.setOutcome(true);
		} catch (Exception ex) {
			log.error(ex);

			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));

		}
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<User> updateProfile(User user, MultipartFile userProfileImage) {
		ServiceOutcome<User> outcome = new ServiceOutcome<User>();
		User curUser = SecurityHelper.getCurrentUser();
		try {
			User userDtls = userRepository.findById(curUser.getUserId()).get();
			userDtls.setFirstName(user.getFirstName());
			userDtls.setLastName(user.getLastName());
			userDtls.setUserName(user.getUserName());
			userDtls.setMobile(user.getMobile());
			userDtls.setEmail(user.getEmail());
			userDtls.setLastUpdatedOn(new Date());
			userDtls.setDateOfBirth(user.getDateOfBirth());
			userDtls.setDesignation(user.getDesignation());

			if (!userProfileImage.isEmpty()) {
				userDtls.setProfilePhoto(
						UploadFile.upload(userProfileImage, "profile picture", userDtls.getUserName(), "umt"));
			}

			userDtls.setLastUpdatedOn(new Date());
			userDtls.setLastUpdatedBy(curUser);

			userDtls = userRepository.save(userDtls);
			outcome.setData(userDtls);
			outcome.setMessage(messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
		} catch (Exception e) {
			log.error(e);
			outcome.setData(null);
			outcome.setOutcome(false);
			outcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		return outcome;
	}

	@Override
	public Boolean saveResetPassword(Long userId, String txtRePass) {
		Boolean status = false;
		try {
			User user = userRepository.findById(userId).get();
			user.setPassword(bCryptPasswordEncoder.encode(txtRePass));
			userRepository.save(user);
			status = true;
		} catch (Exception e) {
			log.error(e);
			status = false;
		}
		return status;
	}

	@Override
	public ServiceOutcome<Boolean> createLoginHistory(User user, HttpServletRequest request) {
		ServiceOutcome<Boolean> svcOutcome = new ServiceOutcome<>();
		try
		{
			UserLoginHistory ulHistory = new UserLoginHistory();
		
			ulHistory.setBrowserDetails(ClientInfo.getClientBrowser(request));
			ulHistory.setEmail(user.getEmail());
			ulHistory.setFirstName(user.getFirstName());
			ulHistory.setLastName(user.getLastName());
			ulHistory.setLoggedInDate(new Date());
			ulHistory.setLoggedOutDate(null);
			ulHistory.setLoginStatus("LOGIN");
			ulHistory.setLoginType(user.getPrimaryRole().getRoleCode());
			ulHistory.setMobile(user.getMobile());
			ulHistory.setOsDetails(ClientInfo.getClientOS(request));
			ulHistory.setUserName(user.getUserName());
			ulHistory.setUserId(user.getUserId());
			ulHistory.setIpAddress(ClientInfo.getClientIpAddr(request));
			
			ulHistoryReporsitory.save(ulHistory);
			
			svcOutcome.setData(true);
		}
		catch(Exception ex)
		{
			svcOutcome.setOutcome(false);
			svcOutcome.setData(false);
			svcOutcome.setMessage(ex.getMessage());
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<Boolean> createLogoutHistory(User user, HttpServletRequest request) {
		ServiceOutcome<Boolean> svcOutcome = new ServiceOutcome<>();
		try
		{
			UserLoginHistory ulHistory = new UserLoginHistory();
		
			ulHistory.setBrowserDetails(ClientInfo.getClientBrowser(request));
			ulHistory.setEmail(user.getEmail());
			ulHistory.setFirstName(user.getFirstName());
			ulHistory.setLastName(user.getLastName());
			ulHistory.setLoggedInDate(new Date());
			ulHistory.setLoggedOutDate(null);
			ulHistory.setLoginStatus("LOGOUT");
			ulHistory.setLoginType(user.getPrimaryRole().getRoleCode());
			ulHistory.setMobile(user.getMobile());
			ulHistory.setOsDetails(ClientInfo.getClientOS(request));
			ulHistory.setUserName(user.getUserName());
			ulHistory.setUserId(user.getUserId());
			
			ulHistoryReporsitory.save(ulHistory);
			
			svcOutcome.setData(true);

		}
		catch(Exception ex)
		{
			svcOutcome.setOutcome(false);
			svcOutcome.setData(false);
			svcOutcome.setMessage(ex.getMessage());
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<CurrentUserVo> getUserObjectUserListRoleObject(Long userId,String userName,String benfCode) {
		ServiceOutcome<CurrentUserVo> svcOutcome = new ServiceOutcome<>();
		try
		{
			User user = new User();

			if(!userId.equals(0L)){
				 user = userRepository.findById(userId).get();
			}else if (!userName.equals("")) {
				user = userRepository.findByUserName(userName);
			} else if (Optional.ofNullable(benfCode).isPresent()) {
				user = userRepository.findByBeneficiaryCode(benfCode);
			}
			CurrentUserVo currentUserVo = new CurrentUserVo();
			currentUserVo.setUserType(user.getUserType());
			currentUserVo.setUserId(user.getUserId());
			currentUserVo.setFirstName(user.getFirstName());
			currentUserVo.setLastName(user.getLastName());
			currentUserVo.setUserName(user.getUserName());
			currentUserVo.setPassword(user.getPassword());
			currentUserVo.setUserLevel(user.getUserLevel());
			currentUserVo.setUserType(user.getUserType());
			currentUserVo.setMobile(user.getMobile());
			currentUserVo.setEmail(user.getEmail());
			currentUserVo.setUserTypeId(user.getUserTypeId());
			currentUserVo.setBeneficiaryCode(user.getBeneficiaryCode());
			currentUserVo.setPrimaryRole(user.getPrimaryRole());

			svcOutcome.setData(currentUserVo);
			svcOutcome.setMessage("Data Fetched Successfully");
		}
		catch(Exception ex)
		{
			svcOutcome.setOutcome(false);
			svcOutcome.setData(null);
			svcOutcome.setMessage(ex.getMessage());
		}

		return svcOutcome;
	}

	@Override
	public ServiceOutcome<List<CurrentUserVo>> getUserListByRoleIdAndObjIdAndLevel(Long roleId, Long userLevelId,String userLevel, String appCode) {
		ServiceOutcome<List<CurrentUserVo>> svcOutcome = new ServiceOutcome<>();
		try {
			List<CurrentUserVo> lstCurrentUserVo = new ArrayList<>();
			List<User> lstUser = userRepository.findByUserListByRoleIdAndObjIdAndLevel(roleId,userLevelId,userLevel,appCode);
			for (User user : lstUser) {
				CurrentUserVo currentUserVo = new CurrentUserVo();
				currentUserVo.setUserType(user.getUserType());
				currentUserVo.setUserId(user.getUserId());
				currentUserVo.setFirstName(user.getFirstName());
				currentUserVo.setLastName(user.getLastName());
				currentUserVo.setUserName(user.getUserName());
				currentUserVo.setPassword(user.getPassword());
				currentUserVo.setUserLevel(user.getUserLevel());
				currentUserVo.setUserType(user.getUserType());
				currentUserVo.setMobile(user.getMobile());
				currentUserVo.setEmail(user.getEmail());
				currentUserVo.setUserTypeId(user.getUserTypeId());
				currentUserVo.setBeneficiaryCode(user.getBeneficiaryCode());
				currentUserVo.setPrimaryRole(user.getPrimaryRole());
				lstCurrentUserVo.add(currentUserVo);
			}
			if(lstCurrentUserVo.size()>0)
			{
				svcOutcome.setData(lstCurrentUserVo);
				svcOutcome.setMessage("Data Fetched Successfully");
			}

		}
		catch(Exception ex){
		svcOutcome.setOutcome(false);
		svcOutcome.setData(null);
		svcOutcome.setMessage(ex.getMessage());
	}
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<Role> getRoleObjectByRoleCode(String roleCode) {
		ServiceOutcome<Role> svcOutcome = new ServiceOutcome<>();
		try
		{
			Role role = roleRepository.findByRoleCode(roleCode);
			svcOutcome.setData(role);
			svcOutcome.setMessage("Data Fetched Successfully");
		}
		catch(Exception ex)
		{
			svcOutcome.setOutcome(false);
			svcOutcome.setData(null);
			svcOutcome.setMessage(ex.getMessage());
		}
		return svcOutcome;
	}


	@Override
	public ServiceOutcome<Role> getRoleObjectByRoleId(Long roleId) {
		ServiceOutcome<Role> svcOutcome = new ServiceOutcome<>();
		try
		{
			Role role = roleRepository.findById(roleId).get();
			svcOutcome.setData(role);
			svcOutcome.setMessage("Data Fetched Successfully");
		}
		catch(Exception ex)
		{
			svcOutcome.setOutcome(false);
			svcOutcome.setData(null);
			svcOutcome.setMessage(ex.getMessage());
		}
		return svcOutcome;
	}

}
