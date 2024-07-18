package com.aashdit.prod.cmc.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aashdit.prod.cmc.VO.umt.AuthRequest;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.LoggedInUser;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.repository.umt.MenuRepository;
import com.aashdit.prod.cmc.service.umt.RoleService;
import com.aashdit.prod.cmc.service.umt.UserService;
import com.aashdit.prod.cmc.utils.AesUtil;


@Controller
@RequestMapping("/overwrite")
public class CustomLoginController implements MessageSourceAware {
	
	private static final Logger logger = Logger.getLogger(CustomLoginController.class);

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	private MessageSource messageSource;
	
	@Autowired
	private UserService userService;
	
	@Value("${post.login.url}")
	private String POST_LOGIN_URL;

	@Value("${captcha.options}")
	private String CAPTCHA_OPTIONS;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private RoleService roleService;

	private List<Menu> m_menus;



	@PostMapping(path = { "/umt/login" }, name = "Login Processing POST")
	public String doLogin(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthRequest authRequest,RedirectAttributes model) 
	{
		String returnPath = "redirect:" + this.POST_LOGIN_URL;
		Boolean isOK = Boolean.valueOf(true);
		String realPass = "";
		HttpSession session = request.getSession();

		try 
		{
			ServiceOutcome<User> svcOutcome = this.userService.findByUsername(authRequest.getUserName());
			if (svcOutcome.getOutcome().booleanValue()) 
			{
				User user = (User) svcOutcome.getData();
				if (user != null) 
				{
					if (user.getIsEnabled().booleanValue()) 
					{
						if (user.getIsLocked().booleanValue()) 
						{
							model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.locked",null, LocaleContextHolder.getLocale()));
							logger.debug("The account is locked.");
							isOK = Boolean.valueOf(false);
						}
						if (user.getIsLoggedIn().booleanValue() && !user.getAllowMultipleSession().booleanValue()) {
							model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.loggedin",null, LocaleContextHolder.getLocale()));
							logger.debug("You are already logged-in.");
							isOK = Boolean.valueOf(false);
						}
						if (isOK.booleanValue()) 
						{
							String svrCaptcha = (String) request.getSession().getAttribute("CAPTCHA_CODE");
							Boolean verifiyCaptcha = Boolean.valueOf(true);
							try {
								if (this.CAPTCHA_OPTIONS.toLowerCase().contains("i"))
									verifiyCaptcha = Boolean.valueOf(false);
							} catch (Exception ex) {
								logger.error("CAPTCHA_OPTIONS may not be defined ");
								logger.error(ex.getMessage());
							}
							if (verifiyCaptcha.booleanValue()) {
								logger.debug("verifiying captcha");
								logger.debug("User sent : " + authRequest.getCaptcha());
								logger.debug("Server has : " + svrCaptcha);
								if (!svrCaptcha.equals(authRequest.getCaptcha())) {
									model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.badcaptcha", null, LocaleContextHolder.getLocale()));
									logger.debug("Captcha Mismatch");
									isOK = Boolean.valueOf(false);
								}
							} else {
								logger.debug("ignoring captcha");
							}
						}
						if (isOK.booleanValue()) 
						{
							String decryptedPassword = new String(java.util.Base64.getDecoder().decode(authRequest.getPassword()));
							String psk = request.getParameter("_csrf");
							psk = psk.substring(0, 16);
							AesUtil aesUtil = new AesUtil(128, 1000);
							if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
								realPass = aesUtil.decrypt(decryptedPassword.split("::")[1],decryptedPassword.split("::")[0], psk, decryptedPassword.split("::")[2]);
							}
							BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
							String existingPassword = realPass;
							String dbPassword = user.getPassword();
							isOK = Boolean.valueOf(passwordEncoder.matches(existingPassword, dbPassword));
							if (!isOK.booleanValue()) {
								model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.incorrect", null, LocaleContextHolder.getLocale()));
								logger.debug("Bad Credentials - Invalid password");
							}
						}
						if (isOK.booleanValue()) {
							/* Code for Session Hijacking */
							HttpSession currentSession = request.getSession();
							currentSession.invalidate();

							HttpSession newSession = request.getSession(true);
							newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());

							String token = UUID.randomUUID().toString();
							newSession.setAttribute("tokenVal", token);

							// Fetch the logged-in user College details -----
//							StaffCommonDetailsVO staffMstDto = staffService.getStaffDetailsCommonByEmailId(user.getUserName()); 
//							newSession.setAttribute("_Sesn_CollegeId", staffMstDto.getCollegeId());
//							newSession.setAttribute("_Sesn_CollegeName", staffMstDto.getCollegeName());
//							newSession.setAttribute("_Sesn_Designation", staffMstDto.getDesignationName());
//							newSession.setAttribute("_Sesn_Department", staffMstDto.getDepartmentName());

							authorizeUser(request, user, httpServletResponse);
						} 
						else 
						{
							user.setWrongLoginCount(Integer.valueOf(((user.getWrongLoginCount() == null) ? 0 : user.getWrongLoginCount().intValue()) + 1));
							if (user.getWrongLoginCount().intValue() >= 5) 
							{
								user.setIsLocked(Boolean.valueOf(true));
								user.setIsLoggedIn(Boolean.valueOf(false));
							}
							this.userService.save(user);
						}
					} 
					else 
					{
						model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.disabled", null,LocaleContextHolder.getLocale()));
						logger.debug("Account Disabled");
						isOK = Boolean.valueOf(false);
					}
				}
				else 
				{
					model.addFlashAttribute("err_msg", this.messageSource.getMessage("login.account.badcredentials", null, LocaleContextHolder.getLocale()));
					logger.debug("Bad Credentials - No user found");
					isOK = Boolean.valueOf(false);
					checkdDosAttack(request, authRequest.getUserName(), model, realPass);
				}
			} 
			else 
			{
				model.addFlashAttribute("err_msg", svcOutcome.getMessage());
				logger.debug("System Issue");
				isOK = Boolean.valueOf(false);
				return "redirect:/login";
			}
		} 
		catch (Exception ex) 
		{
			model.addFlashAttribute("err_msg",this.messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			logger.error(ex);
			isOK = Boolean.valueOf(false);
			return "redirect:/login";
		}
		if (isOK.booleanValue())
			return returnPath;
		return "redirect:/login";
	}

	private void authorizeUser(HttpServletRequest request, User user, HttpServletResponse httpServletResponse) {
		List<UserRoleMap> userRoleMaps = userService.findUserRoleMapByUserId(user.getUserId());
		userRoleMaps = (List<UserRoleMap>) userRoleMaps.stream().filter(r -> r.getIsActive().booleanValue()).collect(Collectors.toList());
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		List<Role> lstRoles = new ArrayList<>();
		for (UserRoleMap urm : userRoleMaps) {
			Role role = (Role) this.roleService.findByRoleId(urm.getRoleId()).getData();
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
			if (role.getIsActive().booleanValue())
				lstRoles.add(role);
		}
		user.setRoles(lstRoles);
		LoggedInUser userDetails = new LoggedInUser(user.getUserName(), user.getPassword(), true, true, true, true,grantedAuthorities, user.getPrimaryRole(), user);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication((Authentication) usernamePasswordAuthenticationToken);
		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
		addSameSiteCookieAttribute(httpServletResponse); // add SameSite=strict cookie attribute
		if(user.getPrimaryRole().getRoleCode().equals("ROLE_ADMIN")|| user.getPrimaryRole().getRoleCode().equals("ROLE_SYSTEM")) {
			loadMenus(request, userDetails,"CORE");
		}
		
		userService.createLoginHistory(user, request);
	}

	private void loadMenus(HttpServletRequest request, LoggedInUser liu, String appCode)
	{
		/* BEG UI Menu Addition */
		m_menus = menuRepository.findUIMenusByRole(liu.getPrimaryRole().getRoleId());
		if (appCode != null)
		{
			m_menus = m_menus.stream().filter(e -> (e.getAppCode() != null && e.getAppCode().equals(appCode)) || e.getAppCode() == null).collect(Collectors.toList());
		}
		
		List<Menu> parents = m_menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());
		//Sort by Display Order
		try
		{
			parents = parents.stream().sorted((a,b) -> (a.getDisplayOrder() - b.getDisplayOrder())).collect(Collectors.toList());
		}
		catch(Exception ex)
		{
			logger.error("ERROR SORTNG MENU -> " + ex.getMessage());
		}
		
		List<Menu> finalList = new ArrayList<Menu>();
		
		parents.forEach(m -> {
			m.setChildren(null);
			List<Menu> children = getMenuChildren(m);
			m.setChildren(children);
			finalList.add(m);
		});
						
		request.getSession().setAttribute("USER_MENUS", finalList);
		
		/* END UI Menu Addition */
		
		unLockUser(request.getParameter("userName"));
	}

	private void unLockUser(String username) {
		ServiceOutcome<User> svcOutcome = this.userService.findByUsername(username);
		User user = (User) svcOutcome.getData();
		if (user != null) {
			user.setWrongLoginCount(Integer.valueOf(0));
			user.setIsLocked(Boolean.valueOf(false));
			user.setIsLoggedIn(Boolean.valueOf(true));
			this.userService.save(user);
		}
	}

	private List<Menu> getMenuChildren(Menu parent) {
		List<Menu> children = (List<Menu>) this.m_menus.stream()
				.filter(m -> (m.getParent() != null && m.getParent().getMenuId() == parent.getMenuId()))
				.filter(m -> (m.getIsDisplay().booleanValue() == true)).collect(Collectors.toList());
		try {
			if (children != null) {
				children = (List<Menu>) children.stream()
						.sorted((a, b) -> a.getDisplayOrder().intValue() - b.getDisplayOrder().intValue())
						.collect(Collectors.toList());
				children.forEach(m -> {
					m.setChildren(null);
					List<Menu> children2 = getMenuChildren(m);
					m.setChildren(children2);
				});
			}
		} catch (Exception ex) {
			logger.error("ERROR SORTNG CHILD MENU -> " + ex.getMessage());
		}
		return children;
	}

	private int checkdDosAttack(HttpServletRequest request, String username, RedirectAttributes attr, String password) {
		HttpSession session = request.getSession();
		int loginAttempt;
		if (session.getAttribute("loginCount") == null) {
			session.setAttribute("loginCount", 0);
			loginAttempt = 0;
		} else {
			loginAttempt = (Integer) session.getAttribute("loginCount");
		}

		// this is 3 attempt counting from 0,1,2
		if (loginAttempt >= 5) {
			long lastAccessedTime = session.getLastAccessedTime();
			Date date = new Date();
			long currentTime = date.getTime();
			long timeDiff = currentTime - lastAccessedTime;
			// 20 minutes in milliseconds
			if (timeDiff >= 600000) {
				// invalidate user session, so they can try again
				session.invalidate();
			} else {
				// Error message
				loginAttempt++;
				attr.addFlashAttribute("err_msg","You have exceeded the 3 failed login attempt. Please contact your system admin for correct username and password.");
				// loginService.createNoUserLoginHistory(username, request);
			}

		} else {
			// userService.saveFailedLoginHistory(request, username,password);
			loginAttempt++;
			int allowLogin = 5 - loginAttempt;
			attr.addFlashAttribute("err_msg", "Login Attempt = " + loginAttempt + ". Invalid username or password. You have " + allowLogin + " attempts remaining. Please try again!");
			// loginService.createNoUserLoginHistory(username, request);
		}
		session.setAttribute("loginCount", loginAttempt);
		session.setAttribute("lastAccessTime", session.getLastAccessedTime());
		return loginAttempt;
	}

	private void addSameSiteCookieAttribute(HttpServletResponse response) {
		Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
		boolean firstHeader = true;
		for (String header : headers) { // there can be multiple Set-Cookie attributes
			if (firstHeader) {
				response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
				firstHeader = false;
				continue;
			}
			response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
		}
	}
}
