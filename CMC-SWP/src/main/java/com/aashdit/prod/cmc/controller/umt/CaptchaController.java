package com.aashdit.prod.cmc.controller.umt;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aashdit.prod.cmc.VO.umt.AuthRequest;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.framework.core.captcha.CaptchaMaker;
import com.aashdit.prod.cmc.framework.core.util.CaptchaString;
import com.aashdit.prod.cmc.model.umt.LoggedInUser;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.repository.umt.MenuRepository;
import com.aashdit.prod.cmc.service.umt.RoleService;
import com.aashdit.prod.cmc.service.umt.UserService;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;



@Controller
public class CaptchaController implements MessageSourceAware{

	final static Logger logger = Logger.getLogger(CaptchaController.class);
	
	private final String CAPTCHA_KEY = "CAPTCHA_CODE";
	
	private MessageSource messageSource;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuRepository menuRepository;
	
	private List<Menu> m_menus;
	
	@Value("${post.login.url}")
	private String POST_LOGIN_URL;
	
	/*
	 * i - ignore | v - verify
	 * s - smallcase | u - uppercase | m - mixed
	 */
	@Value("${captcha.options}")
	private String CAPTCHA_OPTIONS;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}
	
	
	@GetMapping(path="/captcha/{size}")
	public void generateCaptcha(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer size)  throws IOException
	{
		CaptchaMaker cMaker = new CaptchaMaker();
		
		String code = new CaptchaString(size).nextString();
		try
		{
			if (CAPTCHA_OPTIONS.toLowerCase().contains("s"))
			{
				code = code.toLowerCase();
				logger.debug("Captcha Option set to s");
			}
			
			if (CAPTCHA_OPTIONS.toLowerCase().contains("u"))
			{
				code = code.toUpperCase();
				logger.debug("Captcha Option set to u");
			}
			
		}
		catch(Exception ex)
		{
			logger.error("CAPTCHA_OPTIONS may not be defined ");
			logger.error(ex.getMessage());
		}
		
		request.getSession().setAttribute(CAPTCHA_KEY, code);
		
		BufferedImage img = cMaker.drawImage(code);
		response.setContentType("image/png");
		ImageIO.write(img, "png", response.getOutputStream());
	}
	
	@PostMapping(path="/umt/login", name="Login Processing POST")
	public String doLogin(HttpServletRequest request,@ModelAttribute AuthRequest authRequest, RedirectAttributes model)
	{
		String returnPath = "redirect:" + POST_LOGIN_URL;
		Boolean isOK = true;
		try
		{
			ServiceOutcome<User> svcOutcome = userService.findByUsername(authRequest.getUserName());
			if (svcOutcome.getOutcome() != false) {
				User user = svcOutcome.getData();
				if (user != null) {
					if (user.getIsEnabled()) {
						
						if (user.getIsLocked())
						{
							model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.locked", null, LocaleContextHolder.getLocale()));
							logger.debug("Account Locked");
							isOK = false;
						}
						
						if (user.getIsLoggedIn() && user.getAllowMultipleSession() == false)
						{
							model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.loggedin", null, LocaleContextHolder.getLocale()));
							logger.debug("Already Logged In");
							isOK = false;
						}
						
						//Captcha Check
						if (isOK)
						{
							String svrCaptcha = (String)request.getSession().getAttribute(CAPTCHA_KEY);
							Boolean verifiyCaptcha = true;
							try
							{
								if (CAPTCHA_OPTIONS.toLowerCase().contains("i"))
								{
									verifiyCaptcha = false;
								}
							}
							catch(Exception ex)
							{
								logger.error("CAPTCHA_OPTIONS may not be defined ");
								logger.error(ex.getMessage());
							}
							
							if (verifiyCaptcha)
							{
								logger.debug("verifiying captcha");
								logger.debug("User sent : " + authRequest.getCaptcha());
								logger.debug("Server has : " + svrCaptcha);
								if (!svrCaptcha.equals(authRequest.getCaptcha()))
								{
									model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.badcaptcha", null, LocaleContextHolder.getLocale()));
									logger.debug("Captcha Mismatch");
									isOK = false;
								}
							}
							else
							{
								logger.debug("ignoring captcha");
							}
						}
						
						//Password Check
						if (isOK)
						{
							BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
							String existingPassword = authRequest.getPassword(); // Password entered by user
							String dbPassword = user.getPassword(); // Load hashed DB password
							
							isOK = passwordEncoder.matches(existingPassword, dbPassword);
							if (!isOK)
							{
								model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.badcredentials", null, LocaleContextHolder.getLocale()));
								logger.debug("Bad Credentials - Invalid password");
							}
						}
						
						if (isOK)
						{
							authorizeUser(request, user, authRequest.getAppCode());
						}
						else
						{
							user.setWrongLoginCount((user.getWrongLoginCount() == null ? 0 : user.getWrongLoginCount()) + 1);
							if (user.getWrongLoginCount() >= 5)
							{
								user.setIsLocked(true);
								user.setIsLoggedIn(false);
							}
							userService.save(user);
						}

					} else {
						model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.disabled", null, LocaleContextHolder.getLocale()));
						logger.debug("Account Disabled");
						isOK = false;
					}
				} else {
					model.addFlashAttribute("error_msg", messageSource.getMessage("login.account.badcredentials", null, LocaleContextHolder.getLocale()));
					logger.debug("Bad Credentials - No user found");
					isOK = false;
				}
			}
			else
			{
				model.addFlashAttribute("error_msg", svcOutcome.getMessage());
				logger.debug("System Issue");
				isOK = false;
			}
		}
		catch(Exception ex){
			model.addFlashAttribute("error_msg", messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			logger.error(ex);
			isOK = false;
		}
		
		if (isOK)
		{
			return returnPath;
		}
		else
		{
			return "redirect:/login";
		}
		
	}

	private void authorizeUser(HttpServletRequest request, User user, String appCode) {
		List<UserRoleMap> userRoleMaps = userService.findUserRoleMapByUserId(user.getUserId());
		userRoleMaps = userRoleMaps.stream().filter(r -> r.getIsActive()).collect(Collectors.toList());
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		List<Role> lstRoles = new ArrayList<>();
		
		for (UserRoleMap urm : userRoleMaps) {
			Role role = roleService.findByRoleId(urm.getRoleId()).getData();
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
			if (role.getIsActive())
			{
				lstRoles.add(role);
			}
			
		}
		
		user.setRoles(lstRoles);
		
		LoggedInUser userDetails = new LoggedInUser(user.getUserName(), user.getPassword(), true, true,
				true, true, grantedAuthorities, user.getPrimaryRole(), user);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null , grantedAuthorities);

		 SecurityContext sc = SecurityContextHolder.getContext();
		 sc.setAuthentication(auth);
		
		 HttpSession session  = request.getSession(true);
		 session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,	sc);
		 
 
		 loadMenus(request, userDetails, appCode);
		 
		 userService.createLoginHistory(user, request);
	}
	
	@PostMapping(path="/umt/logout", name="Logout")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
	{
		try
		{
			User currentUser = SecurityHelper.getCurrentUser();
			if (currentUser != null)
			{
				
				ServiceOutcome<User> svcOutcome  = userService.findByUsername(currentUser.getUserName());
				User user = svcOutcome.getData();
				if (user != null) {
					user.setWrongLoginCount(0);
					user.setIsLocked(false);
					user.setIsLoggedIn(false);
					
					userService.createLogoutHistory(user, request);
	
					userService.save(user);
				}
				
				Cookie[] cookies = request.getCookies();
				String[] cookieNames = new String[cookies.length];

				for(int i = 0; i < cookies.length; i++)
				{
					cookieNames[i] = cookies[i].getName();
				}
				 
		
				CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(cookieNames);
			    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
			    cookieClearingLogoutHandler.logout(request, response, null);
			    securityContextLogoutHandler.logout(request, response, null);
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		
		String contextPath = request.getContextPath() + POST_LOGIN_URL;
		contextPath = contextPath.replace("//", "/");
		URL url = ServletUriComponentsBuilder.fromCurrentRequest().path(contextPath).build().toUri().toURL();
		
		//return "redirect:" + request.getProtocol().split("/")[0].toLowerCase() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
		return "redirect:" + url.toExternalForm();
	}
	
	@PostMapping(path="/umt/switchRole", name ="Switch Role")
	public String switchRole(RedirectAttributes model, @RequestParam Long roleId, @RequestParam(required = false) String appCode, HttpServletRequest request)
	{
		String returnPath =   "redirect:" + POST_LOGIN_URL;
		try
		{
			User user = SecurityHelper.getCurrentUser();
			Role role = roleService.findByRoleId(roleId).getData();
			
			user.setPrimaryRole(role);
			ServiceOutcome<User> svcUser = userService.save(user);
			if (svcUser.getOutcome())
			{
				authorizeUser(request, user, appCode);
			}
			else
			{
				model.addFlashAttribute("err_msg", "Could not switch role");
			}
			
		}
		catch(Exception ex)
		{
			model.addFlashAttribute("err_msg", messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			logger.error(ex);
		}
		
		return returnPath ;
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
		ServiceOutcome<User> svcOutcome  = userService.findByUsername(username);
		User user = svcOutcome.getData();
		if (user != null) {
			user.setWrongLoginCount(0);
			user.setIsLocked(false);
			user.setIsLoggedIn(true);

			userService.save(user);
		}
	}
	
	private List<Menu> getMenuChildren(Menu parent)
	{
		List<Menu> children = m_menus.stream()
									.filter(m -> (m.getParent() != null && m.getParent().getMenuId() == parent.getMenuId()))
									.filter(m -> m.getIsDisplay() == true)
									.collect(Collectors.toList());
		
		
		
		try
		{
			if (children != null)
			{
				children = children.stream().sorted((a,b) -> (a.getDisplayOrder() - b.getDisplayOrder())).collect(Collectors.toList());
				
				children.forEach(m -> {
					m.setChildren(null);
					List<Menu> children2 = getMenuChildren(m);
					m.setChildren(children2);
					
				});
			}
			
		}
		catch(Exception ex)
		{
			logger.error("ERROR SORTNG CHILD MENU -> " + ex.getMessage());
		}
		
		
		
		return children;
	}
}
